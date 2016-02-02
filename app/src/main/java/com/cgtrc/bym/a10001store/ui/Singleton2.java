package com.cgtrc.bym.a10001store.ui;

/**
 * Created by BYM on 2016/2/2.
 */
public class Singleton2 {
    /**
     * 忽略反射的情况
     */

    private static Singleton2 singleton2 = null;
    private Singleton2() {

    }
    public static Singleton2 getInstance() {
        if(singleton2 == null){ //线程同步 确保在任何情况下都是单例，但是效率相对低 影响实例化速度
            synchronized (Singleton2.class){
                if(singleton2==null){
                    singleton2 = new Singleton2();
                }
            }
        }
        return singleton2;
    }
}
