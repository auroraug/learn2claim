package com.project1.service;


import com.project1.vo.Role;

import java.sql.SQLException;
import java.util.List;

public interface RoleService {

    List<Role> queryRole(Role role, int pageNum, int lineNum) throws SQLException;

    int queryRoleCount(Role role) throws SQLException;

    Role queryRoleBy(String roleId)throws SQLException;

    void add(Role role) throws SQLException;

    void edit(Role role) throws SQLException;

    void deleteRole(String roleId) throws SQLException;
}
