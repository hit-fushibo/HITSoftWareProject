package com.example.springboot;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {

    @Test
    public void testThreadLocalSetAndGet(){
        //提供ThreadLocal对象
        ThreadLocal t1=new ThreadLocal<>();

        //开启两个线程
        new Thread(()->{
            t1.set("萧炎");
            System.out.println(Thread.currentThread().getName()+":"+t1.get());
            System.out.println(Thread.currentThread().getName()+":"+t1.get());
            System.out.println(Thread.currentThread().getName()+":"+t1.get());
        },"蓝色").start();
        new Thread(()->{
            t1.set("药尘");
            System.out.println(Thread.currentThread().getName()+":"+t1.get());
            System.out.println(Thread.currentThread().getName()+":"+t1.get());
            System.out.println(Thread.currentThread().getName()+":"+t1.get());
        },"绿色").start();
    }
}
