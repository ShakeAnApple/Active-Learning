package values;

import java.io.Serializable;
import java.util.*;

public class Symbol implements Serializable{
    private Map<String, VariableValue> _valuesByName;

    public Symbol(List<VariableValue> variableValues) {
        _valuesByName = new HashMap<>();

        for (VariableValue v: variableValues) {
            _valuesByName.put(v.getVarInfo().getName(), v);
        }
    }

    public List<VariableValue> getVariablesValues(){
        return new ArrayList<>(_valuesByName.values());
    }

    public List<String> getVariablesValuesNames(){
        return new ArrayList<>(_valuesByName.keySet());
    }

    public VariableValue getVariableValueByName(String varName){
        return _valuesByName.get(varName);
    }

    public void setVariableValueByName(String varName, AbstractValueHolder valueHolder){
        _valuesByName.get(varName).setValue(valueHolder);
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public String toString() {
        return Arrays.toString(_valuesByName.values().toArray());
    }

    public String toStringWithIntervalNum() {
        return Arrays.toString(_valuesByName.values().stream().map(v -> v.toStringWithIntervalNum()).toArray());
    }

    @Override
    public boolean equals(Object obj) {
        Symbol other = (Symbol)obj;

        if(_valuesByName.size() != other._valuesByName.size()){
            return false;
        }

        for (String varName: _valuesByName.keySet()) {
            VariableValue v1 = _valuesByName.get(varName);
            VariableValue v2 = other._valuesByName.get(varName);
            if (v2 == null){
                return false;
            }

            if (!v1.equals(v2)){
                return false;
            }
        }
        return true;
    }
}
