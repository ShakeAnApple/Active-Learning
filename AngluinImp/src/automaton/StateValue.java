package automaton;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StateValue implements Serializable {
    private List<RequestResult> _requestResults;

    public StateValue(List<RequestResult> requestResults){
        _requestResults = requestResults;
    }

    public List<RequestResult> getRequestResults(){
        return _requestResults;
    }

    @Override
    public String toString()  {
        return Arrays.toString(_requestResults.toArray());
    }

    public String toStringWithIntervalNum()  {
        return Arrays.toString(_requestResults.stream().map(r -> r.toStringWithIntervalNum()).toArray());
    }

    @Override
    public boolean equals(Object obj) {
        StateValue other = (StateValue)obj;
        List<RequestResult> thisOrderedReqRes = this._requestResults.stream()
                .sorted(Comparator.comparing(RequestResult::getOrder))
                .collect(Collectors.toList());

        List<RequestResult> otherOrderedReqRes = other._requestResults.stream()
                .sorted(Comparator.comparing(RequestResult::getOrder))
                .collect(Collectors.toList());
        for (int i =0; i< thisOrderedReqRes.size(); i++) {
            if (!otherOrderedReqRes.get(i).equals(thisOrderedReqRes.get(i))){
                return false;
            }
        }
        return true;
    }
}
