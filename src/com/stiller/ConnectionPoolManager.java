package com.stiller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;

/**
 * Created by stiller on 2017/3/21.
 */
public class ConnectionPoolManager {
    public Hashtable<String,IConnectionPool> pools = new Hashtable<String, IConnectionPool>();

    private volatile static boolean isInit = false;

    // 初始化
    private ConnectionPoolManager(){
        init();
    }
    // 单例实现
    public static ConnectionPoolManager getInstance(){
        return Singtonle.instance;
    }

    private static class Singtonle {
        private static ConnectionPoolManager instance =  new ConnectionPoolManager();
    }

    public boolean isInit(){
        return this.isInit;
    }

    public void setInit(boolean b){
        this.isInit = b;
    }


    // 初始化所有的连接池
    public void init(){
        for(int i =0;i<DBInitInfo.beans.size();i++){
            DBbean bean = DBInitInfo.beans.get(i);
            ConnectionPool pool = new ConnectionPool(bean);
            if(pool != null){
                pools.put(bean.getPoolName(), pool);
                System.out.println("Info:Init connection successed ->" +bean.getPoolName());
            }
        }
        System.out.println("inited!");
        setInit(true);

    }

    // 获得连接,根据连接池名字 获得连接
    public Connection  getConnection(String poolName){
        Connection conn = null;
        if(pools.size()>0 && pools.containsKey(poolName)){
            conn = getPool(poolName).getConnection();
        }else{
            System.out.println("Error:Can't find this connecion pool ->"+poolName);
        }
        return conn;
    }

    // 关闭，回收连接
    public void close(String poolName,Connection conn){
        IConnectionPool pool = getPool(poolName);
        try {
            if(pool != null){
                pool.releaseConnection(conn);
            }
        } catch (SQLException e) {
            System.out.println("连接池已经销毁");
            e.printStackTrace();
        }
    }

    // 清空连接池
    public void destroy(String poolName){
        IConnectionPool pool = getPool(poolName);
        if(pool != null){
            pool.destroy();
        }
    }

    // 获得连接池
    public IConnectionPool getPool(String poolName){
        IConnectionPool pool = null;
        if(pools.size() > 0){
            pool = pools.get(poolName);
        }
        return pool;
    }
}
