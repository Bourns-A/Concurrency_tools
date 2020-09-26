package Lock.lock;
//用trylock避免死锁

import java.awt.print.Pageable;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockDeadLock implements Runnable{

    int flag = 1;
    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        TryLockDeadLock tryLockDeadLock1 = new TryLockDeadLock();
        TryLockDeadLock tryLockDeadLock2 = new TryLockDeadLock();
        tryLockDeadLock1.flag = 1;
        tryLockDeadLock2.flag = 2;
        new Thread(tryLockDeadLock1).start();
        new Thread(tryLockDeadLock2).start();

    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (flag==1) {
                try {
                    if (lock1.tryLock(800, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println("Thread 1 get lock 1");
                            Thread.sleep(new Random().nextInt(1000));
                            if(lock2.tryLock(800, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println("Thread 1 get lock 2");
                                    System.out.println("Thread 1 get 2 locks");
                                    break;
                                }finally {
                                    lock2.unlock();
                                }
                            } else {
                                System.out.println("Thread 1 Failed in acquire the lock 2, retrying");
                            }
                        } finally {
                            lock1.unlock();
                            Thread.sleep(new Random().nextInt(1000));
                        }
                    } else {
                        System.out.println("Thread 1 Failed in acquire the lock 1, retrying");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (flag==2) {
                try {
                    if (lock2.tryLock(3000, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println("Thread 2 get lock 2");
                            Thread.sleep(new Random().nextInt(1000));
                            if(lock1.tryLock(3000, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println("Thread 2 get lock 1");
                                    System.out.println("Thread 2 get 2 locks");
                                    break;
                                }finally {
                                    lock1.unlock();
                                }
                            } else {
                                System.out.println("Thread 2 Failed in acquire the lock 1, retrying");
                            }
                        } finally {
                            lock2.unlock();
                            Thread.sleep(new Random().nextInt(1000));
                        }
                    } else {
                        System.out.println("Thread 2 Failed in acquire the lock 2, retrying");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
