package config;

import values.AbstractValueHandler;
import values.VariableInfo;

import java.util.ArrayList;
import java.util.List;

public class ConfigParser {

    private final ValueHandlerFabric _handlerFabric;

    public ConfigParser(ValueHandlerFabric handlerFabric){
        _handlerFabric = handlerFabric;
    }

    public AbstractContext parse(Config config){
        switch(config.connector){
            case ConfigKeywords.MATLAB_CONNECTOR:{
                return parseMatlabConfig(config);
            }
            case  ConfigKeywords.NXT_CONNECTOR:{
                return parseNxtConfig(config);
            }
            default: {
                throw new RuntimeException("unsupported connector " + config.connector);
            }
        }
    }

    private NxtContext parseNxtConfig(Config config){
        List<VariableInfo> inputVars = parseVars(config.inputVarStrings);
        List<VariableInfo> outputVars = parseVars(config.outputVarStrings);

        NxtContext context = new NxtContext(inputVars, outputVars, Integer.parseInt(config.inPort), Integer.parseInt(config.outPort));
        return context;
    }

    private MatlabContext parseMatlabConfig(Config config){
        List<VariableInfo> inputVars = parseVars(config.inputVarStrings);
        List<VariableInfo> outputVars = parseVars(config.outputVarStrings);

        MatlabContext context = new MatlabContext(inputVars, outputVars, config.workingDir, config.sysName);
        return context;
    }

    private List<VariableInfo> parseVars(List<String> varStrings){
        List<VariableInfo> res = new ArrayList<>();
        for (String s: varStrings){
            String[] sMembers = s.split(" ");
            AbstractValueHandler handler = _handlerFabric.getHandlerByName(sMembers[1]);
            res.add(handler.tryParse(s));
        }
        return res;
    }
}
