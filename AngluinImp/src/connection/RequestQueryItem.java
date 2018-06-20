package connection;

import automaton.State;
import observationTable.RequestSequence;

import java.util.UUID;

/**
 * Created by Eskimos on 11.01.2018.
 */
public class RequestQueryItem {
    private RequestSequence _seq;
    private UUID _id;

    public RequestQueryItem(RequestSequence seq ){
        _seq = seq;
        _id = UUID.randomUUID();
    }

    public RequestQueryItem(UUID id, State state, RequestSequence seq ){
        _seq = seq;
        _id = id;
    }

    public UUID getId() {
        return _id;
    }

    public RequestSequence getSequence(){
        return _seq;
    }
}
