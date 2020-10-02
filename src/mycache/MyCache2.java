package mycache;

import mycache.computable.Computable;
import mycache.computable.ExpensiveFunction;

import java.util.HashMap;
import java.util.Map;

/**
*describe: 装饰者模式给计算器自动添加缓存功能
*
*@author HNH
*/
public class MyCache2<A, V> implements Computable<A, V> {

    private final Map<A, V> cache = new HashMap<>();
    private final Computable<A, V> c;

    public MyCache2(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public synchronized V compute(A arg) throws Exception {
        System.out.println("Enter the Cache");
        V result = cache.get(arg);
        if (result==null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        MyCache2<String, Integer> expensiveCompute = new MyCache2<>(new ExpensiveFunction());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer result = expensiveCompute.compute("666");
                    System.out.println(Thread.currentThread().getName()+"result: "+result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer result = expensiveCompute.compute("667");
                    System.out.println(Thread.currentThread().getName()+"result: "+result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer result = expensiveCompute.compute("666");
                    System.out.println(Thread.currentThread().getName()+"result: "+result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
