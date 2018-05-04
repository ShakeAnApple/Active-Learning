package connector.matlab;

import impl.SingleRequest;
import values.VariableValue;

import java.util.ArrayList;
import java.util.List;

public class MatlabSingleSimulationData {

    private List<MatlabSimulationVariableData> _wordSimulationData;
    private boolean _isEmpty;

    public MatlabSingleSimulationData(SingleRequest[] sequence) {

        _isEmpty = sequence.length == 1 && sequence[0].isEmpty();
        _wordSimulationData = new ArrayList<>();

        List<String> varNames = sequence[0].getSymbol().getVariablesValuesNames();

        for (String varName : varNames) {
            List<VariableValue> values = new ArrayList<>();
            for (SingleRequest req : sequence) {
                values.add(req.getSymbol().getVariableValueByName(varName));
            }

            _wordSimulationData.add(new MatlabSimulationVariableData(values, _isEmpty));
        }
    }

    public boolean isEmpty(){
        return _isEmpty;
    }


    public List<MatlabSimulationVariableData> getSequenceSimulationData(){
        return _wordSimulationData;
    }
}
