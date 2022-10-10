package com.project1.service;

import com.project1.vo.Menu;
import com.project1.vo.RoleMenu;
import java.sql.SQLException;
import java.util.List;

public interface MenuService {

    List<Menu> queryMenu(Menu menu,int pageNum,int lineNum)throws SQLException;

    int queryMenuCount(Menu menu) throws SQLException;

    Menu queryMenuBy(String menuId) throws SQLException;

    void add(Menu menu) throws SQLException;

    void edit(Menu menu) throws SQLException;

    void deleteMenu(String menuId) throws SQLException;

    List<Menu> queryMenusBy(String roleId) throws SQLException;

    void save(RoleMenu roleMenu) throws SQLException;

    void deleteRoleMenuBy(String roleId) throws SQLException;
}
