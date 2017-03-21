package com.stiller;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by stiller on 2017/3/21.
 */
public interface IConnectionPool {
    public Connection getConnection();
    public Connection getCurrentConnection();
    public void releaseConnection(Connection conn) throws SQLException;
    public void destroy();
    public boolean isActive();
    public void checkPool();
}
