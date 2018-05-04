package config;

import values.AbstractValueHandler;
import values.BooleanValueHandler;
import values.IntervalValueHandler;

import java.util.HashMap;

public class ValueHandlerFabric {
    private final HashMap<String, AbstractValueHandler> _handlersByNames;

    public ValueHandlerFabric() {
        _handlersByNames = new HashMap<>();
        _handlersByNames.put(ConfigKeywords.REAL, new IntervalValueHandler());
        _handlersByNames.put(ConfigKeywords.BOOLEAN, new BooleanValueHandler());
    }

    public AbstractValueHandler getHandlerByName(String name){
        return _handlersByNames.get(name);
    }
}
