package automaton.optimization;

import org.apache.commons.exec.CommandLine;

public class ExpressoBooleanFormulaResolver implements BooleanFormulaResolver {

    @Override
    public String resolve(String truthTablePath) {
        ProcessRunner runner = new ProcessRunner(7000);
        // TODO !!!
        CommandLine cl = new CommandLine("python3")
                .addArgument(System.getProperty("user.dir") + "/lib/tt-minimization/main.py").addArgument(truthTablePath);
        String output = runner.run(cl);

        return output;
    }
}
