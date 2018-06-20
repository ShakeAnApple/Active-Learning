package automaton;

import values.Symbol;
import values.Word;

import java.io.Serializable;

public class RequestResult implements Serializable {
    private Word _suffix;
    private Symbol _response;
    private int _order;

    public RequestResult(Word suffix, Symbol response, int order){
        _suffix = suffix;
        _response = response;
        _order = order;
    }

    public RequestResult(Symbol response, int order){
        _response = response;
        _order = order;
    }

    public int getOrder() {
        return _order;
    }

    public Word getSuffix() {
        return _suffix;
    }

    public Symbol getResponse() {
        return _response;
    }

    @Override
    public String toString() {
        return String.format("(%1$s; %2$s)", _order, _response);
    }

    public String toStringWithIntervalNum() {
        return String.format("(%1$s; %2$s)", _order, _response.toStringWithIntervalNum());
    }

    @Override
    public boolean equals(Object obj) {
        RequestResult other = (RequestResult)obj;
        return this._order == other._order && this._response.equals(other._response);
    }
}
