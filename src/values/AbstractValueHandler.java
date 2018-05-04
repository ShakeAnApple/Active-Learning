package values;


import config.VariableConfigParser;

public abstract class AbstractValueHandler<VType> implements VariableConfigParser<VType> {

    public void parseAndSetValue(Object val){
        this.parseAndSetImpl(val);
    }
    protected abstract void parseAndSetImpl(Object val);

    public abstract AbstractValueHandler<VType> clone();

    @Override
    public VariableInfo<AbstractValueHandler<VType>> tryParse(String s){
        return this.tryParseImpl(s);
    }
    protected abstract VariableInfo<AbstractValueHandler<VType>> tryParseImpl(String s);
}
