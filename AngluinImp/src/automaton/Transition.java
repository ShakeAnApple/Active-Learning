package automaton;



import values.Symbol;

import java.io.Serializable;

public class Transition implements Serializable{

    private State _from;
    private State _to;
    private Symbol _symbol;
    private int _repeatCount;

    public Transition(State from, State to, Symbol symbol, int repeatCount) {
        _from = from;
        _to = to;
        _symbol = symbol;
        _repeatCount = repeatCount;
    }

    public Transition(State from, State to, Symbol symbol) {
        _from = from;
        _to = to;
        _symbol = symbol;
        _repeatCount = 1;
    }

    public State getFrom(){
        return _from;
    }

    public Symbol getSymbol(){
        return _symbol;
    }

    public State getTo(){
        return _to;
    }

    public int getRepeatCount(){
        return _repeatCount;
    }

    @Override
    public int hashCode() {
        return String.format("%1$s%2$s%3$s", _from.hashCode(), _to.hashCode(), _symbol.hashCode()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Transition other = (Transition)obj;
        if (other == null){
            return false;
        }
        return _from.equals(other._from) && _to.equals(other._to) && _symbol.equals(other._symbol);
    }
}
