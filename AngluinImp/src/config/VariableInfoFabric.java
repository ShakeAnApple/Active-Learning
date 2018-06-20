package config;

import values.AbstractVariableInfo;
import values.BooleanVariableInfo;
import values.IntervalVariableInfo;

import java.util.HashMap;
import java.util.function.Function;

public class VariableInfoFabric {
    private final HashMap<String, Function<VariableInfoDefinition, AbstractVariableInfo>> _varInfoByTypes;

    public VariableInfoFabric() {
        _varInfoByTypes = new HashMap<>();
        _varInfoByTypes.put(ConfigKeywords.REAL, def -> new IntervalVariableInfo(def));
        _varInfoByTypes.put(ConfigKeywords.BOOLEAN, def -> new BooleanVariableInfo(def));
    }

    public AbstractVariableInfo create(VariableInfoDefinition def){
        return _varInfoByTypes.get(def.getTypeStr()).apply(def);
    }

}
