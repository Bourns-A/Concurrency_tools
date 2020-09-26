package Lock.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

public class Recursion {
    private static ReentrantLock lock = new ReentrantLock();
    private static void accessResource() {
        lock.lock();
        try{
            System.out.println("done");
            if (lock.getHoldCount()<5) {
                System.out.println(lock.getHoldCount());
                accessResource();
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        accessResource();
    }
}
