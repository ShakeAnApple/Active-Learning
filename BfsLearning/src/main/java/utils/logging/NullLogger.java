package utils.logging;

public class NullLogger implements Logger {
    @Override
    public void write(String msg) {
    }

    @Override
    public void writeln(String msg) {
    }
}
