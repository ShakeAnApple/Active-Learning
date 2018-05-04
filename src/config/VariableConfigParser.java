package config;

import values.AbstractValueHandler;
import values.ValueHandler;
import values.VariableInfo;

public interface VariableConfigParser<VType> {
    VariableInfo<AbstractValueHandler<VType>> tryParse(String s);
}
