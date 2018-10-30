package automaton.optimization;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelTaskRunner <TResult> {
    private final ThreadPoolExecutor _pool;
    private ScheduledExecutorService _canceller;
    private List<Future<TResult>> _futures;
    private boolean _timeoutRequired;
    private long _timeout;
    private TimeUnit _timeUnit;

    public ParallelTaskRunner() {
        _pool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        _pool.setKeepAliveTime(30, TimeUnit.SECONDS);
        _futures = new ArrayList<>();
    }

    public ParallelTaskRunner(long timeout, TimeUnit timeUnit) {
        this();
        _canceller = Executors.newSingleThreadScheduledExecutor();
        _timeout = timeout;
        _timeUnit = timeUnit;
        _timeoutRequired = true;
    }

    public void addTask(Callable<TResult> task){
        final Future<TResult> future = _pool.submit(task);
        _futures.add(future);

        if (_timeoutRequired){
            _canceller.schedule(() ->
                    future.cancel(true), _timeout, _timeUnit);
        }
    }

    public void addTask(Callable<TResult> task, long timeout, TimeUnit timeUnit){
        if (_canceller == null){
            _canceller = Executors.newSingleThreadScheduledExecutor();
        }

        final Future<TResult> future = _pool.submit(task);
        _canceller.schedule(() ->
                future.cancel(true), timeout, timeUnit);
    }

    public List<TResult> getResults(boolean ignoreExceptions){
        List<TResult> results = new ArrayList<>();
        for (Future<TResult> future: _futures)
            try {
                TResult res = future.get();
                if (res != null){
                    results.add(res);
                }
            } catch (CancellationException | InterruptedException | ExecutionException ex) {
                if (!ignoreExceptions){
                    ex.printStackTrace();
                }
            }
        return results;
    }
}
