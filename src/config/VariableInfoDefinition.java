package config;

public class VariableInfoDefinition {
    private final String _name;
    private final String _typeStr;
    private final String _orderStr;
    private final String[] _possibleValuesStr;

    public VariableInfoDefinition(String name, String typeStr, String orderStr, String[] possibleValuesStr) {
        _name = name;
        _typeStr = typeStr;
        _orderStr = orderStr;
        _possibleValuesStr = possibleValuesStr;
    }

    public String getName() {
        return _name;
    }

    public String getTypeStr() {
        return _typeStr;
    }

    public String getOrderStr() {
        return _orderStr;
    }

    public String[] getPossibleValuesStr() {
        return _possibleValuesStr;
    }
}
