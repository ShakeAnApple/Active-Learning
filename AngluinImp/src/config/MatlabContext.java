package config;

import values.AbstractVariableInfo;

import java.util.List;

public class MatlabContext extends AbstractContext {
    private final String _workingDir;
    private final String _sysName;

    public MatlabContext(List<AbstractVariableInfo> inputVars, List<AbstractVariableInfo> outputVars, String workingDir, String sysName) {
        super(inputVars, outputVars);

        _workingDir = workingDir;
        _sysName = sysName;
    }

    public String getSysName() {
        return _sysName;
    }

    public String getWorkingDir() {
        return _workingDir;
    }
}
