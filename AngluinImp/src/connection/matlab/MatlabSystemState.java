package connection.matlab;

import values.Symbol;
import connection.SystemState;

public class MatlabSystemState implements SystemState {
    private Symbol _output;

    public MatlabSystemState(Symbol output){
        _output = output;
    }

    @Override
    public Symbol asSymbol() {
        return _output;
    }

    @Override
    public boolean equals(Object obj) {
        MatlabSystemState other = (MatlabSystemState)obj;
        if (other == null){
            return false;
        }

        return _output.equals(other.asSymbol());
    }
}
