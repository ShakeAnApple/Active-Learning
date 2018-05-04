package connector;

import automaton.State;
import values.Symbol;

import java.util.List;

/**
 * Created by Eskimos on 11.01.2018.
 */
public interface IConnector {
    boolean connect();

    void close();

    ResponseQueryItem sendQuery(RequestQueryItem queryItem, Symbol respSymb);

    List<ResponseQueryItem> sendQueries(List<RequestQueryItem> queryItems, Symbol respSymb);

    void resetSystem(State state);

    State getDefault(RequestQueryItem req, Symbol respSymbol);
}
