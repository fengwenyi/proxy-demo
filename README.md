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

上面的例子，是不是感觉我们写的代理很厉害，哈哈！现在有这样一个问题，我们的基类是Car，如果要有其他类，那应该怎么办呢？因此我们有必要学习一下动态代理。

我们先来写一个`TimeHandler`（注意：测试代码在资料里面，这里仅看部分代码）：

```java
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
```

我们再来写测试代码：

```java
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
```

就这样我们也就完成了jdk动态代理的实例代码。我们来总结一下：

1、创建一个实现接口InvocationHandler的类，并实现它的invoke方法。

2、创建被代理的类 和 接口。

3、调用Proxy的静态方法，创建一个静态代理类。

4、通过代理调用方法

显然，jdk动态代理类是基于接口的方法来实现代理。而类的代理是CGLIB。这样我们也就明白在代码里面写接口的用途了，是不是也能明白Spring AOP模块了，当然，可能他还是很复杂的。到学习Spring系列的时候，我们再来研究他。

## jdk动态代理

温馨提示：

jdk动态代理非常强大，著名的Spring AOP模块，我估计就基于此。所以，我们有必要花一定的时间来学一下源码，看一下Proxy是如何实现的。

我们首先来看 `newProxyInstance`：

```java
    public static Object newProxyInstance(ClassLoader loader,
                                          Class<?>[] interfaces,
                                          InvocationHandler h)
        throws IllegalArgumentException
    {
        Objects.requireNonNull(h);

        final Class<?>[] intfs = interfaces.clone();
        final SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            checkProxyAccess(Reflection.getCallerClass(), loader, intfs);
        }

        /*
         * Look up or generate the designated proxy class.
         */
        Class<?> cl = getProxyClass0(loader, intfs);

        /*
         * Invoke its constructor with the designated invocation handler.
         */
        try {
            if (sm != null) {
                checkNewProxyPermission(Reflection.getCallerClass(), cl);
            }

            final Constructor<?> cons = cl.getConstructor(constructorParams);
            final InvocationHandler ih = h;
            if (!Modifier.isPublic(cl.getModifiers())) {
                AccessController.doPrivileged(new PrivilegedAction<Void>() {
                    public Void run() {
                        cons.setAccessible(true);
                        return null;
                    }
                });
            }
            return cons.newInstance(new Object[]{h});
        } catch (IllegalAccessException|InstantiationException e) {
            throw new InternalError(e.toString(), e);
        } catch (InvocationTargetException e) {
            Throwable t = e.getCause();
            if (t instanceof RuntimeException) {
                throw (RuntimeException) t;
            } else {
                throw new InternalError(t.toString(), t);
            }
        } catch (NoSuchMethodException e) {
            throw new InternalError(e.toString(), e);
        }
    }

```

你一定留意到这一段代码`Class<?> cl = getProxyClass0(loader, intfs);`，显然是生成class的地方，我们来看下代码：

```java
    private static Class<?> getProxyClass0(ClassLoader loader,
                                           Class<?>... interfaces) {
        if (interfaces.length > 65535) {
            throw new IllegalArgumentException("interface limit exceeded");
        }

        // If the proxy class defined by the given loader implementing
        // the given interfaces exists, this will simply return the cached copy;
        // otherwise, it will create the proxy class via the ProxyClassFactory
        return proxyClassCache.get(loader, interfaces);
    }
```

显然，这里使用了缓存（WeakCache），这不是我们的重点，注意注释里面最后一个词 `ProxyClassFactory`，好吧我们去看一下：

```java
    /**
     * A factory function that generates, defines and returns the proxy class given
     * the ClassLoader and array of interfaces.
     */
    private static final class ProxyClassFactory
        implements BiFunction<ClassLoader, Class<?>[], Class<?>>
    {
        // prefix for all proxy class names
        private static final String proxyClassNamePrefix = "$Proxy";

        // next number to use for generation of unique proxy class names
        private static final AtomicLong nextUniqueNumber = new AtomicLong();

        @Override
        public Class<?> apply(ClassLoader loader, Class<?>[] interfaces) {

            Map<Class<?>, Boolean> interfaceSet = new IdentityHashMap<>(interfaces.length);
            for (Class<?> intf : interfaces) {
                /*
                 * Verify that the class loader resolves the name of this
                 * interface to the same Class object.
                 */
                Class<?> interfaceClass = null;
                try {
                    interfaceClass = Class.forName(intf.getName(), false, loader);
                } catch (ClassNotFoundException e) {
                }
                if (interfaceClass != intf) {
                    throw new IllegalArgumentException(
                        intf + " is not visible from class loader");
                }
                /*
                 * Verify that the Class object actually represents an
                 * interface.
                 */
                if (!interfaceClass.isInterface()) {
                    throw new IllegalArgumentException(
                        interfaceClass.getName() + " is not an interface");
                }
                /*
                 * Verify that this interface is not a duplicate.
                 */
                if (interfaceSet.put(interfaceClass, Boolean.TRUE) != null) {
                    throw new IllegalArgumentException(
                        "repeated interface: " + interfaceClass.getName());
                }
            }

            String proxyPkg = null;     // package to define proxy class in
            int accessFlags = Modifier.PUBLIC | Modifier.FINAL;

            /*
             * Record the package of a non-public proxy interface so that the
             * proxy class will be defined in the same package.  Verify that
             * all non-public proxy interfaces are in the same package.
             */
            for (Class<?> intf : interfaces) {
                int flags = intf.getModifiers();
                if (!Modifier.isPublic(flags)) {
                    accessFlags = Modifier.FINAL;
                    String name = intf.getName();
                    int n = name.lastIndexOf('.');
                    String pkg = ((n == -1) ? "" : name.substring(0, n + 1));
                    if (proxyPkg == null) {
                        proxyPkg = pkg;
                    } else if (!pkg.equals(proxyPkg)) {
                        throw new IllegalArgumentException(
                            "non-public interfaces from different packages");
                    }
                }
            }

            if (proxyPkg == null) {
                // if no non-public proxy interfaces, use com.sun.proxy package
                proxyPkg = ReflectUtil.PROXY_PACKAGE + ".";
            }

            /*
             * Choose a name for the proxy class to generate.
             */
            long num = nextUniqueNumber.getAndIncrement();
            String proxyName = proxyPkg + proxyClassNamePrefix + num;

            /*
             * Generate the specified proxy class.
             */
            byte[] proxyClassFile = ProxyGenerator.generateProxyClass(
                proxyName, interfaces, accessFlags);
            try {
                return defineClass0(loader, proxyName,
                                    proxyClassFile, 0, proxyClassFile.length);
            } catch (ClassFormatError e) {
                /*
                 * A ClassFormatError here means that (barring bugs in the
                 * proxy class generation code) there was some other
                 * invalid aspect of the arguments supplied to the proxy
                 * class creation (such as virtual machine limitations
                 * exceeded).
                 */
                throw new IllegalArgumentException(e.toString());
            }
        }
    }
```

这一段的代码大概的意思是，他为我们建一个新的类，通过字节码，全类名大概是`com.sun.proxy.$Proxy0`，这个是可以一次增加的，但是是有个数限制的，并且他集成了Proxy，实现了我们写的接口（如IMoveable）。

代理类写好之后，调用`Constructor.newInstance(Object ... initargs)`方法返回代理类实例。就这样代理过程就算结束。

## 后记

先就这样吧，改天再来

## 资料

1、**[模式的秘密---代理模式](https://www.imooc.com/learn/214)**

2、**[proxy-demo](https://github.com/fengwenyi/proxy-demo)**

3、**[细说JDK动态代理的实现原理](https://blog.csdn.net/mhmyqn/article/details/48474815)**
