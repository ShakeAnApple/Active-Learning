package connector.matlab;

import values.VariableValue;
import values.AbstractValueHolder;

import java.util.ArrayList;
import java.util.List;

public class MatlabSimulationVariableData<VType extends AbstractValueHolder> {

    // TODO ordered list?
    private List<VType> _values;
    private String _varName;

    public MatlabSimulationVariableData(List<VariableValue<VType>> values, boolean isEmptyWordSimulation) {
        String varName = values.get(0).getVarInfo().getName();
        _values = new ArrayList<VType>();

        // first element added twice as in matlab default transition CONSUMES MODELLING TIME
        _values.add(values.get(0).getValueHolder());

        if (!isEmptyWordSimulation) {
            for (VariableValue<VType> val : values) {
                if (!varName.equals(val.getVarInfo().getName())) {
                    throw new IllegalArgumentException("all values should be of the same variable");
                }
                _values.add(val.getValueHolder());
            }
        }

        _varName = varName;
    }

    public String getVarName(){
        return _varName;
    }

    public  List<VType> getValues(){
        return _values;
    }

    public int getCyclesCount(){
        return _values.size();
    }
}
