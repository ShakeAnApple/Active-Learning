package values;

import java.io.Serializable;

public class VariableValue<VHolderType extends AbstractValueHolder> implements Serializable {
    private VHolderType _valueHolder;
    private AbstractVariableInfo _varInfo;

    public VariableValue(AbstractVariableInfo varInfo) {
        _varInfo = varInfo;
    }

    public AbstractVariableInfo getVarInfo() {
        return _varInfo;
    }

    public VariableValue clone(){
        VariableValue res = new VariableValue<VHolderType>(_varInfo);
        res._valueHolder = _valueHolder.clone();
        return res;
    }

    public void setValue(VHolderType value){
        _valueHolder = value;
    }

    public void parseAndSetValue(Object val) throws Exception {
        _valueHolder = (VHolderType) _varInfo.tryParseValue(val);
    }

    public VHolderType getValue(){
        return _valueHolder;
    }

    @Override
    public String toString() {
        return String.format("%1$s: %2$s", _varInfo.getName(), _valueHolder);
    }

    @Override
    public boolean equals(Object obj) {
        VariableValue other = (VariableValue)obj;

        if (_varInfo.getName() != other.getVarInfo().getName()){
            return false;
        }

        return _valueHolder.equals(other._valueHolder);
    }
}
