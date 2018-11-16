package utils.logging;

import java.io.IOException;

public interface Logger {
    void write(String msg) throws IOException;
    void writeln(String msg) throws IOException;
}
