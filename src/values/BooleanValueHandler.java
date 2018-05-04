package values;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class BooleanValueHandler extends AbstractValueHandler<Boolean> {
    private boolean _value;

    public BooleanValueHandler(){}

    public BooleanValueHandler(boolean value) {
        _value = value;
    }

    @Override
    protected void parseAndSetImpl(Object val) {
        if (val instanceof Boolean){
            _value = (Boolean)(val);
        } else if(val instanceof boolean[]) {
            boolean[] resultsAr = (boolean[]) val;
            _value = resultsAr[resultsAr.length - 1];
        } else if (val instanceof String){
            if (((String) val).length() > 1){
                _value = Boolean.parseBoolean((String) val);
            } else {
                _value = "1".equalsIgnoreCase((String)val);
            }
        }
    }

    @Override
    public AbstractValueHandler<Boolean> clone() {
        return new BooleanValueHandler(_value);
    }

    @Override
    protected VariableInfo<AbstractValueHandler<Boolean>> tryParseImpl(String val) {
        // order type name a b c d;
        String[] sMembers = val.split(" ");

        int order = Integer.parseInt(sMembers[0]);
        String name = sMembers[2];

        List<AbstractValueHandler<Boolean>> possibleValues = new ArrayList<>();
        for (int i = 3; i < sMembers.length; i++) {
            possibleValues.add(new BooleanValueHandler(Boolean.parseBoolean(sMembers[i].replace(";",""))));
        }
        return new VariableInfo<AbstractValueHandler<Boolean>>(name, order, possibleValues, BooleanValueHandler::new);
    }

    @Override
    public String toString() {
        return ((Integer)(_value ? 1 : 0)).toString();
    }

    @Override
    public boolean equals(Object obj) {
        return _value == ((BooleanValueHandler)obj)._value;
    }
}
