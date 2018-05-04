package config;

import values.VariableInfo;
import values.VariableValue;

import java.util.ArrayList;
import java.util.List;

public class AbstractContext {
    private final List<VariableInfo> _outputVariables;
    private final List<VariableInfo> _inputVariables;


    public AbstractContext(List<VariableInfo> inputVariables, List<VariableInfo> outputVariables) {
        _outputVariables = outputVariables;
        _inputVariables = inputVariables;
    }

    public List<VariableValue> generateOutputVariablesValues(){
        List<VariableValue> vars = new ArrayList<>();

        for (VariableInfo val: _outputVariables) {
            vars.add(val.createVariableValue());
        }

        return vars;
    }

    public List<VariableInfo> getOutputVariablesInfos(){
        return _outputVariables;
    }

    public List<VariableValue> generateInputVariablesValues(){
        List<VariableValue> vars = new ArrayList<>();

        for (VariableInfo val: _inputVariables) {
            vars.add(val.createVariableValue());
        }

        return vars;
    }

    public List<VariableInfo> getInputVariablesInfos(){
        return _inputVariables;
    }
}
