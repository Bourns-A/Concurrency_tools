package mycache.computable;
/**
*describe: 设计一个计算函数接口，用来代表耗时计算，每个计算器都要实现这个接口，这样就可以无侵入实现缓存功能。
*
*@author HNH
*/
public interface Computable<A, V> {
    V compute(A arg) throws Exception;
}
