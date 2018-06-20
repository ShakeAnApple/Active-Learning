package config;

import java.util.ArrayList;
import java.util.List;

public class Config {
    /*

    in order type name possiblevalues;

    out order type name possiblevalues;

    connector
    sysname
    inPort
    outPort

    */

    public Config(){
        inputVarStrings = new ArrayList<>();
        outputVarStrings = new ArrayList<>();
    }

    List<String> inputVarStrings;
    List<String> outputVarStrings;

    String connector;
    String sysName;
    String inPort;
    String outPort;
    String workingDir;


    // in vars
    // out vars

    // connector
}
