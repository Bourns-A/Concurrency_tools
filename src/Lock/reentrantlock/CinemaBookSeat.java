package Lock.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

public class CinemaBookSeat {
    private static ReentrantLock lock = new ReentrantLock();



    public static void bookSeat() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+" booking the seat");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+" BOOKED the seat");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(()->bookSeat()).start();
        new Thread(()->bookSeat()).start();
        new Thread(()->bookSeat()).start();
        new Thread(()->bookSeat()).start();
    }
}
