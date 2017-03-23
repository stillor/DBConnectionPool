package com.stiller;

import java.sql.Connection;

/**
 * Created by stiller on 2017/3/22.
 */
public class ThreadConnection implements Runnable{
    private String threadName;
    private IConnectionPool pool;
    private final static ConnectionPoolManager manager = ConnectionPoolManager.getInstance();

    public ThreadConnection(String name){
        this.threadName = name;
    }

    @Override
    public void run() {
        while(manager.isInit()) {
            pool = manager.getPool("testPool");
            System.out.println("线程"+this.threadName+"-> "+getConnection());

            System.out.println("当前线程"+this.threadName+"-> "+getCurrentConnection());

            pool.checkPool();

            break;
        }
    }

    public Connection getConnection(){
        Connection conn = null;
        if(pool != null && pool.isActive()){
            conn = pool.getConnection();
        }
        return conn;
    }

    public Connection getCurrentConnection(){
        Connection conn = null;
        if(pool != null && pool.isActive()){
            conn = pool.getCurrentConnection();
        }
        return conn;
    }
}
