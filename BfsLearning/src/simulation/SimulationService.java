package simulation;

import automaton.State;
import connector.IConnector;
import connector.RequestQueryItem;
import connector.ResponseQueryItem;
import values.IntervalValueHolder;
import values.VariableValue;

import java.util.*;
import java.util.stream.Collectors;

public class SimulationService implements ISimulationService {

    private final IConnector _connector;

    private State _startState;
    private List<String> _intervalVarsNames;

    public SimulationService(IConnector connector){
        _intervalVarsNames = new ArrayList<>();

        _connector = connector;
        _connector.connect();
    }

    private ResponseQueryItem sendQuery(RequestQueryItem requestQueryItem){
        ResponseQueryItem resp = null;

        _connector.resetSystem(requestQueryItem.getState());
        resp = _connector.sendQuery(requestQueryItem);
        requestQueryItem.getSequence()[requestQueryItem.getSequence().length - 1].incrementRepeatCount();

        if (isMovingInsideInterval(resp.getStartState(), resp.getEndState())){
            resp = processInterval(resp);
        }
        return resp;
    }

    private List<VariableHistoryItem> extractVariableHistoryItems(State state){
        List<VariableValue<IntervalValueHolder>> newIntervalValues = new ArrayList<>();
        for (int iName = 0; iName < _intervalVarsNames.size(); iName++) {
            newIntervalValues.add((VariableValue<IntervalValueHolder>) state
                    .getStateValue()
                    .getSymbol()
                    .getVariableValueByName(_intervalVarsNames.get(iName)));
        }

        List<VariableHistoryItem> variableHistoryItems = newIntervalValues
                .stream()
                .map(v -> new VariableHistoryItem(v.clone()))
                .collect(Collectors.toList());

        return variableHistoryItems;
    }

    private boolean isSomeContinuousVarChanging(List<StateHistoryItem> history){
        for (int i = 0; i < _intervalVarsNames.size(); i++ ){
            List<VariableHistoryItem> currentVarHistory = new ArrayList<VariableHistoryItem>();
            for(StateHistoryItem stateHistoryItem : history){
                currentVarHistory.add(stateHistoryItem.getVariableHistoryItems().get(i));
            }

            double currentPearsonCoeff = countPearsonCorrelationCoefficient(currentVarHistory
                            .stream()
                            .map(item -> item.getValue().getConcreteValue())
                            .collect(Collectors.toList()),
                    history
                            .stream()
                            .map(s -> (double)(s.getIterationsCount()))
                            .collect(Collectors.toList()));

            history
                    .get(history.size() - 1)
                    .getVariableHistoryItems()
                    .get(i)
                    .setPearsonCoefficient(currentPearsonCoeff);

            double previousPearsonCoeff = currentVarHistory.get(currentVarHistory.size() - 2).getPearsonCoefficient();
            if (Math.abs(Math.abs(currentPearsonCoeff) - Math.abs(previousPearsonCoeff)) > 0 || (Math.abs(currentPearsonCoeff) > 0.2)){
                return true;
            }
        }
        return false;
    }

    private ResponseQueryItem processInterval(ResponseQueryItem response){

        List<StateHistoryItem> history = new ArrayList<>();

        List<VariableHistoryItem> variableHistoryItems = extractVariableHistoryItems(response.getStartState());
        StateHistoryItem prevStateHistoryItem = new StateHistoryItem(variableHistoryItems, 1);

        history.add(prevStateHistoryItem);
        StateHistoryItem curStateHistoryItem = null;
        int ctr;
        for(ctr = 0; ctr < 10; ctr ++ ){
            response.getSequence()[response.getSequence().length - 1].incrementRepeatCount();
            response = _connector.sendQuery(new RequestQueryItem(response.getId(), response.getEndState(), response.getSequence()));

            List<VariableHistoryItem> newVariableHistoryItems = extractVariableHistoryItems(response.getEndState());
            curStateHistoryItem = new StateHistoryItem(newVariableHistoryItems, ctr + 2);

            if (prevStateHistoryItem.areStateContinuousVarsDifferent(curStateHistoryItem)){
                return response;
            } else{
                prevStateHistoryItem = curStateHistoryItem;
            }

            history.add(curStateHistoryItem);
        }

        if (!isSomeContinuousVarChanging(history)){
            response.getSequence()[response.getSequence().length - 1].setInfiniteRepeatCount();
            return response;
        }

        do{
            response.getSequence()[response.getSequence().length - 1].incrementRepeatCount();
            response = _connector.sendQuery(new RequestQueryItem(response.getId(), response.getEndState(), response.getSequence()));

            List<VariableHistoryItem> newVariableHistoryItems = extractVariableHistoryItems(response.getEndState());
            curStateHistoryItem = new StateHistoryItem(newVariableHistoryItems, ctr + 2);
        }while (!prevStateHistoryItem.areStateContinuousVarsDifferent(curStateHistoryItem));

        return response;
    }

    @Override
    public List<ResponseQueryItem> sendQueries(List<RequestQueryItem> requestQueryItems) {
        List<ResponseQueryItem> responses = new ArrayList<>();
        for(RequestQueryItem queryItem : requestQueryItems){
            responses.add(sendQuery(queryItem));
        }
        return responses;
    }

    private boolean isMovingInsideInterval(State prevResponseState, State resultSystemState) {
        List<String> intervalValuesNames = prevResponseState
                .getStateValue()
                .getSymbol()
                .getVariablesValues()
                .stream()
                .filter(v -> v.getValueHolder() instanceof IntervalValueHolder)
                .map(v -> v.getVarInfo().getName())
                .collect(Collectors.toList());

        for (String varName: intervalValuesNames) {
            IntervalValueHolder prevVal = (IntervalValueHolder) prevResponseState
                    .getStateValue()
                    .getSymbol()
                    .getVariableValueByName(varName)
                    .getValueHolder();
            IntervalValueHolder curVal = (IntervalValueHolder) resultSystemState
                    .getStateValue()
                    .getSymbol()
                    .getVariableValueByName(varName).getValueHolder();

            if (prevVal.equals(curVal) && !prevVal.getConcreteValue().equals(curVal.getConcreteValue())){
                return true;
            }
        }

        return false;
    }

    @Override
    public State getStartState(RequestQueryItem requestQueryItem) {
        State resp = _connector.getDefault(requestQueryItem);
        _startState = resp;

        _intervalVarsNames = resp.getStateValue()
                .getSymbol()
                .getVariablesValues()
                .stream()
                .filter(v -> v.getValueHolder() instanceof IntervalValueHolder)
                .map(v -> v.getVarInfo().getName())
                .collect(Collectors.toList());

        return resp;
    }

    private double countPearsonCorrelationCoefficient(List<Double> varList1, List<Double> varList2){
        if (varList1.size() != varList2.size()){
            throw new IllegalArgumentException("Arrays have different size, can't count coefficient");
        }

        Double sum1 = varList1.stream().mapToDouble(Double::doubleValue).sum();
        Double sum2 = varList2.stream().mapToDouble(Double::doubleValue).sum();

        Double arithmeticMean1 = sum1/varList1.size();
        Double arithmeticMean2 = sum2/varList2.size();

        List<Double> deviations1 = varList1.stream().map(item -> arithmeticMean1 - item).collect(Collectors.toList());
        List<Double> deviations2 = varList2.stream().map(item -> arithmeticMean2 - item).collect(Collectors.toList());

        List<Double> squaredDeviations1 = deviations1.stream().map(item -> item*item).collect(Collectors.toList());
        List<Double> squaredDeviations2 = deviations2.stream().map(item -> item*item).collect(Collectors.toList());

        Double squaredDeviationsSum1 = squaredDeviations1.stream().mapToDouble(Double::doubleValue).sum();
        Double squaredDeviationsSum2 = squaredDeviations2.stream().mapToDouble(Double::doubleValue).sum();

        if (squaredDeviationsSum1 == 0 || squaredDeviationsSum2 == 0){
            return 0;
        }

        List<Double> deviationsProducts = new ArrayList<Double>();
        for(int i = 0; i < deviations1.size(); i++){
            deviationsProducts.add(deviations1.get(i)*deviations2.get(i));
        }

        Double deviationProductsSum = deviationsProducts.stream().mapToDouble(Double::doubleValue).sum();

        return deviationProductsSum/Math.sqrt(squaredDeviationsSum1*squaredDeviationsSum2);
    }
}
