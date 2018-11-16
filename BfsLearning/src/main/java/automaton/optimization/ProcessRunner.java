package automaton.optimization;

import org.apache.commons.exec.*;
import utils.logging.Log;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ProcessRunner {

    private final long _timeOutMillis;

    public ProcessRunner(long timeOutMillis) {
        _timeOutMillis = timeOutMillis;
    }

    public String run(CommandLine commandLine) {
        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
        ExecuteWatchdog watchdog = new ExecuteWatchdog(_timeOutMillis);
        Thread shutdownHook = new Thread(watchdog::destroyProcess);

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outStream);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setWatchdog(watchdog);
        executor.setStreamHandler(streamHandler);

        try {
            Runtime.getRuntime().addShutdownHook(shutdownHook);
            executor.execute(commandLine, resultHandler);
            resultHandler.waitFor();
            Runtime.getRuntime().removeShutdownHook(shutdownHook);
        } catch (InterruptedException | IOException e) {
            return null;
        }
        try {
            return outStream.toString("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
