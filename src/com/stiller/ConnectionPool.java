package com.stiller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by stiller on 2017/3/21.
 */
public class ConnectionPool implements IConnectionPool {
    private DBbean dbBean;
    private boolean isActive = false;
    private static final AtomicInteger contActive = new AtomicInteger(0);

    private List<Connection> freeConnection = new Vector<Connection>();
    private List<Connection> activeConnection = new Vector<Connection>();

    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    public ConnectionPool(DBbean dbBean){
        super();
        this.dbBean = dbBean;
        init();
        checkPool();
    }

    public void init(){
        try {
            Class.forName(dbBean.getDriverName());
            for (int i = 0; i < dbBean.getInitConnections(); i++) {
                Connection conn;
                    conn = newConnection();
                // 初始化最小连接数
                if (conn != null) {
                    freeConnection.add(conn);
                    contActive.getAndIncrement();
                }
            }
            isActive = true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }  catch (SQLException e) {
        e.printStackTrace();
    }
    }

    private synchronized Connection newConnection()
            throws ClassNotFoundException, SQLException {
        Connection conn = null;
        if (dbBean != null) {
            Class.forName(dbBean.getDriverName());
            conn = DriverManager.getConnection(dbBean.getUrl(),
                    dbBean.getUserName(), dbBean.getPassword());
        }
        return conn;
    }

    private boolean isValid(Connection conn) {
        try {
            if (conn == null || conn.isClosed()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public synchronized Connection getConnection() {
        Connection conn = null;
        try {
            // 判断是否超过最大连接数限制
            if(contActive.get() < this.dbBean.getMaxActiveConnections()){
                if (freeConnection.size() > 0) {
                    conn = freeConnection.get(0);
                    if (conn != null) {
                        threadLocal.set(conn);
                    }
                    freeConnection.remove(0);
                } else {
                    conn = newConnection();
                }

            }else{
                // 继续获得连接,直到从新获得连接
                wait(this.dbBean.getConnTimeOut());
                conn = getConnection();
            }
            if (isValid(conn)) {
                activeConnection.add(conn);
                contActive.getAndIncrement();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return conn;
    }

    @Override
    public Connection getCurrentConnection() {
        Connection conn = threadLocal.get();
        if(!isValid(conn)){
            conn = getConnection();
        }
        return conn;
    }

    @Override
    public void releaseConnection(Connection conn) throws SQLException {
        if (isValid(conn)&& !(freeConnection.size() > dbBean.getMaxConnections())) {
            freeConnection.add(conn);
            activeConnection.remove(conn);
            contActive.getAndDecrement();
            threadLocal.remove();
            // 唤醒所有正待等待的线程，去抢连接
            notifyAll();
        }

    }

    @Override
    public void destroy() {
        for (Connection conn : freeConnection) {
            try {
                if (isValid(conn)) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for (Connection conn : activeConnection) {
            try {
                if (isValid(conn)) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        isActive = false;
        contActive.set(0);

    }

    @Override
    public boolean isActive() {
        return this.isActive;
    }

    class CurrentConnection implements Runnable{
        @Override
        public void run() {
            // 1.对线程里面的连接状态
            // 2.连接池最小 最大连接数
            // 3.其他状态进行检查，因为这里还需要写几个线程管理的类，暂时就不添加了
            System.out.println("空线池连接数：" + freeConnection.size());
            System.out.println("活动连接数：：" + activeConnection.size());
            System.out.println("总的连接数：" + contActive);
        }
    }

    @Override
    public void checkPool() {
        if (dbBean.isCheakPool()) {
            //设置线程池的数量为10
            ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
            service.schedule(new CurrentConnection(),dbBean.getPeriodCheck(),TimeUnit.SECONDS);
        }
//        if(dbBean.isCheakPool()){
//            new Timer().schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    // 1.对线程里面的连接状态
//                    // 2.连接池最小 最大连接数
//                    // 3.其他状态进行检查，因为这里还需要写几个线程管理的类，暂时就不添加了
//                    System.out.println("空线池连接数："+freeConnection.size());
//                    System.out.println("活动连接数：："+activeConnection.size());
//                    System.out.println("总的连接数："+contActive);
//                }
//            },dbBean.getLazyCheck(),dbBean.getPeriodCheck());
//        }
    }
}
