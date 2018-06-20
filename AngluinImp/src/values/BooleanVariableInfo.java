package values;

import config.VariableInfoDefinition;

import java.io.Serializable;

public class BooleanVariableInfo extends AbstractVariableInfo<BooleanValueHolder> implements Serializable {

    public BooleanVariableInfo(VariableInfoDefinition def){
        init(def);
    }

    @Override
    public BooleanValueHolder tryParseValue(Object obj) {
        if (obj instanceof Boolean){
            return new BooleanValueHolder((Boolean)(obj));
        }
        if(obj instanceof boolean[]) {
            boolean[] resultsAr = (boolean[]) obj;
            return new BooleanValueHolder(resultsAr[resultsAr.length - 1]);
        }
        if (obj instanceof String){
            if (((String) obj).length() > 1){
                return new BooleanValueHolder(Boolean.parseBoolean((String) obj));
            } else {
                return new BooleanValueHolder("1".equalsIgnoreCase((String)obj));
            }
        }
        return null;
    }

    @Override
    public BooleanValueHolder tryParseConfigValue(String s) {
        return new BooleanValueHolder(Boolean.parseBoolean(s));
    }
}
