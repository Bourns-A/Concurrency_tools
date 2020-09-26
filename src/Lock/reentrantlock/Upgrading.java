package Lock.reentrantlock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Upgrading {
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(false);
    private static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    private static void readUpgrading() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到了读锁，正在读取");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + "upgrading will cause blocked");
            writeLock.lock();
            System.out.println(Thread.currentThread().getName() + "upgrading Success");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            System.out.println(Thread.currentThread().getName() + "释放读锁");
            readLock.unlock();
        }
    }

    private static void writeDowngrading() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到了写锁，正在写入");
            Thread.sleep(1000);
            readLock.lock();
            System.out.println("Downgrading to readLock");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
            System.out.println(Thread.currentThread().getName() + "释放写锁");
            writeLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        new Thread(()-> writeDowngrading(),"Thread1").start();
//        new Thread(()->readUpgrading(),"Thread2").start();
//        new Thread(()->readUpgrading(),"Thread3").start();
//        new Thread(()-> writeDowngrading(),"Thread4").start();
//        new Thread(()->readUpgrading(),"Thread5").start();
//        System.out.println("先演示降级是可以的");
//        Thread thread1 = new Thread(() -> writeDowngrading(), "Thread1");
//        thread1.start();
//        thread1.join();
//        System.out.println("------------------");
        System.out.println("演示升级是不行的");
        Thread thread2 = new Thread(() -> readUpgrading(), "Thread2");
        thread2.start();
    }
}
