package mycache;

import mycache.computable.Computable;
import mycache.computable.ExpensiveFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
*describe: 装饰者模式给计算器自动添加缓存功能，使用concurrentHashMap
*
*@author HNH
*/
public class MyCache4<A, V> implements Computable<A, V> {

    private final Map<A, V> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public MyCache4(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public  V compute(A arg) throws Exception {
        System.out.println("Enter the Cache");
        V result = cache.get(arg);
        if (result==null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        MyCache4<String, Integer> expensiveCompute = new MyCache4<>(new ExpensiveFunction());
        System.out.println("start");
        Integer result = expensiveCompute.compute("666");
        System.out.println("1st outcome: "+result);
        result = expensiveCompute.compute("666");
        System.out.println("2nd outcome: "+result);
    }

}
