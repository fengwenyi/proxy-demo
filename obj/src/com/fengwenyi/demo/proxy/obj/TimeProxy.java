package com.fengwenyi.demo.proxy.obj;

/**
 * @author Wenyi Feng
 */
public class TimeProxy implements IMoveable {

    private IMoveable m;

    public TimeProxy(IMoveable m) {
        this.m = m;
    }

    @Override
    public void move() {
        System.out.println("行驶前");
        long startTime = System.currentTimeMillis();

        m.move();

        long endTime = System.currentTimeMillis();
        System.out.println("行驶结束");
        long time = endTime - startTime;
        System.out.println("行驶的时间 " + time + "ms");
    }
}
