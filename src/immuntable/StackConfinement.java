package immuntable;

public class StackConfinement implements Runnable{
    int index = 0;
    public void inThread() {
        int neverGoOut = 0;
        for (int i = 0; i < 10000; i++) {
            neverGoOut++;
        }
        System.out.println("neverGoOut: "+neverGoOut);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            index++;
        }
        inThread();
    }

    public static void main(String[] args) throws InterruptedException {
        StackConfinement stackConfinement = new StackConfinement();
        Thread thread1 = new Thread(stackConfinement);
        Thread thread2 = new Thread(stackConfinement);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(stackConfinement.index);
    }
}
