package observationTable.abstr;

import java.util.*;

public class Row<TRowName, TColName, TValue> {
    private TRowName _rowName;
    private Map<TColName, Cell<TRowName, TColName, TValue>> _cells;

    public Row(TRowName rowName, List<Cell<TRowName, TColName, TValue>> cells) {

        for (Cell cell: cells) {
            if (!cell.getRowName().equals(rowName)){
                throw new IllegalArgumentException("cells in row should have same rowName");
            }
        }

        _cells = new HashMap<>();

        for (Cell<TRowName, TColName, TValue> cell : cells) {
            _cells.put(cell.getColName(), cell);
        }
        _rowName = rowName;
    }

    public List<Cell<TRowName, TColName, TValue>> getCells(){
        return new ArrayList<>(_cells.values());
    }

    public Cell<TRowName, TColName, TValue> getCell(TColName colName){
        return _cells.get(colName);
    }

    public void addCell(Cell<TRowName, TColName, TValue> cell){
        if (!cell.getRowName().equals(_rowName)){
            throw new IllegalArgumentException("cells in row should have same rowName");
        }

        _cells.put(cell.getColName(), cell);
    }

    public TRowName getRowName(){
        return _rowName;
    }

    public boolean contentEqualsTo(Row other){
        if(this._cells.size() != other._cells.size()){
            return false;
        }

        for (TColName colName: _cells.keySet()) {
            Cell otherCell = (Cell)other._cells.get(colName);
            if (otherCell == null){
                return false;
            }

            if (!_cells.get(colName).contentEquals(otherCell)){
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return String.format("[rowName: %1$s; cells: %2$s]", _rowName, Arrays.toString(_cells.values().toArray()));
    }
}
