package com.project1.dao;

import com.project1.db.MysqlDB;
import com.project1.vo.Scholar;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ScholarDaoImpl implements ScholarDao{
    @Override
    public List<Scholar> queryScholar(Scholar s, int pageNum, int lineNum) throws SQLException {
        int limit_x = (pageNum - 1) * lineNum;
        int limit_y = lineNum;
        StringBuffer sql = new StringBuffer("select s.*, r.roleName from scholarInfo s left join roleInfo r on r.roleId = s.roleId ");
        sql.append(" where 1 = 1 ");
        if (s.getAddress() != null && !"".equals(s.getAddress())) {
            sql.append(" and s.address like '%" + s.getAddress() + "%' ");
        }
        if (s.getStatus() != null && !s.getStatus().equals("")) {
            sql.append(" and s.status =" + s.getStatus());
        }
        if (s.getMintnum() != null && !s.getMintnum().equals("")) {
            sql.append(" and s.mintnum =" + s.getMintnum());
        }
//        sql.append(" order by u.roleId desc ");
        sql.append(" limit " + limit_x + "," + limit_y);
        System.out.println(sql.toString());
        List<Scholar> list = new ArrayList<>();
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
                Scholar scholar = new Scholar();
                scholar.setAddress(resultSet.getString("address"));
                scholar.setStatus(resultSet.getInt("status"));
                scholar.setRoleId(resultSet.getInt("roleId"));
                scholar.setRoleName(resultSet.getString("roleName"));
                scholar.setMintnum(resultSet.getInt("mintnum"));
                list.add(scholar);
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
    public int queryScholarCount(Scholar scholar) throws SQLException {
        StringBuffer sql = new StringBuffer("select count(*) as scholarCount from scholarInfo s ");
        sql.append(" where 1 = 1");
        if (scholar.getAddress() != null && !"".equals(scholar.getAddress())) {
            sql.append(" and s.address like '%" + scholar.getAddress() + "%' ");
        }
        if (scholar.getStatus() != null && !scholar.getStatus().equals("")) {
            sql.append(" and s.status =" + scholar.getStatus());
        }
        System.out.println(sql.toString());
        MysqlDB mysqlDB = new MysqlDB();
        Connection conn = mysqlDB.getConnect();
        Statement stmt = null;
        ResultSet rs = null;
        int scholarCount = 0;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql.toString());
            while (rs.next()) {
                scholarCount = rs.getInt("scholarCount");//查询出的结果取scholarCount的值
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            rs.close();
            stmt.close();
            conn.close();
        }
        return scholarCount;
    }

    @Override
    public Scholar queryScholarBy(String address) throws SQLException {
        String sql = "select s.* from scholarInfo s where s.address = '"+address+"'";
        Scholar scholar = null;
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
                scholar = new Scholar();
                scholar.setAddress(resultSet.getString("address"));
                scholar.setStatus(resultSet.getInt("status"));
                scholar.setRoleId(resultSet.getInt("roleId"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            resultSet.close();
            statement.close();
            connection.close();
        }
        return scholar;
    }

    @Override
    public void add(Scholar scholar) throws SQLException {
        StringBuffer sql = new StringBuffer("insert into scholarInfo(address,roleId,status,mintnum) values(");
        sql.append("'"+scholar.getAddress() + "',");
/*        sql.append("'"+scholar.getUserName() + "',");
        sql.append("'"+scholar.getUserPassword() + "',");*/
        sql.append(scholar.getRoleId() + ",");
        sql.append(scholar.getStatus() + ",0");
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
    public void edit(Scholar scholar) throws SQLException {
        StringBuffer sql = new StringBuffer("update scholarInfo set ");
        sql.append(" address='" + scholar.getAddress() + "'");
        if (scholar.getMintnum() != null && !scholar.getMintnum().equals("")) {
            sql.append(", mintnum=" + scholar.getMintnum());
        }

        if (scholar.getStatus() != null && !scholar.getStatus().equals("")) {
            sql.append(", status =" + scholar.getStatus());
        }
        sql.append(" where address='" + scholar.getAddress() + "'");
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
            statement.execute(sql.toString());//updata & insert 语句

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            statement.close();
            connection.close();
        }
    }

    @Override
    public void deleteScholar(String address) throws SQLException {
        String sql = "delete from scholarInfo where address='"+address+"'";
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
