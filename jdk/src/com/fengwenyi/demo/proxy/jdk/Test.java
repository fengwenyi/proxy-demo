package com.fengwenyi.demo.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author Wenyi Feng
 */
public class Test {

    public static void main(String[] args) {
        Car car = new Car();
        InvocationHandler h = new TimeHandler(car);
        Class<?> cls = car.getClass();
        /**
         * newProxyInstance(ClassLoader loader,
         *                  Class<?>[] interfaces,
         *                  InvocationHandler h)
         * loader 类加载器
         * interfaces 实现接口
         * h InvocationHandler
         */
        IMoveable moveable = (IMoveable) Proxy.newProxyInstance(cls.getClassLoader(),
                                                                cls.getInterfaces(),
                                                                h);
        moveable.move();
    }

}
