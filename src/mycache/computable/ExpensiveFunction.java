package mycache.computable;
/**
*describe: 实现computebale接口的耗时计算类。不具有缓存能力。
*
*@author HNH
*/
public class ExpensiveFunction implements Computable<String, Integer> {

    @Override
    public Integer compute(String arg) throws Exception {
        Thread.sleep(5000);
        return Integer.valueOf(arg);
    }
}
