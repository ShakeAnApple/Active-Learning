package automaton.optimization;

import automaton.Automaton;
import automaton.State;
import automaton.Transition;
import utils.AlphabetBuilder;
import utils.Tuple;
import utils.logging.Log;
import values.AbstractVariableInfo;
import values.Symbol;
import values.VariableValue;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AutomatonOptimizer {

    private class TransitionGroup{
        private State _from;
        private State _to;
        private List<Transition> _transitions;

        public TransitionGroup(State from, State to, List<Transition> transitions) {
            _from = from;
            _to = to;
            this._transitions = transitions;
        }

        public State getFrom() {
            return _from;
        }

        public State getTo() {
            return _to;
        }

        public List<Transition> getTransitions() {
            return _transitions;
        }
    }

    public AutomatonOptimizer() {
    }

    public Automaton reduceTransitions(Automaton sourceAutomaton){
        Automaton targetAutomaton = new Automaton(sourceAutomaton.getInputVariables(), sourceAutomaton.getOutputVariables());

        List<TransitionGroup> transitionGroups = groupTransitionsByEndStates(sourceAutomaton.getAllTransitions());

        long start = System.currentTimeMillis();
        for (TransitionGroup transitionGroup: transitionGroups){
            Transition newTransition =  reduceTransitionsImpl(sourceAutomaton.getInputVariables(), transitionGroup);
            targetAutomaton.addTransition(newTransition);
        }
        Log.msg("Total optimization: " + (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - start)));

        return targetAutomaton;
    }

    private List<TransitionGroup> groupTransitionsByEndStates(List<Transition> transitions) {
        List<TransitionGroup> result = new ArrayList<>();
        Map<Tuple<State, State>, List<Transition>> transitionsByStates = new HashMap<>();

        for (Transition tr: transitions){
            Tuple<State, State> statesTuple = new Tuple<>(tr.getFrom(), tr.getTo());
            if (!transitionsByStates.containsKey(statesTuple)){
                transitionsByStates.put(statesTuple, new ArrayList<>());
            }

            transitionsByStates.get(statesTuple).add(tr);
        }

        for (Tuple<State, State> key: transitionsByStates.keySet()){
            result.add(new TransitionGroup(key.getObj1(), key.getObj2(), transitionsByStates.get(key)));
        }

        return result;
    }

    /*
        a b c
        0 0 0 -> 0
        0 0 1 -> 1
        0 1 0 -> 1
        0 1 1 -> 1
        1 0 0 -> 0
        1 0 1 -> 1
        1 1 0 -> 0
        1 1 1 -> 1
    */
    private int ctr = 0;
    private Transition reduceTransitionsImpl(List<AbstractVariableInfo> inputVars, TransitionGroup transitionGroup) {
        try {
            Path p = Paths.get(Paths.get("").toAbsolutePath().toString(),"/tmp/input");
            if (Files.exists(p)){
                Files.delete(p);
            }
            Path path = Files.createFile(p);

            TruthTableBuilder cb = new TruthTableBuilder(inputVars.stream()
                    .sorted(Comparator.comparing(AbstractVariableInfo::getOrder))
                    .map(v -> v.getName())
                    .collect(Collectors.toList())
            );
            for (Transition tr: transitionGroup.getTransitions()){
                cb.appendRow(tr.getSymbol(), true);
            }

            List<Symbol> subtractionSymbolsSet = generateSubtractionSymbolsSet(
                    transitionGroup.getTransitions()
                    .stream()
                    .map(t -> t.getSymbol()).collect(Collectors.toList())
                    , inputVars);
            for (Symbol s: subtractionSymbolsSet){
                cb.appendRow(s, false);
            }

            Files.write(path, cb.toString().getBytes());

            BooleanFormulaResolver satBooleanFormulaResolver = new SatBooleanFormulaResolver(3,15);
            long start = System.currentTimeMillis();
            String formula = satBooleanFormulaResolver.resolve(path.toAbsolutePath().toString());
            if (formula == null){
                BooleanFormulaResolver expressoResolver = new ExpressoBooleanFormulaResolver();
                formula = expressoResolver.resolve(path.toAbsolutePath().toString());
            }
            Log.msg("State " + ctr + " optimization " + (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - start)));
            Log.msg("State " + ctr + " processed");
            ctr ++;
            Files.delete(path);
            return new Transition(transitionGroup.getFrom(), transitionGroup.getTo(), formula);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private List<Symbol> generateSubtractionSymbolsSet(List<Symbol> symbols, List<AbstractVariableInfo> inputVars) {
        AlphabetBuilder ab = new AlphabetBuilder();
        List<Symbol> alphabet = ab.build(inputVars);

        List<Symbol> result = new ArrayList<>();
        for (Symbol s: alphabet){
            if (!symbols.contains(s)){
                result.add(s);
            }
        }
        return result;
    }

    private class TruthTableBuilder {
        private StringBuilder _sb;
        TruthTableBuilder(List<String> variableNames) {
            _sb = new StringBuilder();
            _sb.append(
                    Arrays.toString(variableNames.toArray())
                            .replace("[", "")
                            .replace("]", "")
                            .replace(", ", " ") + "\n"
            );
        }

        public void appendRow(Symbol symb, boolean value){
            List<VariableValue> orderedValues = symb.getVariablesValues().stream()
                    .sorted(Comparator.comparing(v -> v.getVarInfo().getOrder()))
                    .collect(Collectors.toList());

            for (VariableValue vv: orderedValues){
                _sb.append(vv.getValueHolder().toString() + " ");
            }
            _sb.append("-> " + (value ? 1 : 0) + "\n");
        }

        public String toString(){
            return _sb.toString();
        }
    }


}
