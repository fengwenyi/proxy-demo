package com.fengwenyi.demo.proxy.obj;

/**
 * @author Wenyi Feng
 */
public class Agency extends Car {

    @Override
    public void move() {
        System.out.println("行驶前");
        long startTime = System.currentTimeMillis();

        super.move();

        long endTime = System.currentTimeMillis();
        System.out.println("行驶结束");
        long time = endTime - startTime;
        System.out.println("行驶的时间 " + time + "ms");
    }
}
