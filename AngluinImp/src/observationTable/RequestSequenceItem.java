package observationTable;

import values.Symbol;

public class RequestSequenceItem {
    private Symbol _symbol;
    private int _repeatCount;

    public RequestSequenceItem(Symbol symbol, int repeatCount){
        _symbol = symbol;
        _repeatCount = repeatCount;
    }

    public RequestSequenceItem(Symbol symbol){
        _symbol = symbol;

        // unknown
        _repeatCount = -1;
    }

    public Symbol getSymbol(){
        return _symbol;
    }

    public void incrementRepeatCount(){
        _repeatCount++;
    }

    public void setInfiniteRepeatCount(){
        _repeatCount = Integer.MAX_VALUE;
    }

    public void decrementRepeatCount(){
        _repeatCount--;
    }

    public int getRepeatCount(){
        return _repeatCount;
    }

    @Override
    public String toString() {
        return _symbol.toString();
    }

    @Override
    public boolean equals(Object obj) {
        RequestSequenceItem other = (RequestSequenceItem)obj;

        return this._symbol.equals(other._symbol) && (this._repeatCount == other._repeatCount);
    }
}
