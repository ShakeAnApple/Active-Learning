package impl;

import automaton.Automaton;
import observationTable.impl.ObservationTableBuilder;
import simulation.SimulationService;

public class LearningService {

    private final ObservationTableBuilder _otBuilder;
    private final AutomatonBuilder _automatonBuilder;
    private final EquivalenceChecker _equivalenceChecker;

    private Automaton _hypothesis;

    public LearningService(SimulationService simService, Automaton hypothesis) throws Exception {
        _hypothesis = hypothesis;

        _automatonBuilder = new AutomatonBuilder(_hypothesis);
        _otBuilder = new ObservationTableBuilder(_hypothesis.getInputAlphabet(), simService);
        _equivalenceChecker = new EquivalenceChecker(simService, _hypothesis.getInputAlphabet());
    }


    public void stepForward(){
        try {
            _otBuilder.build();

            _automatonBuilder.loadTable(_otBuilder.getTable());
            _automatonBuilder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isReady(){
        try {
            Counterexample counterexample = _equivalenceChecker.Check(_automatonBuilder.getAutomaton(), 5);
            if (counterexample == null){
                return true;
            }

            _otBuilder.setCounterexample(counterexample);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
