package simulation;

import values.VariableValue;
import values.IntervalValueHolder;

public class VariableHistoryItem {
    private VariableValue<IntervalValueHolder> _variableValue;
    private double _pearsonCoefficient;

    public VariableHistoryItem(VariableValue<IntervalValueHolder> variableValue){
        _variableValue = variableValue;
    }

    public IntervalValueHolder getValue(){
        return _variableValue.getValue();
    }

    public void setPearsonCoefficient(double pearsonCoefficient) {
        _pearsonCoefficient = pearsonCoefficient;
    }

    public double getPearsonCoefficient() {
        return _pearsonCoefficient;
    }
}
