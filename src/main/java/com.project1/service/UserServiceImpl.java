package com.project1.service;

import com.project1.dao.UserDao;
import com.project1.dao.UserDaoImpl;
import com.project1.vo.User;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();


    @Override
    public List<User> queryUsers(User user,int pageNum,int lineNum) throws SQLException {
        return userDao.queryUsers(user,pageNum,lineNum);
    }


    @Override
    public User queryUserById(String userId) throws SQLException {
        return userDao.queryUserById(userId);
    }

    @Override
    public void addUser(User user) throws SQLException {
        userDao.addUser(user);
    }

    @Override
    public void deleteUser(String userId) throws SQLException {
        userDao.deleteUser(userId);
    }

    @Override
    public int queryUserCount(User u) throws SQLException {
        return userDao.queryUserCount(u);
    }

    @Override
    public void editUser(User user) throws SQLException {
        userDao.editUser(user);
    }

}
