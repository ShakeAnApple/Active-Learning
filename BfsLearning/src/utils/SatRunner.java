package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SatRunner {
    private class SatResult{
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
    }

    private int _pmin;
    private int _pmax;

    public SatRunner(int pmin, int pmax) {
        _pmax = pmax;
        _pmin = pmin;
    }

    private String getInputAsString(InputStream is) throws IOException, InterruptedException {
//        try(Scanner s = new Scanner(is))
//        {
//            return s.useDelimiter("\\A").hasNext() ? s.next() : "";
//        }
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(is));
        StringBuilder builder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append(System.getProperty("line.separator"));
        }
        return builder.toString();
    }

    private SatResult runSat(String truthTablePath, String p){
        ProcessBuilder processBuilder = new ProcessBuilder("C:\\Program Files\\Python36\\Scripts\\tt2bf.exe", "-i", truthTablePath, "-Pmin", p,
                "-Pmax", p, "--sat-solver", "C:\\Programs\\cryptominisat\\cryptominisat5-win-amd64.exe");
        String stdOut = "";
        String stdErr = "";
        Process process = null;
        try {
            process = processBuilder.start();
            stdOut = getInputAsString(process.getInputStream());
            stdErr = getInputAsString(process.getErrorStream());
            process.waitFor();
        } catch (InterruptedException | IOException e) {
            return null;
        } finally {
            if (process.isAlive()) {
                process.destroy();
            }
        }
        Matcher m = Pattern.compile("Boolean formula: (.*)").matcher(stdOut);
        if (m.find()){
            String formula = m.group(1);
            return new SatResult(formula, Integer.valueOf(p));
        }
        return null;
    }

    // from max to min, callback if exceeds
    public String computeResult(String truthTablePath) {
        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        pool.setKeepAliveTime(30, TimeUnit.SECONDS);
        List<Future<SatResult>> results = new ArrayList<>();
        for (int i = _pmax; i >= _pmin; i--) {
            final String p = String.valueOf(i);
             results.add(pool.submit(() -> {
                SatResult res = runSat(truthTablePath, p);
                if (res != null){
                    return res;
                }
                pool.shutdown();
                return null;
            }));
        }
        SatResult res = null;
        for (Future<SatResult> future: results)
            try {
                SatResult tmpRes = future.get();
                if (res == null) {
                    res = tmpRes;
                } else if (tmpRes != null && tmpRes.getP() < res.getP()) {
                    res = tmpRes;
                }
            } catch (InterruptedException | ExecutionException ignored) {
            }

        return res.getFormula();
    }
}
