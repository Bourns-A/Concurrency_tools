package aqs;

import com.sun.corba.se.impl.orbutil.concurrent.Sync;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
*describe: 用AQS实现一个简易版countDownLatch
*
*@author HNH
*/
public class ShortLatch {
    private final Sync sync = new Sync();

    public void signal() {
        sync.releaseShared(0);
    }
    public void await() {
        sync.acquireShared(0);
    }

    private class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected int tryAcquireShared(int arg) {
            return (getState()==1)? 1:-1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            setState(1);
            return true;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ShortLatch shortLatch = new ShortLatch();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+ " trying to get the latch");
                    shortLatch.await();
                    System.out.println(Thread.currentThread().getName()+" go");
                }
            }).start();
        }
        Thread.sleep(5000);
        shortLatch.signal();
    }
}
