package connection.matlab;

import values.AbstractValueHolder;
import values.VariableValue;

import java.util.ArrayList;
import java.util.List;

public class MatlabSimulationVariableData<VHolderType extends AbstractValueHolder> {

    // TODO ordered list?
    private List<VHolderType> _values;
    private String _varName;

    public MatlabSimulationVariableData(List<VariableValue<VHolderType>> values, boolean isEmptyWordSimulation) {
        String varName = values.get(0).getVarInfo().getName();
        _values = new ArrayList<VHolderType>();

        // first element added twice as in matlab default transition CONSUMES MODELLING TIME
        _values.add(values.get(0).getValue());

        if (!isEmptyWordSimulation) {
            for (VariableValue<VHolderType> val : values) {
                if (!varName.equals(val.getVarInfo().getName())) {
                    throw new IllegalArgumentException("all values should be of the same variable");
                }
                _values.add(val.getValue());
            }
        }

        _varName = varName;
    }

    public String getVarName(){
        return _varName;
    }

    public  List<VHolderType> getValues(){
        return _values;
    }

    public int getCyclesCount(){
        return _values.size();
    }
}
