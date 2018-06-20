package connection;

import observationTable.RequestSequence;
import observationTable.RequestSequenceItem;

import java.util.UUID;

/**
 * Created by Eskimos on 11.01.2018.
 */
public class ResponseQueryItem{
    private SystemState _output;
    private RequestSequenceItem _req;

    public ResponseQueryItem(SystemState output, RequestSequenceItem req){
        _output = output;
        _req = req;

    }

    public ResponseQueryItem(UUID id, SystemState output, RequestSequenceItem req ){
        _output = output;
        _req = req;
    }

    public SystemState getOutputSystemState(){
        return _output;
    }

    public void changeOutputSystemState(SystemState output){
        _output = output;
    }

    public RequestSequenceItem getRequest(){
        return _req;
    }
}
