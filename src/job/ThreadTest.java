package job;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程不安全的单例
 */
public class ThreadTest {
    /**
     *
     */
    private static int count = 0;

    /**
     *
     */
    private static ThreadTest _instance;

    private static int numOfThreads = 100;

    public ThreadTest() {
        count++;
    }

    public static void doTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
        for (int i = 0; i < numOfThreads; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    if (_instance == null) {
                        _instance = new ThreadTest();
                    }
                    System.out.println(ThreadTest.count);
                }
            });
        }
        Thread.sleep(5000);
        executorService.shutdown();
        System.out.println("-------------test end--------------");
    }

    public static void reset() {
        count = 0;
        _instance = null;
    }

    /**
     * @param args args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        while (ThreadTest.count <= 1) {
            reset();
            doTest();
        }
    }
}
