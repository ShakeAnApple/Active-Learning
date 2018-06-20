package values;

import config.VariableInfoDefinition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractVariableInfo<VHolderType extends AbstractValueHolder> implements Serializable {
    private String _name;
    protected List<VHolderType> _possibleValues;
    private int _order;

    protected AbstractVariableInfo(){}

    public AbstractVariableInfo(String name, int order, List<VHolderType> possibleValues, Supplier<VHolderType> supplier) {
        _name = name;
        _possibleValues = possibleValues;
        _order = order;
    }

    public AbstractVariableInfo(String name, int order, List<VHolderType> possibleValues) {
        _name = name;
        _possibleValues = possibleValues;
        _order = order;
    }

    protected void init(VariableInfoDefinition def){
        // order type name a b c d;
        _possibleValues = new ArrayList<>();

        _order = Integer.parseInt(def.getOrderStr());
        _name = def.getName();

        for (String valStr: def.getPossibleValuesStr()) {
            _possibleValues.add(this.tryParseConfigValue(valStr));
        }
    }


    public int getOrder() {
        return _order;
    }

    public abstract VHolderType tryParseValue(Object obj);

    public abstract VHolderType tryParseConfigValue(String s);

    public String getName(){
        return _name;
    }

    public List<VHolderType> getPossibleValues(){
        return _possibleValues;
    }
}
