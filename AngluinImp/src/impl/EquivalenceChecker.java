package impl;

import automaton.Automaton;
import automaton.State;
import automaton.Transition;
import connection.*;
import observationTable.RequestSequence;
import simulation.SimulationService;
import values.Symbol;
import values.Word;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class EquivalenceChecker {

    private final List<Symbol> _inputAlphabet;
    private final SimulationService _simulationService;

    public EquivalenceChecker(SimulationService simService, List<Symbol> inputAlphabet) {
        _inputAlphabet = inputAlphabet;
        _simulationService = simService;
    }

    public Counterexample Check(Automaton hypothesis, int depth) throws Exception {

        Counterexample c = null;
        SimpleTestGenerator testGenerator = new SimpleTestGenerator(_inputAlphabet, depth);

        List<Word> testedWords = hypothesis.getStates().stream().map(s -> s.getName()).collect(Collectors.toList());

        while(c == null && testGenerator.hasNext()){

                Word test = testGenerator.next();


                while (testedWords.contains(test) && testGenerator.hasNext()){
                    test = testGenerator.next();
                }

                Symbol hypRes = getHypothesisTestResult(hypothesis, test);
                Symbol sysRes = getSystemTestResult(test);

                if (!hypRes.equals(sysRes)){
                    c = new Counterexample(test, sysRes);
                }

                testedWords.add(test);
            }

        return c;
    }

    private Symbol getHypothesisTestResult(Automaton hypothesis, Word test) throws Exception {
        State nextState = hypothesis.getStartState();
        for (Symbol s : test.getSymbols()) {
            HashSet<Transition> transitionsFromState = hypothesis.getTransitionsByFrom(nextState);
            for (Transition tr : transitionsFromState) {
                if (tr.getSymbol().equals(s)){
                    nextState = tr.getTo();
                    break;
                }
            }
        }
        return nextState.getEmptySuffixOutput().getResponse();
    }

    private Symbol getSystemTestResult(Word test) throws Exception {

        Response resp = _simulationService.sendQuery(new RequestQueryItem(new RequestSequence(test)));

        return resp.getResultSystemState().asSymbol();
    }


}
