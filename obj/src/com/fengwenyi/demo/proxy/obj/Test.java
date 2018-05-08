package com.fengwenyi.demo.proxy.obj;

/**
 * @author Wenyi Feng
 */
public class Test {

    public static void main(String[] args) {
        Car car = new Car();

        LogProxy logProxy = new LogProxy(car);
        TimeProxy timeProxy = new TimeProxy(logProxy);

        timeProxy.move();
    }

}
