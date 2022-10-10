package com.project1.dao;

import com.project1.db.MysqlDB;
import com.project1.vo.admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminDaoImpl implements AdminDao {
    @Override
    public admin queryAdminBy(String address) throws SQLException {
        String sql = "select a.* from adminInfo a where a.address = '" + address + "'";
        admin admin = null;
        MysqlDB mysql = new MysqlDB();
        Connection connection = mysql.getConnect();
        //查询数据库
        Statement statement = null;
        //获取结果
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);//sql查询语句
            while (resultSet.next()) {
                admin = new admin();
                admin.setAddress(resultSet.getString("address"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            resultSet.close();
            statement.close();
            connection.close();
        }
        return admin;
    }
}
