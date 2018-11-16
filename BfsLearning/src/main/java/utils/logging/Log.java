package utils.logging;

import java.io.IOException;

public class Log {
    private static Logger _logger = new NullLogger();

    private Log(){}

    public static void init(Logger logger){
        _logger = logger;
    }

    public static void msg(String str) {
        try {
            _logger.writeln(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
