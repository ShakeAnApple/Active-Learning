package connection;

import values.Symbol;

import java.util.List;

/**
 * Created by Eskimos on 11.01.2018.
 */
public interface IConnector {
    boolean connect();

    void close();

    Response sendQuery(RequestQueryItem queryItem);

    List<Response> sendQueries(List<RequestQueryItem> queryItems);

    void resetSystem(SystemState state);

    boolean isResetAvailable();

//    Symbol getDefault(RequestQueryItem req, Symbol respSymbol);
}
