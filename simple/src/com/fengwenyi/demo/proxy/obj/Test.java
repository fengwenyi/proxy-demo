package com.fengwenyi.demo.proxy.obj;

/**
 * @author Wenyi Feng
 */
public class Test {

    public static void main(String[] args) {
        IMoveable moveable = new Agency();
        moveable.move();
    }

}
