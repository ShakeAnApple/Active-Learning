package automaton.optimization;

public class ExpressoBooleanFormulaResolver implements BooleanFormulaResolver {

    @Override
    public String resolve(String truthTablePath) {
        ProcessRunner runner = new ProcessRunner();
        // TODO !!!
        ProcessRunner.StringProcessOutput output = runner.run("python3", System.getProperty("user.dir") + "/lib/tt-minimization/main.py", truthTablePath);
        return output.getStdOut();
    }
}
