package job;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 饿汉模式下的单例
 */
public class ThreadTest2 {
    /** */
    public static int count = 0;

    private static ThreadTest2 _instance = new ThreadTest2();

    private static int numOfThreads = 100;

    public static ThreadTest2 getInstance() {
        return _instance;
    }

    private ThreadTest2() {
        count++;
    }

    public static void doTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
        for (int i = 0; i < numOfThreads; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(ThreadTest2.getInstance().count);
                }
            });
        }
        Thread.sleep(5000);
        executorService.shutdown();
        System.out.println("-------------test end--------------");
    }

    public static void reset() {
        count = 0;
        _instance = new ThreadTest2();
    }

    /**
     *
     * @param args args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        while (ThreadTest2.getInstance().count <= 1) {
            reset();
            doTest();
        }
    }
}
