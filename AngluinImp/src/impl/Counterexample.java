package impl;

import values.Symbol;
import values.Word;

public class Counterexample {
    private Word _input;
    private Symbol _output;

    public Counterexample(Word input, Symbol output) {
        _input = input;
        _output = output;
    }

    public Word getInput(){
        return _input;
    }

    public Symbol getOutput(){
        return _output;
    }
}
