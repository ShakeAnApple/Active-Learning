package values;

import java.io.Serializable;
import java.util.*;

public class Symbol implements Serializable{
    private Map<String, VariableValue> _valuesByName;

    // TODO fix constructors
    public Symbol(List<VariableInfo> automatonVars, boolean a) {
        _valuesByName = new HashMap<>();

        for (VariableInfo v: automatonVars) {
            _valuesByName.put(v.getName(), v.createVariableValue());
        }
    }

    private Symbol(){
        _valuesByName = new HashMap<>();
    }

    public Symbol(List<VariableValue> variableValues) {
        _valuesByName = new HashMap<>();

        for (VariableValue v: variableValues) {
            _valuesByName.put(v.getName(), v);
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

    public void setVariableValueByName(String varName, AbstractValueHandler valueHandler){
        _valuesByName.get(varName).setValue(valueHandler);
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public String toString() {
        return Arrays.toString(_valuesByName.values().toArray());
    }

    @Override
    public boolean equals(Object obj) {
        Symbol other = (Symbol)obj;
        if (other == null){
            return false;
        }

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
