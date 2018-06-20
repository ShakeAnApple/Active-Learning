package config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigReader {
        /*

    in order type name possiblevalues;

    out order type name possiblevalues;

    connector
    sysname
    inPort
    outPort

    */


    public static Config read(String path){
        Config config = new Config();
        Path filePath = FileSystems.getDefault().getPath(path);
        try (InputStream in = Files.newInputStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            int a;
            StringBuilder sb = new StringBuilder();
            while((a = reader.read()) != -1){
                sb.append((char)a);
                String keyword = sb.toString().trim();

                boolean keywordProcessed = trySetInVar(config, reader, keyword) ||
                        trySetOutVar(config, reader, keyword) ||
                        trySetConnector(config, reader, keyword) ||
                        trySetInPort(config, reader, keyword) ||
                        trySetOutPort(config, reader, keyword) ||
                        trySetSysName(config, reader, keyword) ||
                        trySetWorkingDir(config, reader, keyword);

                if (keywordProcessed){
                    sb = new StringBuilder();
                }
            }

        } catch (IOException x) {
            System.err.println(x);
        }
        return config;
    }

    private static boolean trySetWorkingDir(Config config, BufferedReader reader, String keyword) throws IOException {
        if (!isWorkingDirDeclaration(keyword)){
            return false;
        }

        StringBuilder workingDirStr = new StringBuilder();
        char c;
        while (!isSeparator( (c = (char)reader.read()) )){
            checkUnexpectedEof(c);
            workingDirStr .append(c);
        }
        config.workingDir = workingDirStr.toString().trim();
        return true;
    }

    private static boolean trySetSysName(Config config, BufferedReader reader, String keyword) throws IOException {
        if (!isSysNameDeclaration(keyword)){
            return false;
        }

        StringBuilder sysNameStr = new StringBuilder();
        char c;
        while (!isSeparator( (c = (char)reader.read()) )){
            checkUnexpectedEof(c);
            sysNameStr.append(c);
        }
        config.sysName = sysNameStr.toString().trim();
        return true;
    }

    private static boolean trySetOutPort(Config config, BufferedReader reader, String keyword) throws IOException {
        if (!isOutPortDeclaration(keyword)) {
            return false;
        }

        StringBuilder outportStr = new StringBuilder();
        char c;
        while (!isSeparator( (c = (char)reader.read()) )){
            checkUnexpectedEof(c);
            outportStr.append(c);
        }
        config.outPort = outportStr.toString().trim();
        return true;
    }

    private static boolean trySetInPort(Config config, BufferedReader reader, String keyword) throws IOException {
        if (!isInPortDeclaration(keyword)){
            return false;
        }

        StringBuilder inportStr = new StringBuilder();
        char c;
        while (!isSeparator( (c = (char)reader.read()) )){
            checkUnexpectedEof(c);
            inportStr.append(c);
        }
        config.inPort = inportStr.toString().trim();
        return true;
    }

    private static boolean trySetConnector(Config config, BufferedReader reader, String keyword) throws IOException {
        if (!isConnectorDeclaration(keyword)) {
            return false;
        }
        StringBuilder connectorStr = new StringBuilder();
        char c;
        while (!isSeparator( (c = (char)reader.read()) )){
            checkUnexpectedEof(c);
            connectorStr.append(c);
        }
        config.connector = connectorStr.toString().trim();
        return true;
    }

    private static void checkUnexpectedEof(char c){
        if ((int)c == -1){
            throw new RuntimeException("Unexpected end of config file");
        }
    }

    private static boolean trySetInVar(Config config, BufferedReader reader, String keyword) throws IOException {
        if (!isInVarDeclaration(keyword)){
            return false;
        }

        StringBuilder varDeclarationStr = new StringBuilder();
        char c;
        while (!isSeparator( (c = (char)reader.read()) )){
            checkUnexpectedEof(c);
            varDeclarationStr.append(c);
        }
        config.inputVarStrings.add(varDeclarationStr.toString().trim());

        return true;
    }

    private static boolean trySetOutVar(Config config, BufferedReader reader, String keyword) throws IOException {
        if (!isOutVarDeclaration(keyword)){
            return false;
        }

        StringBuilder varDeclarationStr = new StringBuilder();
        char c;
        while (!isSeparator( (c = (char)reader.read()) )){
            checkUnexpectedEof(c);
            varDeclarationStr.append(c);
        }
        config.outputVarStrings.add(varDeclarationStr.toString().trim());

        return true;
    }

    private static boolean isSeparator(char c){
        return String.valueOf(c).equals(ConfigKeywords.SEPARATOR);
    }

    private static boolean isInVarDeclaration(String s){
        return s.equals(ConfigKeywords.IN_VAR);
    }

    private static boolean isOutVarDeclaration(String s){
        return s.equals(ConfigKeywords.OUT_VAR);
    }

    private static boolean isConnectorDeclaration(String s){
        return s.equals(ConfigKeywords.CONNECTOR);
    }

    private static boolean isInPortDeclaration(String s){
        return s.equals(ConfigKeywords.INPORT);
    }

    private static boolean isOutPortDeclaration(String s){
        return s.equals(ConfigKeywords.OUTPORT);
    }

    private static boolean isSysNameDeclaration(String s){
        return s.equals(ConfigKeywords.SYSNAME);
    }

    private static boolean isWorkingDirDeclaration(String s){
        return s.equals(ConfigKeywords.WORKINGDIR);
    }
}
