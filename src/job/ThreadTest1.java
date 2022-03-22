package job;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 懒汉模式下的单例
 */
public class ThreadTest1 {
    /** */
    private static int count = 0;

    private static ThreadTest1 _instance;

    private static int numOfThreads = 100;

    public static ThreadTest1 getInstance() {
        if (_instance == null) {
            _instance = new ThreadTest1();
        }
        return _instance;
    }

    private ThreadTest1() {
        count++;
    }

    public static void doTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
        for (int i = 0; i < numOfThreads; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(ThreadTest1.getInstance().count);
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
     *
     * @param args args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        while (ThreadTest1.getInstance().count <= 1) {
            reset();
            doTest();
        }
    }
}
