package simulation;

import automaton.State;
import connector.RequestQueryItem;
import connector.ResponseQueryItem;

import java.util.List;

public interface ISimulationService {
    List<ResponseQueryItem> sendQueries(List<RequestQueryItem> requestQueryItems);

    State getStartState(RequestQueryItem requestQueryItem);
}
