package simulation;

import connection.*;
import values.VariableValue;
import observationTable.RequestSequence;
import observationTable.RequestSequenceItem;
import values.IntervalValueHolder;

import java.util.*;
import java.util.stream.Collectors;

public class SimulationService {

    private final IConnector _connector;

    // make suffix tree?
    private List<Response> _startingSequencesProcessed;
    private SystemState _startState;

    public SimulationService(IConnector connector){
        _connector = connector;
        _startingSequencesProcessed = new ArrayList<>();
    }

    public Response getStartState(RequestQueryItem req){
        Response resp = _connector.sendQuery(req);

        _startState = resp.getResultSystemState();

        return resp;
    }

    public Response sendQuery(RequestQueryItem queryItem){

        List<ResponseQueryItem> knownSeq = new ArrayList<>();
        List<RequestSequenceItem> undiscoveredSeq = new ArrayList<>();

        if (_connector.isResetAvailable()) {
            knownSeq = getLongestProcessedSubseq(queryItem.getSequence());
            if (knownSeq.size() > 0) {
                _connector.resetSystem(knownSeq.get(knownSeq.size() - 1).getOutputSystemState());
            } else{
                _connector.resetSystem(_startState);
            }

            for (int i = knownSeq.size(); i < queryItem.getSequence().size(); i ++){
                undiscoveredSeq.add(queryItem.getSequence().getRequestItems().get(i));
            }
        }
        else{
            for (int i = 0; i < queryItem.getSequence().size(); i ++){
                undiscoveredSeq.add(queryItem.getSequence().getRequestItems().get(i));
            }
        }

        SystemState prevResponseState = null;
        if (knownSeq.size() > 0){
            prevResponseState = knownSeq.get(knownSeq.size() - 1).getOutputSystemState();
        } else{
            prevResponseState = _startState;
        };

        Response resp = new Response(knownSeq);
        for (RequestSequenceItem seqItem: undiscoveredSeq) {
            _connector.resetSystem(prevResponseState);

            seqItem.incrementRepeatCount();
            resp = _connector.sendQuery(new RequestQueryItem(new RequestSequence(seqItem)));
            if (continuousVarChangedAndIntervalRemains(prevResponseState, resp.getResultSystemState())){
                resp = processInterval(prevResponseState, seqItem, resp);
            }
//            if (resp.getResultSystemState().equals(prevResponseState) && _connector.isResetAvailable()){
//                resp = processInterval(prevResponseState, seqItem, resp);
//            }
            prevResponseState = resp.getResultSystemState();
            knownSeq.add(resp.getResponseSequence().get(resp.getResponseSequence().size() - 1));

        }

        if (!queryItem.getSequence().isEmpty()) {
            addToProcessedSequences(new Response(knownSeq));
        }

        return resp;
    }

    private boolean continuousVarChangedAndIntervalRemains(SystemState prevResponseState, SystemState resultSystemState) {
        List<String> intervalValuesNames = prevResponseState.asSymbol()
                .getVariablesValues()
                .stream()
                .filter(v -> v.getValue() instanceof IntervalValueHolder)
                .map(v -> v.getVarInfo().getName())
                .collect(Collectors.toList());

        for (String varName: intervalValuesNames) {
            IntervalValueHolder prevVal = (IntervalValueHolder) prevResponseState.asSymbol().getVariableValueByName(varName).getValue();
            IntervalValueHolder curVal = (IntervalValueHolder) resultSystemState.asSymbol().getVariableValueByName(varName).getValue();

            if (prevVal.equals(curVal) && !prevVal.getConcreteValue().equals(curVal.getConcreteValue())){
                return true;
            }
        }

        return false;
    }

    private void addToProcessedSequences(Response newResp) {
        boolean isAbsorbed = false;
        List<Response> absorbedResponses = new ArrayList<>();
        for (Response resp: _startingSequencesProcessed){
            if (resp.getResponseSequence().size() < newResp.getResponseSequence().size()){
                if (newResp.startsWith(resp)){
                    absorbedResponses.add(resp);
                }
            } else{
                if (resp.startsWith(newResp)){
                    isAbsorbed = true;
                    break;
                }
            }
        }

        for(Response absorbedResp : absorbedResponses){
            _startingSequencesProcessed.remove(absorbedResp);
        }

        if (!isAbsorbed){
            _startingSequencesProcessed.add(newResp);
        }
    }

    private List<ResponseQueryItem> getLongestProcessedSubseq(RequestSequence sequence) {

        List<ResponseQueryItem> longestSeq = new ArrayList<>();
        for (Response response : _startingSequencesProcessed) {
            List<ResponseQueryItem> newLongestSeq = new ArrayList<>();

            List<ResponseQueryItem> processedReqItems = response.getResponseSequence();
            List<RequestSequenceItem> newReqItems = sequence.getRequestItems();

            for (int i = 0; i < Math.min(processedReqItems.size(), newReqItems.size()); i++){
                if (processedReqItems.get(i).getRequest().getSymbol()
                        .equals(newReqItems.get(i).getSymbol())){
                    newLongestSeq.add(processedReqItems.get(i));
                } else{
                    break;
                }
            }

            if (newLongestSeq.size() > longestSeq.size()){
                longestSeq = newLongestSeq;
            }

            if (longestSeq.size() == newReqItems.size()){
                break;
            }
        }
        
        return longestSeq;
    }

    public List<Response> sendQueries(List<RequestQueryItem> queryItems){
        List<Response> responses = new ArrayList<>();
        for(RequestQueryItem queryItem : queryItems){
            responses.add(sendQuery(queryItem));
        }
        return responses;
    }

    // TODO create MatlabSimService & NxtSimService & add different process loop logic
    private Response processInterval(SystemState prevState, RequestSequenceItem requestSequenceItem, Response response){

        List<StateHistoryItem> history = new ArrayList<>();


        List<String> intervalValuesNames = prevState.asSymbol()
                .getVariablesValues()
                .stream()
                .filter(v -> v.getValue() instanceof IntervalValueHolder)
                .map(v -> v.getVarInfo().getName())
                .collect(Collectors.toList());


//        List<VariableValue<IntervalValueHolder>> intervalValues = new ArrayList<>();
//        for (int i = 0; i < intervalValuesNames.size(); i++) {
//            intervalValues.add((VariableValue<IntervalValueHolder>) prevState
//                    .asSymbol()
//                    .getVariableValueByName(intervalValuesNames.get(i)));
//        }
//
//        List<VariableHistoryItem> sortedVariableHistoryItems = intervalValues
//                .stream()
//                .sorted(Comparator.comparing(v -> v.getVarInfo().getOrder()))
//                .map(v -> new VariableHistoryItem(v.clone()))
//                .collect(Collectors.toList());
//
//        history.add(new StateHistoryItem(sortedVariableHistoryItems, 1));


        // TODO add "transition won't be a self loop" support
        prevState = response.getResultSystemState();

        for(int i = 0; i < 10; i ++ ){
            requestSequenceItem.incrementRepeatCount();
            response = _connector.sendQuery(new RequestQueryItem(new RequestSequence(requestSequenceItem)));

            if (!response.getResultSystemState().equals(prevState)){
                return response;
            }

            List<VariableValue<IntervalValueHolder>> newIntervalValues = new ArrayList<>();
            for (int iName = 0; iName < intervalValuesNames.size(); iName++) {
                newIntervalValues.add((VariableValue<IntervalValueHolder>) response.getResultSystemState()
                        .asSymbol()
                        .getVariableValueByName(intervalValuesNames.get(iName)));
            }

            List<VariableHistoryItem> newSortedVariableHistoryItems = newIntervalValues
                    .stream()
                    .sorted(Comparator.comparing(v-> v.getVarInfo().getOrder()))
                    .map(v -> new VariableHistoryItem(v.clone()))
                    .collect(Collectors.toList());

            history.add(new StateHistoryItem(newSortedVariableHistoryItems, i + 2));
        }

        boolean isVarChanging = false;
        for (int i = 0; i < intervalValuesNames.size(); i++ ){

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
                isVarChanging = true;
                break;
            }
        }

        if (!isVarChanging){
            requestSequenceItem.setInfiniteRepeatCount();
            return response;
        }


        // fluctuations case is unsupported here
        while(response.getResultSystemState().equals(prevState)){
            requestSequenceItem.incrementRepeatCount();
            response = _connector.sendQuery(new RequestQueryItem(new RequestSequence(requestSequenceItem)));
        }
        return response;
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
