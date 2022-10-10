package com.project1.service;

import com.project1.vo.admin;

import java.sql.SQLException;

public interface AdminService {

    admin queryAdminBy(String address)throws SQLException;
}
