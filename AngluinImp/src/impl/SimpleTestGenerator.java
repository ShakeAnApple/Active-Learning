package impl;

import values.Symbol;
import values.Word;

import java.util.*;

public class SimpleTestGenerator implements Iterable<Word>, Iterator<Word> {

    private List<Symbol> _inputAlphabet;
    private int _maxWordLength;
    private int _maxWordsCount;
    private int _cursor;

    public SimpleTestGenerator(List<Symbol> inputAlphabet, int maxWordLength) {
        _inputAlphabet = inputAlphabet;
        _maxWordLength = maxWordLength;
        _maxWordsCount = (int)Math.pow(inputAlphabet.size(), maxWordLength);
    }

    public Iterator<Word> iterator() {
        _cursor = 0;
        return this;
    }

    public boolean hasNext() {
        return _cursor < _maxWordsCount - 1;
    }

    public Word next() {
        if (!hasNext()) {
            _cursor = 0;
            throw new NoSuchElementException();
        }
        _cursor++;
        return generateTest();
    }

    private Word generateTest(){
        Random r = new Random();
        int wordLength = r.nextInt(_maxWordLength) + 1;
        List<Symbol> wSymbs = new ArrayList<>();
        for (int i = 0; i < wordLength; i++) {
            int nextSNum = r.nextInt(_inputAlphabet.size());
            wSymbs.add(_inputAlphabet.get(nextSNum));
        }
        return new Word(wSymbs);
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}