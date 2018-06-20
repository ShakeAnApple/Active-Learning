package values;

import observationTable.RequestSequence;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Word implements Serializable {

    private List<Symbol> _symbols;
    private boolean _isEmpty;

    public Word(List<Symbol> symbols) {
        _symbols = symbols;
    }

    public Word(Symbol symbol) {
        _symbols = List.of(symbol);
    }

    public Word(List<Symbol> symbols, boolean isEmpty){
        _symbols = symbols;
        _isEmpty = isEmpty;
    }

    public boolean isEmpty(){
        return _isEmpty;
    }

    public List<Symbol> getSymbols(){
        return _symbols;
    }

    public int size(){
        return _symbols.size();
    }

    public static Word parse(RequestSequence seq){
        return new Word(seq.getRequestItems().stream()
                        .map(r -> r.getSymbol())
                        .collect(Collectors.toList()),
                seq.isEmpty());
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public String toString() {
        return String.format("%1$s isEmpty: %2$s",Arrays.toString(_symbols.toArray()), _isEmpty);
    }

    @Override
    public boolean equals(Object obj) {
        Word other = (Word)obj;
        if ((this._symbols.size() != other._symbols.size() || (_isEmpty != other._isEmpty))){
            return false;
        }

        for (int i = 0; i < _symbols.size(); i++) {
            if(_symbols.get(i) != other._symbols.get(i)){
                return false;
            }
        }

        return true;
    }
}
