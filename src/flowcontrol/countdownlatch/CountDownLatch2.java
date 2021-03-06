package flowcontrol.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
*describe:多等一
*
*@author HNH
*/
public class CountDownLatch2 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int no = i+1;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println("Np" + no + "ready");
                    try {
                        latch.await();
                        System.out.println("begin");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            service.submit(runnable);
        }
        Thread.sleep(1000);
        System.out.println("start");
        latch.countDown();
    }
}
