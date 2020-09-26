package Lock.readwrite;
//演示公平锁和非公平锁的读写锁策略
import threadlocal.ThreadLocalNomalUsage;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class NonfairBarge {
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(false);
    private static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    public static void main(String[] args) {
        //头节点如果都是读取，会很快的同步执行完毕。所以写线程更可能作为等待队列的头节点。
        new Thread(() ->write(), "Thread1").start();
        new Thread(() ->read(), "Thread2").start();
        new Thread(() ->read(), "Thread3").start();
        new Thread(() ->write(), "Thread4").start();
        new Thread(() ->read(), "Thread5").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Thread thread[] = new Thread[1000];
                for (int i = 0; i < 1000; i++) {
                    thread[i] = new Thread(()->read(), "subThread created Thread "+i);
                }
                for (int i = 0; i < 1000; i++) {
                    thread[i].start();

                }
            }
        }).start();

    }

    private static void read() {
        System.out.println(Thread.currentThread().getName()+ " start trying to get readlock");
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+ " get readlock, readiong");
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName()+" released lock");
            readLock.unlock();
        }
    }

    private static void write() {
        System.out.println(Thread.currentThread().getName()+ " start trying to get writelock");
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+ " get writelock, writing");
            Thread.sleep(40);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName()+" released lock");
            writeLock.unlock();
        }
    }
}
