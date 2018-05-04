//package connector.matlab;
//
//import automaton.State;
//import automaton.StateValue;
//import config.AbstractContext;
//import values.Symbol;
//import com.mathworks.engine.EngineException;
//import com.mathworks.engine.MatlabEngine;
//import connector.IConnector;
//import connector.RequestQueryItem;
//import connector.ResponseQueryItem;
//import values.VariableValue;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Future;
//import java.util.stream.Collectors;
//
//public class MatlabConnector implements IConnector {
//    private MatlabEngine _eng;
//
//    private final String _workingDirectory;
//    private final String _sysName;
//
//    private final AbstractContext _ctx;
//
//    public MatlabConnector(String workingDirectory, String sysName, AbstractContext ctx) {
//        _workingDirectory = workingDirectory;
//        _sysName = sysName;
//        _ctx = ctx;
//    }
//
//    public Future<Void> loadModelAsync(String sysName){
//        if (_eng == null){
//            throw new NullPointerException("Matlab is not connected");
//        }
//
//        Future<Void> fLoad = _eng.evalAsync(String.format("load_system('%s')",sysName));
//        return fLoad;
//    }
//
////    private Future<Void> evalScriptAsync(String script) {
////        validateConnection();
////
////        Future<Void> fSim = _eng.evalAsync(script);
////        return fSim;
////    }
//
//    private void runSimulation(int simCount){
//        try {
//            if (simCount == 1) {
//                _eng.eval("out = sim(in, 'ShowProgress', 'on');");
//            }
//            else {
//                _eng.eval("out = parsim(in, 'ShowProgress', 'on');");
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private String buildScript(List<RequestQueryItem> requests){
//        MatlabSimulationScriptBuilder mb = new MatlabSimulationScriptBuilder();
//        mb.appendClearCommand();
//        mb.appendSimulationsCount(requests.size());
//        for (RequestQueryItem req: requests) {
//            MatlabSingleSimulationData simData = new MatlabSingleSimulationData(req.getSequence());
//            mb.appendSimulationsData(simData, _sysName);
//        }
//        return mb.getScript();
//    }
//
//    @Override
//    public boolean connect() {
//        try {
//            if (_eng == null){
//                _eng = MatlabEngine.startMatlab();
//            }
//            _eng.eval(String.format("cd '%s'", _workingDirectory));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }
//
//    @Override
//    public void close(){
//        if (_eng != null){
//            try {
//                _eng.close();
//            } catch (EngineException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public ResponseQueryItem sendQuery(RequestQueryItem request) {
//        String script = buildScript(new ArrayList<>(){{add(request);}});
//        try {
//            _eng.eval(script);
//            runSimulation(1);
//            Symbol[] resp = getSimulationResult(1);
//            return new ResponseQueryItem(request.getId(), request.getState(), new State(new StateValue(resp[0]), false), request.getSequence());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public List<ResponseQueryItem> sendQueries(List<RequestQueryItem> requests) {
//        List<ResponseQueryItem> responses = new ArrayList<>();
//        String script = buildScript(requests);
//        try {
//            _eng.eval(script);
//            runSimulation(requests.size());
//            Symbol[] resp = getSimulationResult(requests.size());
//            for(int i = 0; i < resp.length; i++) {
//                responses.add(new ResponseQueryItem(
//                        requests.get(i).getId(),
//                        requests.get(i).getState(),
//                        new State(new StateValue(resp[i]), false), requests.get(i).getSequence()
//                ));
//            }
//            return responses;
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public void resetSystem(State state) {
//    }
//
//    @Override
//    public State getDefault(RequestQueryItem req) {
//        ResponseQueryItem resp = sendQuery(req);
//        return resp.getEndState();
//    }
//
//    public Symbol[] getSimulationResult(int simulationsCount) throws Exception {
//        Symbol[] result = new Symbol[simulationsCount];
//
//        for (int i= 1; i <= simulationsCount; i++){
//
//            Map<String, VariableValue> valuesByNames = _ctx.generateOutputVariablesValues().stream().collect(Collectors.toMap(VariableValue::getName, v -> v));
//
//            for (int j = 1; j <= valuesByNames.values().size(); j++){
//
//                _eng.eval(String.format("signalName = out(%1$s).yout{%2$s}.Name", i, j));
//                String varName = (String) _eng.getVariable("signalName");
//                _eng.eval(String.format("data = out(%1$s).yout{%2$s}.Values.Data", i, j));
//                Object val = _eng.getVariable("data");
//
//                valuesByNames.get(varName).parseAndSetValue(val);
//            }
//            result[i-1] = new Symbol(new ArrayList<>(valuesByNames.values()));
//        }
//
//        return result;
//    }
//}
