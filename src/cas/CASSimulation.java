package cas;
/**
*describe:模拟CAS的等价代码
*
*@author HNH
*/
public class CASSimulation {
    private volatile int value;
    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;
        if (oldValue == expectedValue) {
            value = newValue;
        }
        return oldValue;
    }
}
