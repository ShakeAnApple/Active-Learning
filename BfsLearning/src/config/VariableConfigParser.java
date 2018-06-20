package config;

import values.AbstractValueHolder;
import values.AbstractVariableInfo;

public interface VariableConfigParser<VType> {
    AbstractVariableInfo<AbstractValueHolder<VType>> tryParse(String s);
}
