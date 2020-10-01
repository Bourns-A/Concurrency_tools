package future;

import java.util.Random;
import java.util.concurrent.*;

/**
*describe: 演示一个future对象的get方法，lambda形式。
*
*@author HNH
*/
public class OneFuture_lambda {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Callable<Integer> callable = ()->{
            Thread.sleep(3000);
            return new Random().nextInt();
        };
        Future<Integer> future = service.submit(new CallableTask());
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }

    static class CallableTask implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            Thread.sleep(3000);
            return new Random().nextInt();
        }
    }
}
