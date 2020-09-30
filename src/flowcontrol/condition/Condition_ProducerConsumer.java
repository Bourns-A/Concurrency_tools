package flowcontrol.condition;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
*describe: 使用condition实现生产者消费者模式
*
*@author HNH
*/
public class Condition_ProducerConsumer {
    private int queueSize = 10;
    private PriorityQueue<Integer> queue = new PriorityQueue<>(queueSize);
    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    public static void main(String[] args) {
        Condition_ProducerConsumer r1 = new Condition_ProducerConsumer();
        Producer producer = r1.new Producer();
        Consumer consumer = r1.new Consumer();
        producer.start();
        consumer.start();

    }

    class Consumer extends Thread {
        @Override
        public void run() {
            consume();
        }
        private void consume() {
            while(true) {
                lock.lock();
                try {
                    while (queue.size()==0) {
                        System.out.println("Empty Queue");
                        try {
                            notEmpty.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.poll();
                    notFull.signalAll();
                    System.out.println("got one entry from queue, "+queue.size()+" entries remaining");
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    class Producer extends Thread {
        @Override
        public void run() {
            produce();
        }
        private void produce() {
            while(true) {
                lock.lock();
                try {
                    while (queue.size()==queueSize) {
                        System.out.println("Full Queue");
                        try {
                            notFull.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.offer(1);
                    notEmpty.signalAll();
                    System.out.println("added one entry to queue, "+queue.size()+" entries remaining");
                } finally {
                    lock.unlock();
                }
            }
        }
    }

}
