package future;
import java.util.concurrent.*;

/**
*describe: 演示get的超时方法。
 * 超时后要调用cancel方法取消任务。
*
*@author HNH
*/
public class FutureTimeOut {
    private static final Ad DEFAULT_AD = new Ad("Ad with no connect");
    ExecutorService exec = Executors.newFixedThreadPool(10);

    static class Ad {
        String name;

        public Ad(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Ad{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    static class FetchAdTask implements Callable<Ad> {

        @Override
        public Ad call() throws Exception {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("Interupted while sleeping");
                e.printStackTrace();
                return new Ad("Ad with no connect");
            }
            return new Ad("got from internet");
        }
    }

    public void printAd() {
        Future<Ad> f = exec.submit(new FetchAdTask());
        Ad ad;
        try {
            ad = f.get(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            ad = new Ad("Ad with no connect");
            e.printStackTrace();
        } catch (ExecutionException e) {
            ad = new Ad("Ad with no connect");
            e.printStackTrace();
        } catch (TimeoutException e) {
            ad = new Ad("Ad with no connect");
            System.out.println("Connection Timeout");
            boolean cancel = f.cancel(false);
            System.out.println("Cancel Outcome: "+cancel);
        }
        exec.shutdown();
        System.out.println(ad);
    }

    public static void main(String[] args) {
        FutureTimeOut timeOut = new FutureTimeOut();
        timeOut.printAd();
    }
}
