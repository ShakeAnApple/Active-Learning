package connection.nxt;

import values.Symbol;
import connection.SystemState;

public class NxtSystemState implements SystemState {
    private Symbol _symbol;

    public NxtSystemState(Symbol symbol){
        _symbol = symbol;
    }

    public Symbol getSymbol() {
        return _symbol;
    }

    @Override
    public Symbol asSymbol() {
        return _symbol;
    }

    @Override
    public boolean equals(Object obj) {
        NxtSystemState other = (NxtSystemState)obj;
        if (other == null){
            return false;
        }

        return _symbol.equals(other.asSymbol());
    }
}
