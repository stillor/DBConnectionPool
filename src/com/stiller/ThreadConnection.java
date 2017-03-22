package com.stiller;

import java.sql.Connection;

/**
 * Created by stiller on 2017/3/22.
 */
public class ThreadConnection implements Runnable{
    private IConnectionPool pool;
    private final static ConnectionPoolManager manager = ConnectionPoolManager.getInstance();

    @Override
    public void run() {
        while(manager.isInit()) {
            pool = manager.getPool("testPool");
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
