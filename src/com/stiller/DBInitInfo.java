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
        DBbean beanMySQL = new DBbean();
        beanMySQL.setDriverName("com.mysql.jdbc.Driver");
        beanMySQL.setUrl("jdbc:mysql://localhost:3306/student");
        beanMySQL.setUserName("root");
        beanMySQL.setPassword("123456");

        beanMySQL.setMinConnections(5);
        beanMySQL.setMaxConnections(100);

        beanMySQL.setPoolName("testPool");
        beans.add(beanMySQL);
    }
}
