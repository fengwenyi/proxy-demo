package com.fengwenyi.demo.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Wenyi Feng
 */
public class TimeHandler implements InvocationHandler {

    private Object target;

    public TimeHandler (Object target) {
        super();
        this.target = target;
    }

    /**
     *
     * @param proxy 被代理对象
     * @param method 被代理的方法
     * @param args 被理的参数
     * @return Object方法的返回值
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("行驶前");
        long startTime = System.currentTimeMillis();

        method.invoke(target);

        long endTime = System.currentTimeMillis();
        System.out.println("行驶结束");
        long time = endTime - startTime;
        System.out.println("行驶的时间 " + time + "ms");

        return null;
    }
}
