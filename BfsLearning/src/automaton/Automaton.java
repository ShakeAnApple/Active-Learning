package automaton;

import utils.Utils;
import values.AbstractVariableInfo;
import values.Interval;
import values.IntervalValueHolder;
import values.VariableValue;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

public class Automaton {
    private HashSet<State> _states;
    private Map<State, List<Transition>> _transitions;
    private State _startState;

    private List<AbstractVariableInfo> _inputVariableInfos;
    private List<AbstractVariableInfo> _outputVariableInfos;

    public Automaton(List<AbstractVariableInfo> inputVars, List<AbstractVariableInfo> outputVars){
        _states = new HashSet<>();
        _transitions = new HashMap();
        _inputVariableInfos = inputVars;
        _outputVariableInfos = outputVars;
    }

    public List<AbstractVariableInfo> getInputVariables() {
        return _inputVariableInfos;
    }

    public List<AbstractVariableInfo> getOutputVariables() {
        return _outputVariableInfos;
    }

    public State getStartState() {
        return _startState;
    }

    public void addState(State state) {
        _states.add(state);
        if (state.isStart()){
            _startState = state;
        }
    }

    public void loadTransitions(String path){
        List<Transition> list = Utils.deserializeTransitions(path);
        for(Transition tr : list){
            if (!_transitions.containsKey(tr.getFrom())){
                _transitions.put(tr.getFrom(), new ArrayList<>(){{add(tr);}});
            }
            _transitions.get(tr.getFrom()).add(tr);

            addState(tr.getFrom());
            addState(tr.getTo());
        }
    }

    public void addTransition(Transition tr){
        if (_transitions.get(tr.getFrom()) == null){
            _transitions.put(tr.getFrom(), new ArrayList<>());
        }
        addState(tr.getFrom());

        _transitions.get(tr.getFrom()).add(tr);

//        if (!_transitions.get(tr.getFrom()).contains(tr)){
//
//        }
        addState(tr.getTo());
    }

    public List<Transition> getTransitionsByFrom(State from){
        return _transitions.get(from);
    }

    public List<Transition> getAllTransitions(){
        return _transitions.values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public HashSet<State> getStates() {
        return _states;
    }

    public Map<State, String> getStatesNames(){
        Map<State, String> statesNames = new HashMap<>();
        List<State> statesArr = new ArrayList<>(_states);
        for (int i = 0; i < _states.size(); i++) {
            String stateName = "s" + i;
            statesNames.put(statesArr.get(i), stateName);
        }
        return statesNames;
    }
}
