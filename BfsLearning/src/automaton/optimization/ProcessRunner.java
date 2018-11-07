package automaton.optimization;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessRunner {
    public class StringProcessOutput {
        private String _stdErr;
        private String _stdOut;

        public StringProcessOutput(InputStream stdOut, InputStream stdErr) throws IOException {
            _stdOut = getInputAsString(stdOut);
            _stdErr = getInputAsString(stdErr);
        }

        private String getInputAsString(InputStream is) throws IOException {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(is));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            return builder.toString();
        }

        public String getStdErr() {
            return _stdErr;
        }

        public String getStdOut() {
            return _stdOut;
        }
    }

    public StringProcessOutput run(String... command) {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = null;
        StringProcessOutput output = null;
        try {
            process = processBuilder.start();
            output = new StringProcessOutput(process.getInputStream(), process.getErrorStream());
            process.waitFor();
        } catch (InterruptedException | IOException e) {
            return null;
        } finally {
            if (process.isAlive()) {
                process.destroy();
            }
        }
        return output;
    }
}
