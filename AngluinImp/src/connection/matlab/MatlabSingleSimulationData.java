package connection.matlab;

import values.VariableValue;
import observationTable.RequestSequence;
import observationTable.RequestSequenceItem;

import java.util.ArrayList;
import java.util.List;

public class MatlabSingleSimulationData {

    private List<MatlabSimulationVariableData> _wordSimulationData;
    private boolean _isEmpty;

    public MatlabSingleSimulationData(RequestSequence sequence) {

        _isEmpty = sequence.size() == 1 && sequence.isEmpty();
        _wordSimulationData = new ArrayList<>();

        List<String> varNames = sequence.getRequestItems().get(0).getSymbol().getVariablesValuesNames();

        for (String varName : varNames) {
            List<VariableValue> values = new ArrayList<>();
            for (RequestSequenceItem req : sequence.getRequestItems()) {
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
