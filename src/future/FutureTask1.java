package future;

import java.util.concurrent.*;

/**
*describe: FutureTask的用法
*
*@author HNH
*/
public class FutureTask1 {
    public static void main(String[] args) {
        Task task = new Task();
        FutureTask<Integer> integerFutureTask = new FutureTask<>(task);
        //new Thread(integerFutureTask).start();
        ExecutorService service = Executors.newCachedThreadPool();
        service.submit(integerFutureTask);
        try {
            System.out.println("Task's outcome: "+ integerFutureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}

class Task implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("SubThread calculating");
        Thread.sleep(1000);
        int sum = 0;
        for (int i = 0; i <= 100; i++) {
            sum+=i;
        }
        return sum;
    }
}
