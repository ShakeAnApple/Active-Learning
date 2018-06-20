package impl;

import automaton.Automaton;
import automaton.RequestResult;
import automaton.State;
import automaton.Transition;
import observationTable.RequestSequence;
import observationTable.RequestSequenceItem;
import observationTable.abstr.Cell;
import observationTable.abstr.Row;
import observationTable.impl.ObservationTable;
import utils.SerializationUtils;
import values.Symbol;
import values.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AutomatonBuilder {

    private ObservationTable _observationTable;
    private Automaton _automaton;

    public AutomatonBuilder(Automaton automaton) {
        _automaton = automaton;
    }

    public void build() throws Exception {

        _automaton.reset();

        Map<String,Row<RequestSequence, RequestSequence, Symbol>> allRows = _observationTable.getAllRowsByName();
        List<Row<RequestSequence, RequestSequence, Symbol>> stateRows = _observationTable.getStates();

        for (Row<RequestSequence, RequestSequence, Symbol> stateRow : stateRows) {
            State fromSt = convertToState(stateRow);
            _automaton.addState(fromSt);

            for (Symbol s: _automaton.getInputAlphabet()) {
                Row nextStateRow = allRows.get(stateRow.getRowName().concat(new RequestSequenceItem(s, 1)).toString());
                if (nextStateRow == null){
                    throw new Exception("chto-to ne tak s tablicei");
                }

                State toSt = convertToState(nextStateRow);
                _automaton.addState(toSt);

                Transition tr = new Transition(fromSt, toSt, s);
                _automaton.addTransition(tr);
            }
        }
        SerializationUtils.serializeAutomaton(_automaton, "automaton_gen.bin");
    }

    public void loadTable(ObservationTable observationTable){
        _observationTable = observationTable;
    }

    public Automaton getAutomaton() {
        return _automaton;
    }

    // TODO move to converter?
    private State convertToState(Row<RequestSequence, RequestSequence, Symbol> row){
        List<RequestResult> requestResults = new ArrayList<>();

        List<Cell<RequestSequence, RequestSequence, Symbol>> cells = row.getCells();
        for (int i = 0; i < cells.size(); i++){
            Cell<RequestSequence, RequestSequence, Symbol> cell = cells.get(i);
            requestResults.add(new RequestResult(cell.getValue(), i));
        }

//        List<StateValue> stateValues = new ArrayList<>();
//        for (Cell<Word, Word, Symbol> cell: row.getCells()) {
//            stateValues.add(new StateValue(cell.getColName(), cell.getValue()));
//        }
        State state = new State(Word.parse(row.getRowName()), requestResults, row.getRowName().isEmpty());
        return state;
    }
}
