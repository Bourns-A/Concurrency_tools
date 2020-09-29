package flowcontrol.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
*describe:  多重检查示例：一等多
*
*@author HNH
*/
public class CountDownLatch1 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int number = i+1;
            Runnable  runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep((long) (Math.random() * 1000));
                        System.out.println("No" + number + "finished check");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        latch.countDown();
                    }
                }
            };
            executor.submit(runnable);
        }
        System.out.println("waiting for the examine");
        latch.await();
        System.out.println("Done");
    }
}
