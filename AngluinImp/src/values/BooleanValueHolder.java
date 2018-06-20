package values;

import java.io.Serializable;

public class BooleanValueHolder extends AbstractValueHolder<Boolean> implements Serializable {
    private boolean _value;

    public BooleanValueHolder(boolean value) {
        _value = value;
    }

    @Override
    public Boolean getValue() {
        return _value;
    }

    @Override
    public AbstractValueHolder<Boolean> clone() {
        return new BooleanValueHolder(_value);
    }

    @Override
    public String toString() {
        return ((Integer)(_value ? 1 : 0)).toString();
    }

    @Override
    public boolean equals(Object obj) {
        return _value == ((BooleanValueHolder)obj)._value;
    }
}
