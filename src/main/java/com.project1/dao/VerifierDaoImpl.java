package com.project1.dao;

import com.project1.db.MysqlDB;
import com.project1.vo.User;
import com.project1.vo.Verifier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VerifierDaoImpl implements VerifierDao {

    @Override
    public List<Verifier> queryVerifier(Verifier verifier, int pageNum, int lineNum) throws SQLException {
        int limit_x = (pageNum - 1) * lineNum;
        int limit_y = lineNum;
        StringBuffer sql = new StringBuffer("select v.*, r.roleName from verifierInfo v left join roleInfo r on r.roleId = v.roleId ");
        sql.append(" where 1 = 1 ");
        if (verifier.getAddress() != null && !"".equals(verifier.getAddress())) {
            sql.append(" and v.address like '%" + verifier.getAddress() + "%' ");
        }
        /*if (verifier.getUserName() != null && !"".equals(verifier.getUserName())) {
            sql.append(" and u.userName like '%" + verifier.getUserName() + "%' ");
        }*/
        /*if (verifier.getGender() != 0) {
            sql.append(" and u.gender =" + verifier.getGender());
        }*/

//        sql.append(" order by u.roleId desc ");
        sql.append(" limit " + limit_x + "," + limit_y);
        System.out.println(sql);
        List<Verifier> list = new ArrayList<>();
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
                Verifier verifier1 = new Verifier();
                verifier1.setAddress(resultSet.getString("address"));
                verifier1.setRoleId(resultSet.getInt("roleId"));
                verifier1.setRoleName(resultSet.getString("roleName"));
                list.add(verifier1);
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
    public int queryVerifierCount(Verifier v) throws SQLException {
        StringBuffer sql = new StringBuffer("select count(*) as verifierCount from verifierInfo v ");
        sql.append(" where 1 = 1");
        if (v.getAddress() != null && !"".equals(v.getAddress())) {
            sql.append(" and v.address like '%" + v.getAddress() + "%' ");
        }

        System.out.println(sql.toString());
        MysqlDB mysqlDB = new MysqlDB();
        Connection conn = mysqlDB.getConnect();
        Statement stmt = null;
        ResultSet rs = null;
        int verifierCount = 0;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql.toString());
            while (rs.next()) {
                verifierCount = rs.getInt("verifierCount");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            rs.close();
            stmt.close();
            conn.close();
        }
        return verifierCount;
    }

    @Override
    public Verifier queryVerifierBy(String address) throws SQLException {
        String sql = "select v.* from verifierInfo v where v.address = '" + address + "'";
        Verifier verifier = null;
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
                verifier = new Verifier();
                verifier.setAddress(resultSet.getString("address"));
                verifier.setRoleId(resultSet.getInt("roleId"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            resultSet.close();
            statement.close();
            connection.close();
        }
        return verifier;
    }

    @Override
    public void add(Verifier verifier) throws SQLException {
        StringBuffer sql = new StringBuffer("insert into verifierInfo(address,roleId) values(");
        sql.append("'" + verifier.getAddress() + "',");

        sql.append(verifier.getRoleId());
        sql.append(")");
        System.out.println(sql.toString());
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
    public void edit(Verifier verifier) throws SQLException {
        StringBuffer sql = new StringBuffer("update verifierInfo set ");
        sql.append(" address='" + verifier.getAddress() + "', ");
        sql.append(" roleId=" + verifier.getRoleId());
        sql.append(" where address='" + verifier.getAddress() + "'");
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
    public void deleteVerifier(String address) throws SQLException {
        String sql = "delete from verifierInfo where address='" + address + "'";
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

