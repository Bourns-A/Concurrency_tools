package Lock.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//lock不会在异常时自动释放锁。
public class MustUnlock {
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        lock.lock();
        try {
            //获取本锁保护的资源
            System.out.println(Thread.currentThread().getName()+"job");
        } finally {
            lock.unlock();
        }
    }
}
