package impl;

import automaton.Symbol;

public class SingleRequest {
    private Symbol _symbol;
    private int _repeatCount;
    private boolean _isEmpty;

    public SingleRequest(Symbol symbol, int repeatCount){
        _symbol = symbol;
        _repeatCount = repeatCount;
    }

    public SingleRequest(Symbol symbol, int repeatCount, boolean isEmpty){
        _symbol = symbol;
        _repeatCount = repeatCount;
        _isEmpty = isEmpty;
    }

    public boolean isEmpty(){
        return _isEmpty;
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
}
