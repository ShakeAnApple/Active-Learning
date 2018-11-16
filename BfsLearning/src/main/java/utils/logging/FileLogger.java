package utils.logging;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileLogger implements Logger {

    private final Writer _writer;

    public FileLogger(String path) throws FileNotFoundException {
        _writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8));
    }

    @Override
    public void write(String msg) throws IOException {
        _writer.write(msg);
    }

    @Override
    public void writeln(String msg) throws IOException {
        _writer.write(msg + "\n");
    }
}
