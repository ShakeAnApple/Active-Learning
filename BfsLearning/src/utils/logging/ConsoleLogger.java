package utils.logging;

public class ConsoleLogger implements Logger {
    @Override
    public void write(String msg) {
        System.out.print(msg);
    }

    @Override
    public void writeln(String msg) {
        System.out.println(msg);
    }
}
