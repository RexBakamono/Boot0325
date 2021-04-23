package com.rex.boot.synchronize;

public class Main {
    public static void main(String[] args) {
        System.out.println("Main 线程 开始运行!");
        final B b1 = new B();
        Thread t1 = new Thread() {
            @Override
            public void run() {
                System.out.println("t1 开始运行!");
                b1.mB("HAHA");
                System.out.println("t1 结束运行!");
            }
        };
        t1.start();
        final B b2 = new B();
        Thread t2 = new Thread() {
            @Override
            public void run() {
                System.out.println("t2 开始运行!");
                b2.mC("XIXI");
                System.out.println("t2 结束运行!");
            }
        };
        t2.start();

        System.out.println("Main 线程 结束运行!");
    }
}
