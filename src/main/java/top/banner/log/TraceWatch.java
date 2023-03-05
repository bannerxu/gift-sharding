package top.banner.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TraceWatch<T> {
    private static final Logger log = LoggerFactory.getLogger(TraceWatch.class);

    private final Class<T> clazz;
    /**
     * Start time of the current task.
     */
    private Long startMs;
    /**
     * Name of the current task.
     */
    private String currentTaskName;

    public TraceWatch(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * 开始时间差类型指标记录，如果需要终止，请调用 {@link #stop(String)}
     *
     * @param taskName 指标名
     */
    public void start(String taskName) throws IllegalStateException {
        if (this.currentTaskName != null) {
            throw new IllegalStateException("Can't start TraceWatch: it's already running");
        }
        this.currentTaskName = taskName;
        this.startMs = System.currentTimeMillis();
    }

    /**
     * 终止时间差类型指标记录，调用前请确保已经调用 {@link #start(String)}
     */
    public void stop(String mark) throws IllegalStateException {
        if (this.currentTaskName == null) {
            throw new IllegalStateException("Can't stop TraceWatch: it's not running");
        }
        final long stop = System.currentTimeMillis();
        long lastTime = stop - this.startMs;
        log.info("耗时监控 ==> [{}.{}()] => [{}] => [耗时：{}ms] => 开始时间：{} ~ 结束时间：{}", clazz.getSimpleName(), currentTaskName, mark, lastTime, startMs, stop);

        this.currentTaskName = null;
    }

    /**
     * 终止时间差类型指标记录，调用前请确保已经调用 {@link #start(String)}
     */
    public void stop() throws IllegalStateException {
        stop("");
    }


}