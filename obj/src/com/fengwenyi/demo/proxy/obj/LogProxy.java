package com.fengwenyi.demo.proxy.obj;

/**
 * @author Wenyi Feng
 */
public class LogProxy implements IMoveable {

    private IMoveable m;

    public LogProxy(IMoveable m) {
        this.m = m;
    }

    @Override
    public void move() {
        System.out.println("开始记录日志");

        m.move();

        System.out.println("结束记录日志");
    }
}
