package automaton.optimization;

import org.apache.commons.exec.CommandLine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SatBooleanFormulaResolver implements BooleanFormulaResolver {
//    private static final String SATSOLVER_PATH = "C:\\soft\\cryptominisat\\cryptominisat5-win-amd64.exe";
    private static final String SATSOLVER_PATH = "cryptominisat5";
    private final String TT2BF_PATH = "tt2bf";

    private class SatResult implements Comparable<SatResult>{
        private String _formula;
        private int _p;

        public SatResult(String formula, int p) {
            _formula = formula;
            _p = p;
        }

        public int getP() {
            return _p;
        }

        public String getFormula(){
            return _formula;
        }

        @Override
        public int compareTo(SatResult o) {
            return this._p - o._p;
        }
    }

    private int _pmin;
    private int _pmax;

    public SatBooleanFormulaResolver(int pmin, int pmax) {
        _pmax = pmax;
        _pmin = pmin;
    }

    @Override
    public String resolve(String truthTablePath) {
        ParallelTaskRunner<SatResult> taskRunner = new ParallelTaskRunner<>();
        List<Callable<SatResult>> satTasks = createSatTasks(truthTablePath);
        for (Callable<SatResult> satTask : satTasks){
            taskRunner.addTask(satTask);
        }
        List<SatResult> results = taskRunner.getResults(true);
        taskRunner.close();
        if (!results.isEmpty()){
            SatResult res = Collections.min(results);
            return res.getFormula();
        }
        return null;
    }

    private List<Callable<SatResult>> createSatTasks(String truthTablePath){
        List<Callable<SatResult>> tasks = new ArrayList<>();
        for (int i = _pmax; i >= _pmin; i--) {
            final String p = String.valueOf(i);
            tasks.add(() -> runSatWithParameter(truthTablePath, p));
        }
        return tasks;
    }

    private SatResult runSatWithParameter(String truthTablePath, String p){
        ProcessRunner runner = new ProcessRunner(7000);

        CommandLine cl = new CommandLine(TT2BF_PATH)
                .addArgument("-i").addArgument(truthTablePath)
                .addArgument("-Pmin").addArgument(p)
                .addArgument("-Pmax").addArgument(p)
                .addArgument("--sat-solver").addArgument(SATSOLVER_PATH);
        String output = runner.run(cl);

        Matcher m = Pattern.compile("Boolean formula: (.*)").matcher(output);
        if (m.find()){
            String formula = m.group(1);
            return new SatResult(formula, Integer.valueOf(p));
        }
        return null;
    }
}
