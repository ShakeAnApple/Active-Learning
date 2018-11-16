package config;

import values.AbstractVariableInfo;
import values.VariableValue;

import java.util.ArrayList;
import java.util.List;

public class AbstractContext {
    private final List<AbstractVariableInfo> _outputVariables;
    private final List<AbstractVariableInfo> _inputVariables;


    public AbstractContext(List<AbstractVariableInfo> inputVariables, List<AbstractVariableInfo> outputVariables) {
        _outputVariables = outputVariables;
        _inputVariables = inputVariables;
    }

    public List<VariableValue> generateOutputVariablesValues(){
        List<VariableValue> vars = new ArrayList<>();

        for (AbstractVariableInfo variableInfo: _outputVariables) {
            VariableValue varValue = new VariableValue(variableInfo);
            vars.add(varValue);
        }

        return vars;
    }

    public List<AbstractVariableInfo> getOutputVariablesInfos(){
        return _outputVariables;
    }

    public List<VariableValue> generateInputVariablesValues(){
        List<VariableValue> vars = new ArrayList<>();

        for (AbstractVariableInfo variableInfo: _inputVariables) {
            VariableValue varValue = new VariableValue(variableInfo);
            vars.add(varValue);
        }

        return vars;
    }

    public List<AbstractVariableInfo> getInputVariablesInfos(){
        return _inputVariables;
    }
}
