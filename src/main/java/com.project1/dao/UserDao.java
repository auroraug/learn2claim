package com.project1.dao;

import com.project1.vo.User;

import java.sql.SQLException;
import java.util.List;
//数据库层
public interface UserDao {

    List<User> queryUsers(User user,int pageNum, int lineNum) throws SQLException;

    User queryUserById(String userId) throws SQLException;

    void addUser(User user) throws SQLException;

    void deleteUser(String userId) throws SQLException;

    int queryUserCount(User u) throws SQLException;

    void editUser(User user)throws SQLException;
}
