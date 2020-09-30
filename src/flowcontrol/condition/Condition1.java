package flowcontrol.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
*describe: condition基本用法
*
*@author HNH
*/
public class Condition1 {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        Condition1 condition1 = new Condition1();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    condition1.method2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        condition1.method1();
    }

    void method1() throws InterruptedException {
        lock.lock();
        try {
            System.out.println("use await method");
            condition.await();
            System.out.println("continue");
        } finally {
            lock.unlock();
        }
    }

    void method2() {
        lock.lock();
        try {
            System.out.println("ready to weak others");
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

}
