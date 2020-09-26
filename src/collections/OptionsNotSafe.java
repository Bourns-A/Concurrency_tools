package collections;

import java.util.concurrent.ConcurrentHashMap;

//组合操作不保证concurrentHashMap线程安全
public class OptionsNotSafe implements Runnable{
    private static ConcurrentHashMap<String, Integer> scores = new ConcurrentHashMap<String, Integer>();

    public static void main(String[] args) throws InterruptedException {
        scores.put("Ming", 0);
        Thread t1 = new Thread(new OptionsNotSafe());
        Thread t2 = new Thread(new OptionsNotSafe());

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(scores);
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            Integer score = scores.get("Ming");
            Integer newScore = score+1;
            scores.put("Ming", newScore);
        }
    }
}
