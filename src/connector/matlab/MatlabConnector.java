//package connector.matlab;
//
//import automaton.State;
//import automaton.StateValue;
//import values.Symbol;
//import com.mathworks.engine.EngineException;
//import com.mathworks.engine.MatlabEngine;
//import connector.IConnector;
//import connector.RequestQueryItem;
//import connector.ResponseQueryItem;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Future;
//
//public class MatlabConnector implements IConnector {
//    private MatlabEngine _eng;
//
//    private String _workingDirectory;
//    private String _sysName;
//
//    public MatlabConnector(String workingDirectory, String sysName) {
//        _workingDirectory = workingDirectory;
//        _sysName = sysName;
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
//    public ResponseQueryItem sendQuery(RequestQueryItem request, Symbol respSymb) {
//        String script = buildScript(new ArrayList<>(){{add(request);}});
//        try {
//            _eng.eval(script);
//            runSimulation(1);
//            Symbol[] resp = getSimulationResult(1, respSymb);
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
//    public List<ResponseQueryItem> sendQueries(List<RequestQueryItem> requests, Symbol respSymb) {
//        List<ResponseQueryItem> responses = new ArrayList<>();
//        String script = buildScript(requests);
//        try {
//            _eng.eval(script);
//            runSimulation(requests.size());
//            Symbol[] resp = getSimulationResult(requests.size(), respSymb);
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
//    public State getDefault(RequestQueryItem req, Symbol respSymbol) {
//        ResponseQueryItem resp = sendQuery(req, respSymbol);
//        return resp.getEndState();
//    }
//
//    public Symbol[] getSimulationResult(int simulationsCount, Symbol outputSymbol) throws Exception {
//        Symbol[] result = new Symbol[simulationsCount];
//
//        for (int i= 1; i <= simulationsCount; i++){
//
//            Symbol singleSimResult = outputSymbol.copyStructure();
//
//            for (int j = 1; j <= outputSymbol.getVariablesValues().size(); j++){
//
//                _eng.eval(String.format("signalName = out(%1$s).yout{%2$s}.Name", i, j));
//                String varName = (String) _eng.getVariable("signalName");
//                _eng.eval(String.format("data = out(%1$s).yout{%2$s}.Values.Data", i, j));
//                Object val = _eng.getVariable("data");
//
//                singleSimResult.parseAndSetValueByName(varName, val);
//            }
//            result[i-1] = singleSimResult;
//        }
//
//        return result;
//    }
//}
