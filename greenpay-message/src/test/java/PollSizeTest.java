import org.junit.Before;
import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.util.Timer;
import java.util.TimerTask;

public class PollSizeTest {
    private volatile boolean expired;
    /**
     * Time (millis) of the test run in the CPU time calculation.
     */
    private final long elapsed = 3000;
    /**
     * Accuracy of test run. It must finish within 20ms of the testTime
     * otherwise we retry the test. This could be configurable.
     */
    private static final int EPSYLON = 15000;
    private Runnable runnable;
    @Before
    public void init(){
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private static long getCurrentThreadCPUTime(){
        return ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
    }
    @Test
    public void test(){
        long cputime = getCurrentThreadCPUTime();
        int runs = 0;
        long start = 0;
        do {
            if (++runs > 10) {
                throw new IllegalStateException("Test not accurate");
            }
            expired = false;
            start = System.currentTimeMillis();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    expired = true;
                }
            }, elapsed);
            while (!expired) {
                runnable.run();
            }
            start = System.currentTimeMillis() - start;
            timer.cancel();
        } while (Math.abs(start - elapsed) > EPSYLON);
        collectGarbage(3);

        cputime = getCurrentThreadCPUTime() - cputime;
        System.out.println(cputime);
    }
    private void collectGarbage(int times) {
        for (int i = 0; i < times; i++) {
            System.gc();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
