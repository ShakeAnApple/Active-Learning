package automaton;

import values.Word;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class State implements Serializable{
    private boolean _isStart;
    private Word _name;
    private StateValue _stateValue;

    public State(Word name, List<RequestResult> requestResults, boolean isStart) {
        _isStart = isStart;
        _name = name;

        _stateValue = new StateValue(requestResults);
    }

    public Word getName(){
        return _name;
    }

    public StateValue getStateValue(){
        return _stateValue;
    }

    public boolean isStart(){
        return _isStart;
    }

    @Override
    public int hashCode() {
        List<RequestResult> orderedValues = _stateValue
                .getRequestResults()
                .stream()
                .sorted(Comparator.comparing(RequestResult::getOrder))
                .collect(Collectors.toList());
//        StringBuilder sb = new StringBuilder();
//        for(RequestResult v: orderedValues){
//            if (v.getResponse().getValue() instanceof IntervalValueHolder){
//                sb.append(String.format("%1$1s: %2$s; ", v.getName(), ((IntervalValueHandler)(v.getValue())).getCurrentIntervalNum()));
//            } else{
//                sb.append(String.format("%1$1s: %2$s; ", v.getName(), v.getValue()));
//            }
//        }
//        return Arrays.toString(orderedValues.stream().map(v -> v.toStringWithIntervalNum()).toArray()).concat(_name.toString()).hashCode();
        return Arrays.toString(orderedValues.stream().map(v -> v.toStringWithIntervalNum()).toArray()).hashCode();
    }

    public RequestResult getEmptySuffixOutput(){
        RequestResult res = _stateValue.getRequestResults().stream()
                .filter(r -> r.getOrder() == 0)
                .findFirst()
                .get();

        if (res == null){
            throw new RuntimeException("No empty suffix output o_o");
        }

        return res;
    }

    @Override
    public String toString() {
        return _stateValue.toString();
    }

    @Override
    public boolean equals(Object obj) {
        State other = (State)obj;
        return this._stateValue.equals(other._stateValue);
    }
}
