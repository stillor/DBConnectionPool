package com.stiller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stiller on 2017/3/21.
 */
public class DBInitInfo {
    public  static List<DBbean>  beans = null;
    static{
        beans = new ArrayList<DBbean>();
        // 这里数据 可以从xml 等配置文件进行获取
        // 为了测试，这里我直接写死
        String driverName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/green";
        String userName = "root";
        String password = "123456";
        String poolName = "testPool";

        DBbean dBbean = new DBbean.Builder().driverName(driverName).url(url).password(password).userName(userName)
                .poolName(poolName).build();

        dBbean.setMinConnections(5);
        dBbean.setMaxConnections(100);
        beans.add(dBbean);
    }
}
