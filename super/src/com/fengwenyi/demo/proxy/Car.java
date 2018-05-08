package com.fengwenyi.demo.proxy;

/**
 * @author Wenyi Feng
 */
public class Car extends Moveable {

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
