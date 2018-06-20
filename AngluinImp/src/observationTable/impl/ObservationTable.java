package observationTable.impl;

import connection.RequestQueryItem;
import values.Symbol;
import observationTable.RequestSequence;
import observationTable.RequestSequenceItem;
import observationTable.abstr.Cell;
import observationTable.abstr.Row;
import utils.Tuple;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class ObservationTable {

    private Map<RequestSequence, Row<RequestSequence, RequestSequence, Symbol>> _states;
    private Map<RequestSequence, Row<RequestSequence, RequestSequence, Symbol>> _oneLetterExtensions;

    private HashSet<RequestSequence> _columnNames;
    
    private List<Symbol> _inputAlphabet;

    public ObservationTable(List<Symbol> inputAlphabet) {
        _states = new HashMap<>();
        _oneLetterExtensions = new HashMap<>();
        _columnNames = new HashSet<>();
        
        _inputAlphabet = inputAlphabet;
    }

    public List<Row<RequestSequence, RequestSequence, Symbol>> getStates(){
        return new ArrayList<>(_states.values());
    }

    public Map<String, Row<RequestSequence, RequestSequence, Symbol>> getAllRowsByName(){
        HashMap<String, Row<RequestSequence, RequestSequence, Symbol>> allRows = new HashMap<>();
        for (Row stRow: _states.values()) {
            allRows.put(stRow.getRowName().toString(), stRow);
        }
        for (Row expRow: _oneLetterExtensions.values()) {
            allRows.put(expRow.getRowName().toString(), expRow);
        }
        return allRows;
    }

    public boolean rowExists(RequestSequence rowName){
        return _states.containsKey(rowName) || _oneLetterExtensions.containsKey(rowName);
    }

    public List<RequestSequence> getAllRowsNames(){
        List<RequestSequence> res = new ArrayList<>(_states.keySet());
        res.addAll(_oneLetterExtensions.keySet());
        return res;
    }

    public void moveRowToStates(RequestSequence rowName){
        Row rowToMove = _oneLetterExtensions.get(rowName);
        _states.put(rowName, rowToMove);
        _oneLetterExtensions.remove(rowName);
    }

    public RequestSequence[] getColumnNames(){
        return _columnNames.toArray(new RequestSequence[_columnNames.size()]);
    }

    public void addCellsToExistingRows(Cell<RequestSequence, RequestSequence, Symbol>[] cells) throws Exception {
        for (int i = 0; i < cells.length; i++) {
            Cell<RequestSequence,RequestSequence, Symbol> curCell = cells[i];

            RequestSequence colName = curCell.getColName();
            _columnNames.add(colName);

            RequestSequence rowName = (RequestSequence)curCell.getRowName();

            Row<RequestSequence, RequestSequence, Symbol> row = _states.get(rowName) != null ? _oneLetterExtensions.get(rowName) : null;

            if (row == null){
                throw new IllegalArgumentException("No row for the cell");
            }
            row.addCell(curCell);
        }
    }

    public void addCells(Cell<RequestSequence, RequestSequence, Symbol>[] cells, boolean toStates) {
        if (toStates){
            addCells(cells, _states);
        } else {
            addCells(cells, _oneLetterExtensions);
        }
    }

    private void addCells(Cell<RequestSequence, RequestSequence, Symbol>[] cells, Map<RequestSequence, Row<RequestSequence, RequestSequence, Symbol>> rowsPart){
        for (int i = 0; i < cells.length; i++) {
            Cell<RequestSequence, RequestSequence, Symbol> curCell = cells[i];

            RequestSequence colName = curCell.getColName();
            _columnNames.add(colName);

            RequestSequence rowName = curCell.getRowName();

            if (rowsPart.get(rowName) == null){
                rowsPart.put(rowName, new Row<RequestSequence, RequestSequence, Symbol>(rowName, new ArrayList<Cell<RequestSequence, RequestSequence, Symbol>>()));
            }

            rowsPart.get(rowName).addCell(curCell);
        }
    }

    // get rownames of the rows from 1letter extension part that are not introduced in states part
    public List<RequestSequence> checkClosedness(){
        List<RequestSequence> missingRows = new ArrayList<>();
        int checkedCtr = 0;
        int extSize = _oneLetterExtensions.values().size();
        for (Row<RequestSequence, RequestSequence, Symbol> rowExt: _oneLetterExtensions.values()) {
            boolean equalFound = false;
            for (Row rowState: _states.values()) {
                if (rowExt.contentEqualsTo(rowState)){
                    equalFound = true;
                    break;
                }
            }
            checkedCtr ++;
            System.out.println("Checked " + checkedCtr + " out of " + extSize);
            if (!equalFound){
                missingRows.add(rowExt.getRowName());
            }
        }

        return  missingRows;
    }

    public List<RequestSequence> checkConsistancy(){
        if (_states.size() <= 1){
            return new ArrayList<>();
        }

        HashSet<RequestSequence> diffSuffixes = new HashSet<>();

        List<Tuple<RequestSequence, RequestSequence>> equalStatesPairs = new ArrayList<>();
        Row[] stateRowsArr = _states.values().toArray(new Row[_states.values().size()]);

        for (int i = 0; i < _states.values().size() - 1; i++) {
            Row firstRow = stateRowsArr[i];
            for (int j = i + 1; j < _states.values().size(); j++) {
                Row secondRow = stateRowsArr[j];
                if (firstRow.contentEqualsTo(secondRow)){
                    equalStatesPairs.add(new Tuple(firstRow.getRowName(), secondRow.getRowName()));
                }
            }
        }

        for (Tuple<RequestSequence, RequestSequence> pair: equalStatesPairs) {
            for (Symbol symb: _inputAlphabet) {
                RequestSequenceItem req = new RequestSequenceItem(symb);
                RequestSequence rowName1 = pair.getObj1().concat(req);
                RequestSequence rowName2 = pair.getObj2().concat(req);

                Row row1 = _states.get(rowName1) != null ? _states.get(rowName1) : _oneLetterExtensions.get(rowName1);
                Row row2 = _states.get(rowName2) != null ? _states.get(rowName2) : _oneLetterExtensions.get(rowName2);

                if ((row1 == null) || (row2 == null)){
                    //Todo chto-to poshlo ne tak
                    continue;
                }

                if(!row1.contentEqualsTo(row2)){
                    List<Cell> row1Cells = row1.getCells();
                    for (Cell<RequestSequence, RequestSequence, Symbol> row1Cell : row1Cells) {
                        if (row1Cell.getValue().equals(row2.getCell(row1Cell.getColName()).getValue())){
                            diffSuffixes.add(new RequestSequence(req).concat(row1Cell.getColName()));
                        }
                    }
                }
            }
        }

        return new ArrayList<>(diffSuffixes);
    }

    public void writeToFile(String path){
        StringBuilder sb = new StringBuilder();
        Row<RequestSequence, RequestSequence, Symbol> exampleRow = _states.values().stream().findFirst().get();

        sb.append("Request: (" + String.join(",",exampleRow.getRowName().getRequestItems().get(0).getSymbol().getVariablesValuesNames()) + ") \n");
        sb.append("Response: (" + String.join(",",exampleRow.getCells().get(0).getValue().getVariablesValuesNames()) + ") \n");
        sb.append("States: \n");

        for (Row<RequestSequence, RequestSequence, Symbol> row : _states.values()) {
            sb.append(String.format("%1$s \n", row));
        }
        sb.append("\n\nExperiments: \n");
        for (Row row : _oneLetterExtensions.values()) {
            sb.append(String.format("%1$s \n", row));
        }

        try {
            Files.write( Paths.get(path), sb.toString().getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return super.toString();
//        Row<RequestSequence, RequestSequence, Symbol> exampleRow = _states.values().stream().findFirst().get();
//
//        String result = "Request: (" + String.join(",",exampleRow.getRowName().getRequestItems().get(0).getSymbol().getVariablesValuesNames()) + ") \n";
//        result += "Response: (" + String.join(",",exampleRow.getCells().get(0).getValue().getVariablesValuesNames()) + ") \n";
//        result += "States: \n";
//
//        for (Row<RequestSequence, RequestSequence, Symbol> row : _states.values()) {
////            result += String.format("[rowName: %1$s; cells: %2$s]", _rowName, Arrays.toString(_cells.values().toArray()));
//             result += String.format("%1$s \n", row);
//        }
//        result += "\n\nExperiments: \n";
//        for (Row row : _oneLetterExtensions.values()) {
//            result += String.format("%1$s \n", row);
//        }
//        return result;
    }
}
