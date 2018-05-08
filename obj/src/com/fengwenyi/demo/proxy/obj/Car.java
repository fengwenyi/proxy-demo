package com.fengwenyi.demo.proxy.obj;

import java.util.Random;

/**
 * @author Wenyi Feng
 */
public class Car implements IMoveable {

    @Override
    public void move() {
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("行驶中……");
    }
}
