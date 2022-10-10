package com.project1.dao;

import com.project1.vo.admin;

import java.sql.SQLException;

public interface AdminDao {

    admin queryAdminBy(String address)throws SQLException;

}
