package atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
*describe: atomicInteger使用
*
*@author HNH
*/
public class AtomicInteger1 implements Runnable {
    private static final AtomicInteger atomicInteger = new AtomicInteger();
    public void incrementAtomic() {
        atomicInteger.getAndIncrement();
    }

    private static volatile int basicCount = 0;
    public void incrementBasic() {
        basicCount++;
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger1 r1 = new AtomicInteger1();
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r1);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("atomic: "+atomicInteger.get());
        System.out.println("Basic: "+basicCount);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            incrementAtomic();
            incrementBasic();
        }
    }
}
