package mycache;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
*describe: 最简单的缓存形式：HashMap
 * 线程不安全
 * 代码服用性差。
*
*@author HNH
*/

public class MyCache0 {
    private final HashMap<String, Integer> cache = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        MyCache0 myCache0 = new MyCache0();
        System.out.println("start");
        Integer result = myCache0.computer("13");
        System.out.println("1st outcome: "+result);
        result = myCache0.computer("13");
        System.out.println("2nd outcome: "+result);
    }

    public synchronized Integer computer(String userId) throws InterruptedException {
        Integer result = cache.get(userId);
        if (result==null) {
            //如果缓存中没有，那么现在需要计算结果，并保存至缓存.
            result = doCompute(userId);
            cache.put(userId, result);
        }
        return result;
    }

    private Integer doCompute(String userId) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        return new Integer(userId);
    }
}
