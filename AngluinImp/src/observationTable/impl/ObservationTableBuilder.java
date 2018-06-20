package observationTable.impl;

import impl.Counterexample;
import values.Symbol;
import connection.*;
import observationTable.RequestSequence;
import observationTable.RequestSequenceItem;
import observationTable.abstr.Cell;
import simulation.SimulationService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ObservationTableBuilder {

    private final SimulationService _simulationService;
    private final List<Symbol> _inputAlphabet;

    private ObservationTable _observationTable;

    private Counterexample _counterexample;

    public ObservationTableBuilder(List<Symbol> inputAlphabet, SimulationService simService) throws Exception {
        _inputAlphabet = inputAlphabet;
        _simulationService = simService;

        _observationTable = new ObservationTable(_inputAlphabet);

        initTable(_inputAlphabet);
    }

    private void initTable(List<Symbol> _inputAlphabet) throws Exception {

        List<RequestQueryItem> requests = new ArrayList<RequestQueryItem>();

        RequestSequence colName = new RequestSequence(new RequestSequenceItem(_inputAlphabet.get(0), 0), true);

        Cell<RequestSequence, RequestSequence, Symbol>[] newStateCells = new Cell[1];
        RequestSequence emptyRequestSequence = new RequestSequence(new RequestSequenceItem(_inputAlphabet.get(0), 0), true);
        newStateCells[0]= new Cell(emptyRequestSequence, colName, null);

        RequestQueryItem req = new RequestQueryItem(emptyRequestSequence);

        requests.add(req);

        Cell<RequestSequence, RequestSequence, Symbol>[] newCells = new Cell[_inputAlphabet.size()];
        for (int i = 1; i < newCells.length + 1; i++) {
            Symbol symb = _inputAlphabet.get(i - 1);
            RequestSequenceItem seqItem = new RequestSequenceItem(symb);

            Cell cell = new Cell(new RequestSequence(seqItem), colName, null);
            newCells[i - 1]= cell;

            RequestQueryItem r = new RequestQueryItem(new RequestSequence(seqItem));

            requests.add(r);
        }

        Response startStateResp = _simulationService.getStartState(requests.get(0));
        List<Response> responses = _simulationService.sendQueries(requests);
        newStateCells[0].setValue(startStateResp.getResultSystemState().asSymbol());

        for (int i = 1; i < newCells.length + 1; i++) {
            newCells[i - 1].setValue(responses.get(i).getResultSystemState().asSymbol());
        }

        _observationTable.addCells(newStateCells, true);
        _observationTable.addCells(newCells, false);
    }


    private void processCounterexample() throws Exception {
        if (_counterexample == null){
            return;
        }

        RequestSequence counterxampleRequestSequence = new RequestSequence(
                _counterexample.getInput().getSymbols()
                        .stream()
                        .map(s -> new RequestSequenceItem(s))
                        .collect(Collectors.toList())
        );

        List<RequestSequence> newOneExtRequestSequences = new ArrayList<>();

        for (int i = 1; i < counterxampleRequestSequence.size() - 1; i++) {
            RequestSequence newRequestSequence = counterxampleRequestSequence.subtract(0, i);
            if (!_observationTable.rowExists(newRequestSequence)){
                newOneExtRequestSequences.add(newRequestSequence);
            }
        }

        RequestSequence[] suff = _observationTable.getColumnNames();
        int cellsCount = newOneExtRequestSequences.size() * suff.length;


        Cell<RequestSequence, RequestSequence, Symbol>[] newOneExtCells = new Cell[cellsCount];
        Cell<RequestSequence, RequestSequence, Symbol>[] newStateCells = new Cell[suff.length];

        RequestQueryItem[] requests = new RequestQueryItem[cellsCount + suff.length];

        for (int i = 0; i < suff.length; i++) {
            newStateCells[i] = new Cell(counterxampleRequestSequence, suff[i], null);
            if(!suff[i].isEmpty()) {
                requests[i] = new RequestQueryItem(counterxampleRequestSequence.concat(suff[i]));
            } else{
                requests[i] = new RequestQueryItem(counterxampleRequestSequence);
            }
        }

        for (int i = 0; i < requests.length - suff.length; i++) {
            RequestSequence curPref = newOneExtRequestSequences.get(i);
            for (int j = 0; j < suff.length; j++){
                newOneExtCells[suff.length*i + j] = new Cell(curPref, suff[j], null);
                if(!suff[j].isEmpty()) {
                    requests[suff.length * i + j + suff.length] = new RequestQueryItem(curPref.concat(suff[j]));
                } else{
                    requests[suff.length * i + j + suff.length] = new RequestQueryItem(curPref);
                }
            }
        }

        List<Response> responses = _simulationService.sendQueries(List.of(requests));

        for (int i = 0; i < suff.length; i++) {
            newStateCells[i].setValue(responses.get(i).getResultSystemState().asSymbol());
        }

        for (int i = 0; i < requests.length - suff.length; i++) {
            newOneExtCells[i].setValue(responses.get(i + suff.length).getResultSystemState().asSymbol());
        }

        _observationTable.addCells(newStateCells, true);
        _observationTable.addCells(newOneExtCells, false);
    }

    public void build() throws Exception {
        if (_counterexample != null){
            processCounterexample();
            _counterexample = null;
        }

//        List<RequestSequence> distinguishingSuffixes = _observationTable.checkConsistancy();
        List<RequestSequence> missingRowsNames = _observationTable.checkClosedness();

  //      while(!((distinguishingSuffixes.size() == 0) && (missingRowsNames.size() == 0))) {
        int ctr = 0;
        while(missingRowsNames.size() != 0) {
            ctr ++;
//            makeTableConsistent(distinguishingSuffixes);
            makeTableClosed(missingRowsNames);

            _observationTable.writeToFile("obsTable" + ctr + ".txt");

//            distinguishingSuffixes = _observationTable.checkConsistancy();
            missingRowsNames = _observationTable.checkClosedness();
            System.out.println("Missing rows count: " + missingRowsNames.size());
        }
    }

    private void makeTableConsistent(List<RequestSequence> distinguishingSuffixes) {

        if (distinguishingSuffixes.size() == 0) {
            return;
        }

        List<RequestSequence> allPrefixes = _observationTable.getAllRowsNames();

        Cell<RequestSequence, RequestSequence, Symbol>[] newCells = new Cell[allPrefixes.size()*distinguishingSuffixes.size()];

        RequestQueryItem[] requests = new RequestQueryItem[allPrefixes.size()*distinguishingSuffixes.size()];

        for (int i = 0; i < allPrefixes.size(); i++) {
            RequestSequence curPref = allPrefixes.get(i);
            for (int j = 0; j < distinguishingSuffixes.size(); j++){
                newCells[distinguishingSuffixes.size()*i + j] = new Cell(curPref, distinguishingSuffixes.get(j), null);
                requests[distinguishingSuffixes.size()*i + j] = new RequestQueryItem(curPref.concat(distinguishingSuffixes.get(j)));
            }
        }

        List<Response> responses = _simulationService.sendQueries(List.of(requests));
        for (int i = 0; i < responses.size(); i++) {
            newCells[i].setValue(responses.get(i).getResultSystemState().asSymbol());
        }

        try {
            _observationTable.addCellsToExistingRows(newCells);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeTableClosed(List<RequestSequence> missingRowsNames) {

        if (missingRowsNames.size() == 0) {
            return;
        }

        List<RequestSequence> newPrefixes = new ArrayList<>();

        for (RequestSequence rowName: missingRowsNames) {
            _observationTable.moveRowToStates(rowName);

            for (Symbol s : _inputAlphabet) {
                newPrefixes.add(rowName.concat(new RequestSequenceItem(s)));
            }
        }

        RequestSequence[] suff = _observationTable.getColumnNames();

        Cell<RequestSequence, RequestSequence, Symbol>[] newCells = new Cell[newPrefixes.size()*suff.length];

        RequestQueryItem[] requests = new RequestQueryItem[newPrefixes.size()*suff.length];

        for (int i = 0; i < newPrefixes.size(); i++) {
            RequestSequence curPref = newPrefixes.get(i);
            for (int j = 0; j < suff.length; j++){
                newCells[suff.length*i + j] = new Cell(curPref, suff[j], null);
                if(!suff[j].isEmpty()) {
                    requests[suff.length * i + j] = new RequestQueryItem(curPref.concat(suff[j]));
                } else{
                    requests[suff.length * i + j] = new RequestQueryItem(curPref);
                }
            }
        }

        List<Response> responses = _simulationService.sendQueries(List.of(requests));
        for (int i = 0; i < responses.size(); i++) {
            newCells[i].setValue(responses.get(i).getResultSystemState().asSymbol());
        }

        _observationTable.addCells(newCells, false);
    }

    public ObservationTable getTable() {
        return _observationTable;
    }

    public void setCounterexample(Counterexample counterexample) {
        _counterexample = counterexample;
    }

}
