package connection.nxt;

import config.NxtContext;
import connection.*;
import values.BooleanValueHolder;
import values.Symbol;
import values.VariableValue;
import connection.tcp.TcpServer;
import observationTable.RequestSequenceItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Eskimos on 11.01.2018.
 */
public class NxtStudioConnector implements IConnector {

    private final int _inPort;
    private final int _outPort;

    private TcpServer _serverIn;
    private TcpServer _serverOut;

    private final NxtContext _context;

    public NxtStudioConnector(int inPort, int outPort, NxtContext context){
        _inPort = inPort;
        _outPort = outPort;
        _context = context;
    }

    @Override
    public boolean connect() {
        Thread th1 = new Thread(() -> {
            try {
                _serverIn = new TcpServer(_inPort);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Thread th2 = new Thread(() -> {
            try {
                _serverOut = new TcpServer(_outPort);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        th1.start();
        th2.start();
        try {
            th1.join();
            th2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public Response sendQuery(RequestQueryItem queryItem) {
        try {
            return processQuery(queryItem);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Response processQuery(RequestQueryItem queryItem) throws IOException {

        String responseString = "";
        //SingleRequest lastReq = queryItem.getSequence();
        List<ResponseQueryItem> subresponses = new ArrayList<>();
        for(RequestSequenceItem req : queryItem.getSequence().getRequestItems()){
            _serverIn.write(
                    getSymbolString(req.getSymbol(), false)
            );
            responseString = _serverOut.readLine();
            _serverOut.writeLine(responseString);

            ResponseQueryItem resp = new ResponseQueryItem(new NxtSystemState(parseResponse(responseString)), req);
            subresponses.add(resp);

            if (_serverIn.canRead()) {
                _serverIn.readLine();
            }
        }

        return new Response(queryItem.getId(), subresponses);
    }

    private Symbol parseResponse(String str){
        try {
            List<VariableValue> variableValues = _context.generateOutputVariablesValues();
            variableValues.sort(Comparator.comparing(v -> v.getVarInfo().getOrder()));

            String[] strVars = str.split(";");
            for (int i = 0; i < strVars.length; i++) {

                variableValues.get(i).parseAndSetValue(strVars[i]);
            }

            Symbol newSymb = new Symbol(variableValues);
            return newSymb;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Response> sendQueries(List<RequestQueryItem> queryItems) {
        try {
            List<Response> responses = new ArrayList<>();
            for(RequestQueryItem queryItem : queryItems){
//                resetSystem(queryItem.getState());
                responses.add(processQuery(queryItem));
            }
            return responses;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void resetSystem(SystemState state) {
        NxtSystemState nxtState = (NxtSystemState)state;

        try {
            while (_serverOut.canRead()){
                _serverOut.readLine();
            }

            _serverIn.write(
                   getSymbolString(nxtState.getSymbol(), true)
            );

            String responseString = _serverOut.readLine();
            _serverOut.writeLine(responseString);

            while (_serverIn.canRead()) {
                _serverIn.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isResetAvailable() {
        return true;
    }

    private String[] getSymbolString(Symbol smb, boolean isReset){
        List<VariableValue> varVals = smb.getVariablesValues();

        List<String> varValsString = varVals
                .stream()
                .sorted(Comparator.comparing(v -> v.getVarInfo().getOrder()))
                .map(v -> v.getValue().toString())
                .collect(Collectors.toList());

        varValsString.add(0, String.valueOf(new BooleanValueHolder(isReset)));

        return varValsString.toArray(new String[varValsString.size()]);
    }

    public void close(){
        try {
            _serverIn.close();
            _serverOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
