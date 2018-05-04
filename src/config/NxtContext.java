package config;

import values.VariableInfo;

import java.util.List;

public class NxtContext extends AbstractContext {
    private final int _inPort;
    private final int _outPort;

    public NxtContext(List<VariableInfo> inputVars, List<VariableInfo> outputVars, int inPort, int outPort) {
        super(inputVars, outputVars);
        _inPort = inPort;
        _outPort = outPort;

        super.setContext(this);
    }


    public int getInPort(){
        return _inPort;
    }

    public int getOutPort(){
        return _outPort;
    }
}
