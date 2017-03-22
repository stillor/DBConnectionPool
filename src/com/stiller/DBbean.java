package com.stiller;

/**
 * Created by stiller on 2017/3/21.
 */
public class DBbean {
    private String driverName;
    private String url;
    private String password;
    private String userName;

    private String poolName;
    private int minConnections = 1; // 空闲池，最小连接数
    private int maxConnections = 10; // 空闲池，最大连接数

    private int initConnections = 5;// 初始化连接数

    private long connTimeOut = 1000;// 重复获得连接的频率

    private int maxActiveConnections = 100;// 最大允许的连接数，和数据库对应

    private long connectionTimeOut = 1000*60*20;// 连接超时时间，默认20分钟

    private boolean isCurrentConnection = true; // 是否获得当前连接，默认true

    private boolean isCheakPool = true; // 是否定时检查连接池
    private long lazyCheck = 1000*60*60;// 延迟多少时间后开始 检查
    private long periodCheck = 1000*60*60;// 检查频率

    public static class Builder{
        private String driverName = "";
        private String url = "";
        private String password = "";
        private String userName = "";
        private String poolName = "";

        public Builder driverName(String str){
            driverName = str;
            return this;
        }
        public Builder url(String str){
            url = str;
            return this;
        }
        public Builder password(String str){
            password = str;
            return this;
        }
        public Builder userName(String str){
            userName = str;
            return this;
        }
        public Builder poolName(String str){
            poolName = str;
            return this;
        }

        public DBbean build(){
            return new DBbean(this);
        }


    }

    private DBbean(Builder builder){
        driverName = builder.driverName;
        url = builder.url;
        password = builder.password;
        userName = builder.userName;
        poolName = builder.poolName;
    }

    public DBbean(String driverName,String url,String password,String userName,String poolName){
        this.driverName = driverName;
        this.url = url;
        this.password = password;
        this.userName = userName;
        this.poolName = poolName;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public int getMinConnections() {
        return minConnections;
    }

    public void setMinConnections(int minConnections) {
        this.minConnections = minConnections;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public int getInitConnections() {
        return initConnections;
    }

    public void setInitConnections(int initConnections) {
        this.initConnections = initConnections;
    }

    public long getConnTimeOut() {
        return connTimeOut;
    }

    public void setConnTimeOut(long connTimeOut) {
        this.connTimeOut = connTimeOut;
    }

    public int getMaxActiveConnections() {
        return maxActiveConnections;
    }

    public void setMaxActiveConnections(int maxActiveConnections) {
        this.maxActiveConnections = maxActiveConnections;
    }

    public long getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(long connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    public boolean isCurrentConnection() {
        return isCurrentConnection;
    }

    public void setCurrentConnection(boolean currentConnection) {
        isCurrentConnection = currentConnection;
    }

    public boolean isCheakPool() {
        return isCheakPool;
    }

    public void setCheakPool(boolean cheakPool) {
        isCheakPool = cheakPool;
    }

    public long getLazyCheck() {
        return lazyCheck;
    }

    public void setLazyCheck(long lazyCheck) {
        this.lazyCheck = lazyCheck;
    }

    public long getPeriodCheck() {
        return periodCheck;
    }

    public void setPeriodCheck(long periodCheck) {
        this.periodCheck = periodCheck;
    }

    public DBbean(){}

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
