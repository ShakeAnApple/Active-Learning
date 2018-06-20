package connector.matlab;

import java.util.Arrays;
import java.util.List;

public class MatlabSimulationScriptBuilder {

    private StringBuilder _scriptBuilder;
    private int _simulationsCount;
    private int _currentSimulationNumber;

    public MatlabSimulationScriptBuilder() {
        _simulationsCount = 0;
        _currentSimulationNumber = 0;

        _scriptBuilder = new StringBuilder();
    }

    public void appendSimulationsCount(int count){
        //_scriptBuilder.append(String.format("in(%s) = Simulink.SimulationInput('Cylinder_simple');\n", count));
        _simulationsCount = count;
    }


//    in(1) = setBlockParameter(in(1), ['Cylinder_simple' '/Fwd'], 'OutValues', '[0 0 0]');
//    in(1) = setBlockParameter(in(1), ['Cylinder_simple' '/Retr'], 'OutValues', '[0 0 0]');
//    in(1) = setModelParameter(in(1), 'StopTime', '2');

    public void appendSimulationsData(MatlabSingleSimulationData data, String modelName){
        _currentSimulationNumber ++;
        if (_currentSimulationNumber > _simulationsCount){
            throw new IndexOutOfBoundsException(
                    String.format("Current simulation index %1$s is out of range. Total simulations count %2$s", _currentSimulationNumber,_simulationsCount));
        }

//        _scriptBuilder.append(String.format("in(%1$s).ModelName = '%2$s';\n", _currentSimulationNumber, modelName));
        _scriptBuilder.append(String.format("in(%1$s) = Simulink.SimulationInput('%2$s');\n", _currentSimulationNumber, modelName));

        List<MatlabSimulationVariableData> variablesSimulation = data.getSequenceSimulationData();
        for(MatlabSimulationVariableData variable : variablesSimulation){
            String valuesStr = Arrays.toString(variable.getValues().toArray()).replace(",","");
            _scriptBuilder.append(String.format("in(%1$s) = setBlockParameter(in(%1$s), ['%2$s' '/%3$s'], 'OutValues', '%4$s');\n",
                    _currentSimulationNumber, modelName, variable.getVarName(), valuesStr));
           // _scriptBuilder.append(String.format("in(%1$s) = in(%1$s).setVariable('%2$s', %3$s);\n", _currentSimulationNumber, variable.getVarName(), valuesStr));
        }

        //_scriptBuilder.append(String.format("in(%1$s) = in(%1$s).setModelParameter('StopTime','%2$s');\n", _currentSimulationNumber, variablesSimulation.get(0).getCyclesCount()));
        if (data.isEmpty()) {
            _scriptBuilder.append(String.format("in(%1$s) = setModelParameter(in(%1$s), 'StopTime', '%2$s');\n", _currentSimulationNumber, 0));
        } else {
            _scriptBuilder.append(String.format("in(%1$s) = setModelParameter(in(%1$s), 'StopTime', '%2$s');\n", _currentSimulationNumber, variablesSimulation.get(0).getCyclesCount() - 1));
        }
    }

    public String getScript(){
        return _scriptBuilder.toString();
    }

    public void appendClearCommand(){
        _scriptBuilder.append("clear in;");
    }

    public void reset(){
        if (_scriptBuilder.length() > 0) {
            _scriptBuilder.delete(0, _scriptBuilder.length() - 1);
            _simulationsCount = 0;
            _currentSimulationNumber = 0;
        }
    }

}
