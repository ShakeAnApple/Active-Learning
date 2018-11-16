package config;

import values.AbstractVariableInfo;

import java.util.List;

public class NxtContext extends AbstractContext {
    private final int _inPort;
    private final int _outPort;

    public NxtContext(List<AbstractVariableInfo> inputVars, List<AbstractVariableInfo> outputVars, int inPort, int outPort) {
        super(inputVars, outputVars);
        _inPort = inPort;
        _outPort = outPort;
    }


    public int getInPort(){
        return _inPort;
    }

    public int getOutPort(){
        return _outPort;
    }
}
