package Lock.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CinemaReadWrite {
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    private static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    public static void main(String[] args) {
        new Thread(()->read(), "Thread 0").start();
        new Thread(()->read(), "Thread 1").start();

        new Thread(()->write(), "Thread 2").start();
        new Thread(()->write(), "Thread 3").start();
    }

    private static void read() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " got the readLock, reading");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " released readLock");
            readLock.unlock();
        }
    }

    private static void write() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " got the writeLock, Writing");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " released writeLock");
            writeLock.unlock();
        }
    }
}
