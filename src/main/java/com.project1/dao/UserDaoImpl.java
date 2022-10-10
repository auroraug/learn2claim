package com.project1.dao;

import com.project1.db.MysqlDB;
import com.project1.vo.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao{

    @Override
    public int queryUserCount(User u) throws SQLException {
        StringBuffer sql = new StringBuffer("select count(*) as userCount from userInfo u ");
        sql.append(" where 1 = 1");
        if (u.getUserId() != null && !"".equals(u.getUserId())) {
            sql.append(" and u.userId like '%" + u.getUserId() + "%' ");
        }
        if (u.getUserName() != null && !"".equals(u.getUserName())) {
            sql.append(" and u.userName like '%" + u.getUserName() + "%' ");
        }
        if (u.getGender() != 0) {
            sql.append(" and u.gender =" + u.getGender());
        }
        System.out.println(sql.toString());
        MysqlDB mysqlDB = new MysqlDB();
        Connection conn = mysqlDB.getConnect();
        Statement stmt = null;
        ResultSet rs = null;
        int userCount = 0;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql.toString());
            while (rs.next()) {
                userCount = rs.getInt("userCount");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            rs.close();
            stmt.close();
            conn.close();
        }
        return userCount;
    }

    @Override
    public List<User> queryUsers(User u,int pageNum,int lineNum) throws SQLException {
        int limit_x = (pageNum - 1) * lineNum;
        int limit_y = lineNum;
        StringBuffer sql = new StringBuffer("select u.*, r.roleName from userInfo u left join roleInfo r on r.roleId = u.roleId ");
        sql.append(" where 1 = 1 ");
        if (u.getUserId() != null && !"".equals(u.getUserId())) {
            sql.append(" and u.userId like '%" + u.getUserId() + "%' ");
        }
        if (u.getUserName() != null && !"".equals(u.getUserName())) {
            sql.append(" and u.userName like '%" + u.getUserName() + "%' ");
        }
        if (u.getGender() != 0) {
            sql.append(" and u.gender =" + u.getGender());
        }

//        sql.append(" order by u.roleId desc ");
        sql.append(" limit " + limit_x + "," + limit_y);
        System.out.println(sql.toString());
        List<User> list = new ArrayList<>();
        MysqlDB mysql = new MysqlDB();
        Connection connection = mysql.getConnect();
        //查询数据库
        Statement statement = null;
        //获取结果
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql.toString());//sql查询语句
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getString("userId"));
                user.setUserName(resultSet.getString("userName"));
                user.setGender(resultSet.getInt("gender"));
                user.setUserPassword(resultSet.getString("userPassword"));
                user.setRoleId(resultSet.getInt("roleId"));
                user.setRoleName(resultSet.getString("roleName"));
                list.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            resultSet.close();
            statement.close();
            connection.close();
        }
        return list;
    }

    @Override
    public User queryUserById(String userId) throws SQLException {
        String sql = "select u.* from userInfo u where u.userId = '"+userId+"'";
        User user = null;
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
                user = new User();
                user.setUserId(resultSet.getString("userId"));
                user.setUserName(resultSet.getString("userName"));
                user.setGender(resultSet.getInt("gender"));
                user.setUserPassword(resultSet.getString("userPassword"));
                user.setRoleId(resultSet.getInt("roleId"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            resultSet.close();
            statement.close();
            connection.close();
        }
        return user;
    }

    @Override
    public void addUser(User user) throws SQLException {
        StringBuffer sql = new StringBuffer("insert into userInfo(userId,userName,userPassword,gender,roleId) values(");
        sql.append("'"+user.getUserId() + "',");
        sql.append("'"+user.getUserName() + "',");
        sql.append("'"+user.getUserPassword() + "',");
        sql.append(user.getGender() + ",");
        sql.append(user.getRoleId() );
        sql.append(")");
        MysqlDB mysql = new MysqlDB();
        Connection connection = mysql.getConnect();
        //查询数据库
        Statement statement = null;
        //获取结果
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
//            resultSet = statement.executeQuery(sql);//sql查询语句
            statement.execute(sql.toString());//updata & insert 语句

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            statement.close();
            connection.close();
        }
    }


    @Override
    public void editUser(User user) throws SQLException {
        StringBuffer sql = new StringBuffer("update userInfo set ");
        sql.append(" userId='" + user.getUserId() + "', ");
        sql.append(" userName='" + user.getUserName() + "', ");
        sql.append(" userPassword='" + user.getUserPassword() + "', ");
        sql.append(" gender=" + user.getGender() + ",");
        sql.append(" roleId=" + user.getRoleId());
        sql.append(" where userId='" + user.getUserId() + "'");
        MysqlDB mysql = new MysqlDB();
        Connection connection = mysql.getConnect();
        //查询数据库
        Statement statement = null;
        //获取结果
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
//            resultSet = statement.executeQuery(sql);//sql查询语句
            statement.execute(sql.toString());//updata & insert 语句

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            statement.close();
            connection.close();
        }
    }
    public void deleteUser(String userId)throws SQLException{
        String sql = "delete from userInfo where userId='"+userId+"'";
        System.out.println(sql);
        MysqlDB mysql = new MysqlDB();
        Connection connection = mysql.getConnect();
        //查询数据库
        Statement statement = null;
        //获取结果
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
//            resultSet = statement.executeQuery(sql);//sql查询语句
            statement.execute(sql);//updata & insert 语句

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            statement.close();
            connection.close();
        }
    }
}
