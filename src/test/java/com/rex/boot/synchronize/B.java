package com.rex.boot.synchronize;

public class B {
    synchronized public static void mB(String value) {
        for (int i = 0; i < 10; i++) {
            System.out.println(value);
            try {
                Thread.sleep(150);
            } catch (Exception e) {

            }
        }
    }

    synchronized public static void mC(String value) {
        for (int i = 0; i < 10; i++) {
            System.out.println(value);
            try {
                Thread.sleep(150);
            } catch (Exception e) {

            }
        }
    }
}
