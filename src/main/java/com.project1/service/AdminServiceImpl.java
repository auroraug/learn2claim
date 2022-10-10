package com.project1.service;

import com.project1.dao.AdminDao;
import com.project1.dao.AdminDaoImpl;
import com.project1.vo.admin;

import java.sql.SQLException;

public class AdminServiceImpl implements AdminService {

    AdminDao adminDao = new AdminDaoImpl();
    @Override
    public admin queryAdminBy(String address) throws SQLException {
        return adminDao.queryAdminBy(address);
    }
}
