package mycache;

import mycache.computable.Computable;
import mycache.computable.ExpensiveFunction;

import java.util.HashMap;
import java.util.Map;

/**
*describe: 装饰者模式给计算器自动添加缓存功能，减小锁的粒度。但是依旧并发不安全。
*
*@author HNH
*/
public class MyCache3<A, V> implements Computable<A, V> {

    private final Map<A, V> cache = new HashMap<>();
    private final Computable<A, V> c;

    public MyCache3(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public  V compute(A arg) throws Exception {
        System.out.println("Enter the Cache");
        V result = cache.get(arg);
        if (result==null) {
            result = c.compute(arg);
            synchronized (this) {
                cache.put(arg, result);
            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        MyCache3<String, Integer> expensiveCompute = new MyCache3<>(new ExpensiveFunction());
        System.out.println("start");
        Integer result = expensiveCompute.compute("666");
        System.out.println("1st outcome: "+result);
        result = expensiveCompute.compute("666");
        System.out.println("2nd outcome: "+result);
    }

}
