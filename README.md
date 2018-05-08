# proxy-demo

Learn proxy

## 写在前面的话

动态代理，很早就听过了，在很多框架源码中都能看到proxy的影子，显然他对于我们来说，很重要。学习代理基于两个方面的原因，我在这里分享一下。

* 一次面试，面试提出一个问题，有一个toString方法，我怎么在之前和之后做一些事情。当时我有些懵，其实，在实际开发中，这是可以办到的。

* 在一次Android公开课，讲师带领我们写网络访问框架，我突然意识到proxy真的很重要。

基于此，故来学习proxy，如果你还没有学习，那正好。如果你学习，但还是有些迷茫，不知道如何使用，那你也来看看吧。诚然我也是初学者，但这并不能阻止我们能一起探讨proxy的世界。

## 常见的代理模式

* 远程代理

* 虚拟代理

* 保护代理

* 智能引用代理

下面，通过静态代理和动态代理来实现能能引用代理代理

## 场景描述

我们记录一辆车在行驶过程中的一些数据，比如纪录日志，计算行驶时间……

## super

这个demo我们通过super这个关键字来实现

```
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
```

测试：
![super-test.png](https://upload-images.jianshu.io/upload_images/5805596-4e8d1e56cb196333.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

会写Android的兄弟都知道`super`这个家伙经常见到。

## simple

这是一个简单的静态代理实例

## obj

我们通过代理对象来实现

这里我们需要在行驶中，纪录日志。我们通过代理对象的方式来实现，显然日记代理类和时间时间代理类大致差不多，所以，我们来看下 `TimeProxy`。

```java
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
```

测试：

```java
    Car car = new Car();

    LogProxy logProxy = new LogProxy(car);
    TimeProxy timeProxy = new TimeProxy(logProxy);

    timeProxy.move();
```

![time-log.png](https://upload-images.jianshu.io/upload_images/5805596-e8a55949db945db8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 动态代理

后面再来

zzz~~~

## 资料

1、**[模式的秘密---代理模式](https://www.imooc.com/learn/214)**
