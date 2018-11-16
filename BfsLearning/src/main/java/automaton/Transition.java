package automaton;


import automaton.transitionFormula.TransitionFormula;
import values.Symbol;

import java.io.Serializable;

public class Transition implements Serializable{

    private State _from;
    private State _to;
    private Symbol _symbol;
    private int _repeatCount;
    //TODO temp solution
    private String _stringFormula;
    private TransitionFormula _transitionFormula;

    public Transition(State from, State to, Symbol symbol, int repeatCount) {
        _from = from;
        _to = to;
        _symbol = symbol;
        _repeatCount = repeatCount;
    }

    public Transition(State from, State to, String formula) {
        _from = from;
        _to = to;
        _stringFormula = formula;
        _repeatCount = 1;
    }

    public Transition(State from, State to, TransitionFormula formula) {
        _from = from;
        _to = to;
        _transitionFormula = formula;
        _repeatCount = 1;
    }

    public TransitionFormula getTransitionFormula() {
        return _transitionFormula;
    }

    public String getStringFormula(){
        return _stringFormula;
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
    public String toString() {
        return String.format("From: %1$s To: %2$s By: %3$s RC: %4$s", _from, _to, _symbol, _repeatCount);
    }

    @Override
    public boolean equals(Object obj) {
        Transition other = (Transition)obj;
        return this._from.equals(other._from) &&
                this._to.equals(other._to) &&
                this._symbol.equals(other._symbol) &&
                this._repeatCount == other._repeatCount;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    public String toNusmvString() {
        return _transitionFormula.toString();
    }
}
