package observationTable.abstr;

public class Cell<TRowName, TColName, TValue> {
    private TRowName _rowName;
    private TColName _colName;
    private TValue _value;

    public Cell(TRowName rowName, TColName colName, TValue value) {
        _rowName = rowName;
        _colName = colName;
        _value = value;
    }

    public TRowName getRowName(){
        return _rowName;
    }

    public TColName getColName(){
        return _colName;
    }

    public void setValue(TValue val){
        _value = val;
    }

    public TValue getValue() {
        return _value;
    }

    public boolean contentEquals(Cell other){
        return _value.equals(other._value) && _colName.equals(other._colName);
    }

    @Override
    public String toString() {
        return String.format("(col %1$s: val %2$s)",_colName,_value);
    }

    @Override
    public boolean equals(Object obj) {
        Cell other = (Cell)obj;
        return _value.equals(other._value) && _colName.equals(other._colName) && _rowName.equals(other._rowName);
    }
}
