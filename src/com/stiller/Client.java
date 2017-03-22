package com.stiller;

/**
 * Created by stiller on 2017/3/22.
 */
public class Client {
    public static void main(String[] args) throws InterruptedException {

        ThreadConnection a = new ThreadConnection();
        ThreadConnection b = new ThreadConnection();
        ThreadConnection c = new ThreadConnection();
        Thread t1 = new Thread(a);
        Thread t2 = new Thread(b);
        Thread t3 = new Thread(c);

        t1.start();
        t2.start();
        t3.start();

        System.out.println("线程A-> "+a.getConnection());
        System.out.println("线程B-> "+b.getConnection());
        System.out.println("线程C-> "+c.getConnection());

        System.out.println("=============================");

        System.out.println("线程A-> "+a.getCurrentConnection());
        System.out.println("线程B-> "+b.getCurrentConnection());
        System.out.println("线程C-> "+c.getCurrentConnection());
    }

}
