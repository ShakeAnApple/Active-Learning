package observationTable;

import values.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RequestSequence {
    private List<RequestSequenceItem> _requestItems;
    private boolean _isEmpty;

    public RequestSequence(List<RequestSequenceItem> requestSequenceItems) {
        _requestItems = requestSequenceItems;

        if (_requestItems.size() == 0){
            _isEmpty = true;
        }
    }

    public RequestSequence(Word word) {
        _requestItems = word.getSymbols().stream().map(s -> new RequestSequenceItem(s, 1)).collect(Collectors.toList());
    }

    public RequestSequence(RequestSequenceItem requestSequenceItem) {
        _requestItems = List.of(requestSequenceItem);
    }

    public RequestSequence(RequestSequenceItem requestSeqItem, boolean isEmpty){
        _requestItems = List.of(requestSeqItem);
        _isEmpty = isEmpty;
    }

    public boolean isEmpty(){
        return _isEmpty;
    }

    public List<RequestSequenceItem> getRequestItems(){
        return _requestItems;
    }

    public RequestSequence subtract(int fromIdx, int toIdx){
        return new RequestSequence(_requestItems.subList(fromIdx, toIdx));
    }

    public int size(){
        return _requestItems.size();
    }

    public RequestSequence concat(RequestSequenceItem symb){
        List<RequestSequenceItem> newSymbs = new ArrayList<>();
        if (!_isEmpty) {
            newSymbs = new ArrayList<>(_requestItems);
        }

        newSymbs.add(symb);
        return new RequestSequence(newSymbs);
    }

    public RequestSequence concat(RequestSequence other){
        if (other.isEmpty()) {
            return (new RequestSequence(new ArrayList<>(_requestItems)));
        }

        if (_isEmpty){
            return (new RequestSequence(new ArrayList<>(other._requestItems)));
        }

        List<RequestSequenceItem> newSymbs = new ArrayList<>(_requestItems);
        for (RequestSequenceItem s:other.getRequestItems()) {
            newSymbs.add(s);
        }
        return new RequestSequence(newSymbs);
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public String toString() {
        return String.format("%1$s isEmpty: %2$s", Arrays.toString(_requestItems.toArray()), _isEmpty);
    }

    @Override
    public boolean equals(Object obj) {
        RequestSequence other = (RequestSequence) obj;
        if ((this._requestItems.size() != other._requestItems.size() || (_isEmpty != other._isEmpty))){
            return false;
        }

        for (int i = 0; i < _requestItems.size(); i++) {
            if(!_requestItems.get(i).equals(other._requestItems.get(i))){
                return false;
            }
        }

        return true;
    }

}
