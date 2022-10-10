package com.project1.service;

import com.project1.dao.MenuDao;
import com.project1.dao.MenuDaoImpl;
import com.project1.vo.Menu;
import com.project1.vo.RoleMenu;

import java.sql.SQLException;
import java.util.List;

public class MenuServiceImpl implements MenuService{

    MenuDao menuDao = new MenuDaoImpl();
    @Override
    public List<Menu> queryMenu(Menu menu, int pageNum, int lineNum) throws SQLException {
        return menuDao.queryMenu(menu,pageNum,lineNum);
    }

    @Override
    public int queryMenuCount(Menu menu) throws SQLException {
        return menuDao.queryMenuCount(menu);
    }

    @Override
    public Menu queryMenuBy(String menuId) throws SQLException {
        return menuDao.queryMenuBy(menuId);
    }

    @Override
    public void add(Menu menu) throws SQLException {
        menuDao.add(menu);
    }

    @Override
    public void edit(Menu menu) throws SQLException {
        menuDao.edit(menu);
    }

    @Override
    public void deleteMenu(String menuId) throws SQLException {
        menuDao.deleteMenu(menuId);
    }

    @Override
    public List<Menu> queryMenusBy(String roleId) throws SQLException {
        return menuDao.queryMenusBy(roleId);
    }

    @Override
    public void save(RoleMenu roleMenu) throws SQLException {
        menuDao.save(roleMenu);
    }

    @Override
    public void deleteRoleMenuBy(String roleId) throws SQLException {
        menuDao.deleteMenu(roleId);
    }
}
