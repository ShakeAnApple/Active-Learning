package impl;

import automaton.*;
import config.AbstractContext;
import connector.IConnector;
import connector.RequestQueryItem;
import connector.ResponseQueryItem;
import simulation.ISimulationService;
import simulation.StateHistoryItem;
import simulation.VariableHistoryItem;
import utils.Utils;
import values.AbstractValueHolder;
import values.IntervalValueHolder;
import values.Symbol;
import values.VariableValue;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LearningService {
    private final List<Symbol> _inputAlphabet;
    private final List<Symbol> _outputAlphabet;

    private final ISimulationService _simulationService;

    private Automaton _hypothesis;
    private HashSet<State> _possibleStates;
    private Map<State, SingleRequest[]> _processedStates;
    private Map<State, SingleRequest[]> _nextStates;

    private long _roundCount;

    /*
    reset symbol: reset;
                  resetButtonFloor0 := STRING_TO_BOOL(substr[2]);
                resetButtonFloor1 := STRING_TO_BOOL(substr[3]);
                resetButtonFloor2 := STRING_TO_BOOL(substr[4]);

                resetReqButton0 := STRING_TO_BOOL(substr[5]);
                resetReqButton1 := STRING_TO_BOOL(substr[6]);
                resetReqButton2 := STRING_TO_BOOL(substr[7]);

                resetCarAt0 := STRING_TO_BOOL(substr[8]);
                resetCarAt1 := STRING_TO_BOOL(substr[9]);
                resetCarAt2 := STRING_TO_BOOL(substr[10]);

                resetDoor0Closed := STRING_TO_BOOL(substr[11]);
                resetDoor1Closed := STRING_TO_BOOL(substr[12]);
                resetDoor2Closed := STRING_TO_BOOL(substr[13]);

                resetPos := STRING_TO_BOOL(substr[14]);


     req symbol: reset;
                motorUp;motorDown;
                door0;door1;door2
     */
    public LearningService(List<Symbol> inputAlphabet, List<Symbol> outputAlphabet, Automaton hypothesis, ISimulationService simulationService) {
        _inputAlphabet = inputAlphabet;
        _outputAlphabet = outputAlphabet;
        _hypothesis = hypothesis;
        _simulationService = simulationService;

        _roundCount = 0;

        init();
    }

    private void init(){
        State tempState = new State(new StateValue(_outputAlphabet.get(0)),false);

        State startState = _simulationService.getStartState(
                new RequestQueryItem(tempState, new SingleRequest[]{new SingleRequest(_inputAlphabet.get(0),1, true)})
        );

        _hypothesis.addState(startState);

        _possibleStates = new HashSet<State>();
        _nextStates = new HashMap<>();

        for (Symbol s: _outputAlphabet){
            State newState = new State(new StateValue(s), s.equals(startState.getStateValue().getSymbol()));
            _possibleStates.add(newState);
        }

        _processedStates = new HashMap<State, SingleRequest[]>();
        _nextStates.put(startState, new SingleRequest[0]);
    }

    public void stepForward(){
        _roundCount++;

        System.out.println("Round " + _roundCount + ":");
        long startAll = System.currentTimeMillis();

        // motorUp;motorDown;Door0;Door1;Door2
        List<RequestQueryItem> requestQueryItems = new ArrayList<>();
        for (State state: _nextStates.keySet()){
            SingleRequest[] sequence = _nextStates.get(state);
            for(Symbol inS: _inputAlphabet){
                SingleRequest[] newSeq = Arrays.copyOf(sequence, sequence.length + 1);
                newSeq[newSeq.length - 1] = new SingleRequest(inS, 1);
                requestQueryItems.add(new RequestQueryItem(state, newSeq));
            }
        }

        _nextStates.clear();

        System.out.print("Sending Queries: ");
        long startQueries = System.currentTimeMillis();

        List<ResponseQueryItem> responseQueryItems = _simulationService.sendQueries(requestQueryItems);

        long elapsedQueries = System.currentTimeMillis() - startQueries;
        System.out.print(elapsedQueries);
        System.out.println();

//        for(ResponseQueryItem r: responseQueryItems){
//            if (_hypothesis.getStartState() != null && r.getEndState().getStateValue().equals(_hypothesis.getStartState().getStateValue())){
//                r.getEndState().setIsStart(true);
//            }
//        }
//
        addToTransitions(responseQueryItems, true);

        System.out.println("Total: " + (System.currentTimeMillis() - startAll));
    }

    public boolean isReady(){
        if (_possibleStates.size() == _processedStates.size()
                || _nextStates.keySet().size() == 0){


            return true;
        }

        return false;
    }

    public void addToTransitions(List<ResponseQueryItem> responses, boolean addToNextStates){
        for (ResponseQueryItem r : responses){
            _processedStates.put(r.getStartState(),
                    Stream.of(
                            r.getSequence())
                            .limit(r.getSequence().length - 1)
                            .collect(Collectors.toList())
                            .toArray(new SingleRequest[r.getSequence().length - 1]
                            )
            );
        }

        for (ResponseQueryItem resp: responses){
            SingleRequest lastRequest = resp.getSequence()[resp.getSequence().length - 1];

            Transition tr = new Transition(
                    resp.getStartState(),
                    resp.getEndState(),
                    lastRequest.getSymbol(),
                    lastRequest.getRepeatCount());
            _hypothesis.addTransition(tr);

            if (addToNextStates && !_processedStates.containsKey(resp.getEndState()) && lastRequest.getRepeatCount() < Integer.MAX_VALUE){
                _nextStates.put(resp.getEndState(), resp.getSequence());
            }
        }
    }

}
