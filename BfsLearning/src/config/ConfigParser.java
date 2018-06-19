package config;

import values.AbstractVariableInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigParser {

    private final VariableInfoFabric _varInfoFabric;

    public ConfigParser(VariableInfoFabric handlerFabric){
        _varInfoFabric = handlerFabric;
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
        List<AbstractVariableInfo> inputVars = parseVars(config.inputVarStrings);
        List<AbstractVariableInfo> outputVars = parseVars(config.outputVarStrings);

        NxtContext context = new NxtContext(inputVars, outputVars, Integer.parseInt(config.inPort), Integer.parseInt(config.outPort));
        return context;
    }

    private MatlabContext parseMatlabConfig(Config config){
        List<AbstractVariableInfo> inputVars = parseVars(config.inputVarStrings);
        List<AbstractVariableInfo> outputVars = parseVars(config.outputVarStrings);

        MatlabContext context = new MatlabContext(inputVars, outputVars, config.workingDir, config.sysName);
        return context;
    }

    private List<AbstractVariableInfo> parseVars(List<String> varStrings){
        List<AbstractVariableInfo> res = new ArrayList<>();
        for (String s: varStrings){
            String[] sMembers = s.replace(";", "").split(" ");
            VariableInfoDefinition varDef = new VariableInfoDefinition(sMembers[2], sMembers[1], sMembers[0], Arrays.copyOfRange(sMembers, 3, sMembers.length));
            AbstractVariableInfo varInfo = _varInfoFabric.create(varDef);
            res.add(varInfo);
        }
        return res;
    }
}
