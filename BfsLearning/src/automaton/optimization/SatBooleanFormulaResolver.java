package automaton.optimization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SatBooleanFormulaResolver implements BooleanFormulaResolver {
    private static final String SATSOLVER_PATH = "C:\\soft\\cryptominisat\\cryptominisat5-win-amd64.exe";
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
        ParallelTaskRunner<SatResult> taskRunner = new ParallelTaskRunner<>(7, TimeUnit.SECONDS);
        List<Callable<SatResult>> satTasks = createSatTasks(truthTablePath);
        for (Callable<SatResult> satTask : satTasks){
            taskRunner.addTask(satTask);
        }
        List<SatResult> results = taskRunner.getResults(true);
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
        ProcessRunner runner = new ProcessRunner();
        ProcessRunner.StringProcessOutput output = runner.run(TT2BF_PATH, "-i", truthTablePath, "-Pmin", p,
                "-Pmax", p, "--sat-solver", SATSOLVER_PATH);

        Matcher m = Pattern.compile("Boolean formula: (.*)").matcher(output.getStdOut());
        if (m.find()){
            String formula = m.group(1);
            return new SatResult(formula, Integer.valueOf(p));
        }
        return null;
    }

//    private <T> T taskRunner(Callable<T> action){
//        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
//        List<Future<SatResult>> results = new ArrayList<>();
//        for (int i = _pmax; i >= _pmin; i--) {
//            final String p = String.valueOf(i);
//            results.add(pool.submit(() -> {
//                SatResult res = runSatWithParameter(truthTablePath, p);
//                if (res != null){
//                    return res;
//                }
//                pool.shutdown();
//                return null;
//            }));
//        }
//        SatResult res = null;
//        for (Future<SatResult> future: results)
//            try {
//                SatResult tmpRes = future.get();
//                if (res == null) {
//                    res = tmpRes;
//                } else if (tmpRes != null && tmpRes.getP() < res.getP()) {
//                    res = tmpRes;
//                }
//            } catch (InterruptedException | ExecutionException ignored) {
//            }
//
//        return res.getFormula();

//    }
}
