package com.cgtrc.bym.a10001store.ui;

/**
 * Created by BYM on 2016/2/2.
 */
public class Singleton {

    private static Singleton singleton = new Singleton();
    private Singleton(){

    }
    public static Singleton getInstance(){
        return singleton;
    }
}


