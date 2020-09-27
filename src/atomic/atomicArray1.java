package atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class atomicArray1 {
    public static void main(String[] args) {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(1000);
        Thread[] threadIncrementer = new Thread[100];
        Thread[] threadDecrementer = new Thread[100];
        Incrementer incrementer = new Incrementer(atomicIntegerArray);
        Decrementer decrementer = new Decrementer(atomicIntegerArray);
        for (int i = 0; i < 100; i++) {
            threadDecrementer[i] = new Thread(decrementer);
            threadIncrementer[i] = new Thread(incrementer);
            threadDecrementer[i].start();
            threadIncrementer[i].start();
        }
        for (int i = 0; i < 100; i++) {
            try {
                threadDecrementer[i].join();
                threadIncrementer[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            if (atomicIntegerArray.get(i)!=0) {
                System.out.println("error at: "+i);
            }
        }
        System.out.println("Done");
    }
}

class Decrementer implements Runnable {
    private AtomicIntegerArray array;

    public Decrementer(AtomicIntegerArray array) {
        this.array = array;
    }

    @Override
    public void run() {
        for (int i = 0; i < array.length(); i++) {
            array.getAndDecrement(i);
        }
    }
}

class Incrementer implements Runnable {
    private AtomicIntegerArray array;

    public Incrementer(AtomicIntegerArray array) {
        this.array = array;
    }

    @Override
    public void run() {
        for (int i = 0; i < array.length(); i++) {
            array.getAndIncrement(i);
        }
    }
}