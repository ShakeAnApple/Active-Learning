package config;

import values.VariableInfo;

import java.util.List;

public class MatlabContext extends AbstractContext{
    private final String _workingDir;
    private final String _sysName;

    public MatlabContext(List<VariableInfo> inputVars, List<VariableInfo> outputVars, String workingDir, String sysName) {
        super(inputVars, outputVars);

        _workingDir = workingDir;
        _sysName = sysName;

        super.setContext(this);
    }

    public String getSysName() {
        return _sysName;
    }

    public String getWorkingDir() {
        return _workingDir;
    }
}
