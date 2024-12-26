package com.elandt.lil.spring_demo.singleton;

/**
 * This is the more traditional Java means of defining a Singleton
 */
public class SingletonA {

    // This is the instance handle
    private static SingletonA instance;

    private SingletonA() {
        super();
    }

    /**
     * The method to return a new instance if one doesn't already exist, otherwise a synchronized one.
     *
     * This implementation includes "double locking" and triggers the following from Sonar:
     *
     * <p>Double-checked locking should not be used (java:S2168)</p>
     *
     * <p>
     * Double-checked locking is the practice of checking a lazy-initialized object’s state both before and after
     * a synchronized block is entered to determine whether to initialize the object. In early JVM versions,
     * synchronizing entire methods was not performant, which sometimes caused this practice to be used in its place.
     * </p>
     *
     * <p>
     * Apart from float and int types, this practice does not work reliably in a platform-independent manner
     * without additional synchronization of mutable instances. Using double-checked locking for the lazy
     * initialization of any other type of primitive or mutable object risks a second thread using an
     * uninitialized or partially initialized member while the first thread is still creating it. The results can
     * be unexpected, potentially even causing the application to crash.
     * </p>
     *
     * <p>How can I fix it?</p>
     *
     * <p>
     * Given significant performance improvements of synchronized methods in recent JVM versions, synchronized
     * methods are now preferred over the less robust double-checked locking.
     * </p>
     *
     * <p>
     * If marking the entire method as synchronized is not an option, consider using an inner static class
     * to hold the reference instead. Inner static classes are guaranteed to be initialized lazily.
     *
     * @return the instance of {@link SingletonA}
     */
    public static SingletonA getInstance() {
        if (null == instance) {
            synchronized (SingletonA.class) {
                if (null == instance) {
                    instance = new SingletonA();
                }
            }
        }

        return instance;
    }

    // Compliant version 1 - entire method is synchronized
    public static synchronized SingletonA getInstanceCompliantV1() {
        if (null == instance) {
            instance = new SingletonA();
        }

        return instance;
    }

    // Compliant version 2 - static inner class
    private static class SingletonAHolder {
        public static final SingletonA instance = new SingletonA();
    }

    public static SingletonA getInstanceCompliantV2() {
        return SingletonA.SingletonAHolder.instance;
    }
}
