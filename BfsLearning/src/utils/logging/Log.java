package utils.logging;

public class Log {
    private static Logger _logger = new NullLogger();

    private Log(){}

    public static void init(Logger logger){
        _logger = logger;
    }

    public static void msg(String str){
        _logger.writeln(str);
    }
}
