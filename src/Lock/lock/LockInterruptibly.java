package Lock.lock;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockInterruptibly implements Runnable {
    private Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        LockInterruptibly lockInterruptibly = new LockInterruptibly();
        Thread thread1 = new Thread(lockInterruptibly);
        Thread thread2 = new Thread(lockInterruptibly);
        thread1.start();
        thread2.start();
    }

    @Override
    public void run() {
        System.out.printf(Thread.currentThread().getName()+ "is trying to get the lock");
        try {
            lock.lockInterruptibly();
            try {
                System.out.println(Thread.currentThread().getName() + "got the lock");
                Thread.sleep(5000);
            }catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "Interrupted while sleeping");
            }finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName()+"released the lock");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
