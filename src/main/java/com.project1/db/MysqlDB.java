package com.project1.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//实现java连接mysql的对象,相当于spring框架中的application.properties
public class MysqlDB {

    private final static String DB_Driver = "com.mysql.jdbc.Driver";
    private final static String DB_URL = "jdbc:mysql://os01:3306/userdb?useUnicode=true&characterEncoding=utf8";
    private final static String DB_USER = "root";
    private final static String DB_PASSWD = "123456";

    public Connection getConnect()  {
        Connection connection = null;
        try {
            Class.forName(DB_Driver);
            connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWD);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return connection;
    }
}
