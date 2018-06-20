package automaton;

import utils.AlphabetConstructor;
import utils.SerializationUtils;
import values.AbstractVariableInfo;
import values.Symbol;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Automaton implements Serializable{
    private HashSet<State> _states;
    private Map<State, HashSet<Transition>> _transitions;
    private State _startState;

    private final List<AbstractVariableInfo> _inputVariableInfos;
    private final List<AbstractVariableInfo> _outputVariableInfos;

    private final List<Symbol> _inputAlphabet;
    private final List<Symbol> _outputAlphabet;

    public Automaton(List<AbstractVariableInfo> inputVars, List<AbstractVariableInfo> outputVars, List<Symbol> inputAlphabet, List<Symbol> outputAlphabet){
        _inputVariableInfos = inputVars;
        _outputVariableInfos = outputVars;
        _inputAlphabet = inputAlphabet;
        _outputAlphabet = outputAlphabet;

        reset();
    }

    public List<AbstractVariableInfo> getInputVariableInfos() {
        return _inputVariableInfos;
    }

    public List<AbstractVariableInfo> getOutputVariableInfos() {
        return _outputVariableInfos;
    }

    public List<Symbol> getInputAlphabet(){
        return _inputAlphabet;
    }

    public List<Symbol> getOutputAlphabet(){
        return _outputAlphabet;
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

//    public void loadTransitions(String path){
//        List<Transition> list = SerializationUtils.deserializeTransitions(path);
//        for(Transition tr : list){
//            if (!_transitions.containsKey(tr.getFrom())){
//                _transitions.put(tr.getFrom(), new HashSet<>(){{add(tr);}});
//            } else{
//                _transitions.get(tr.getFrom()).add(tr);
//            }
//            _states.add(tr.getFrom());
//            _states.add(tr.getTo());
//        }
//    }

    public void addTransition(Transition tr){
        if (_transitions.get(tr.getFrom()) == null){
            _transitions.put(tr.getFrom(), new HashSet<Transition>());
        }

        _transitions.get(tr.getFrom()).add(tr);
    }

    public HashSet<Transition> getTransitionsByFrom(State from){
        return _transitions.get(from);
    }

    public HashSet<Transition> getAllTransitions(){
        HashSet<Transition> res = new HashSet<Transition>();
        for (HashSet<Transition> trSet: _transitions.values()){
            res.addAll(trSet);
        }
        return res;
    }

    public HashSet<State> getStates() {
        return _states;
    }

    public void reset(){
        _states = new HashSet<>();
        _transitions = new HashMap<>();

        _startState = null;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
