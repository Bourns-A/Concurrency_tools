package atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
*describe: 原子类升级
*
*@author HNH
*/

public class AtomicIntegerFieldUpgrade implements Runnable{
    static Candidate tom;
    static Candidate peter;

    public static AtomicIntegerFieldUpdater<Candidate> scoreUpdator =
            AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");

    public static void main(String[] args) throws InterruptedException {
        tom = new Candidate();
        peter = new Candidate();
        AtomicIntegerFieldUpgrade r = new AtomicIntegerFieldUpgrade();
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("peter: "+peter.score);
        System.out.println("tom: "+tom.score);

    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            peter.score++;
            scoreUpdator.getAndIncrement(tom);
        }
    }

    public static class Candidate {
        volatile int score;
    }
}
