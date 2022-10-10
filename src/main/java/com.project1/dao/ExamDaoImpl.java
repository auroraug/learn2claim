package com.project1.dao;

import com.project1.db.MysqlDB;
import com.project1.vo.Exam;
import com.project1.vo.Verifier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExamDaoImpl implements ExamDao{
    @Override
    public List<Exam> queryExam(Exam exam, int pageNum, int lineNum) throws SQLException {
        int limit_x = (pageNum - 1) * lineNum;
        int limit_y = lineNum;
        StringBuffer sql = new StringBuffer("select e.* from exam_description e ");
        sql.append(" where 1 = 1 ");
        if (exam.getExamId() != null && !"".equals(exam.getExamId())) {
            sql.append(" and e.id like '%" + exam.getExamId() + "%' ");
        }
        if (exam.getDescription() != null && !"".equals(exam.getDescription())) {
            sql.append(" and e.exam_description like '%" + exam.getDescription() + "%' ");
        }
        /*if (verifier.getGender() != 0) {
            sql.append(" and u.gender =" + verifier.getGender());
        }*/

//        sql.append(" order by u.roleId desc ");
        sql.append(" limit " + limit_x + "," + limit_y);
        System.out.println(sql);
        List<Exam> list = new ArrayList<>();
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
                Exam exam1 = new Exam();
                exam1.setExamId(resultSet.getString("id"));
                exam1.setDescription(resultSet.getString("exam_description"));
                exam1.setTime(resultSet.getInt("exam_time"));
                list.add(exam1);
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
    public int queryExamCount(Exam e) throws SQLException {
        StringBuffer sql = new StringBuffer("select count(*) as examCount from exam_description e ");
        sql.append(" where 1 = 1");
        if (e.getExamId() != null && !"".equals(e.getExamId())) {
            sql.append(" and e.id like '%" + e.getExamId() + "%' ");
        }

        System.out.println(sql.toString());
        MysqlDB mysqlDB = new MysqlDB();
        Connection conn = mysqlDB.getConnect();
        Statement stmt = null;
        ResultSet rs = null;
        int examCount = 0;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql.toString());
            while (rs.next()) {
                examCount = rs.getInt("examCount");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            rs.close();
            stmt.close();
            conn.close();
        }
        return examCount;
    }

    @Override
    public Exam queryExamBy(String examId) throws SQLException {
        String sql = "select e.* from exam_description e where e.id = '" + examId + "'";
        Exam exam = null;
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
                exam = new Exam();
                exam.setExamId(resultSet.getString("id"));
                exam.setDescription(resultSet.getString("exam_description"));
                exam.setTime(resultSet.getInt("exam_time"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            resultSet.close();
            statement.close();
            connection.close();
        }
        return exam;
    }

    @Override
    public void add(Exam exam) throws SQLException {
        StringBuffer sql = new StringBuffer("insert into exam_description(id,exam_description,exam_time) values(");
        sql.append("'" + exam.getExamId() + "',");
        sql.append("'"+exam.getDescription()+"',");
        sql.append(exam.getTime());
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
    public void edit(Exam exam) throws SQLException {
        StringBuffer sql = new StringBuffer("update exam_description set ");
        sql.append(" id='" + exam.getExamId() + "', ");
        sql.append(" exam_description='" + exam.getDescription()+ "', ");
        sql.append(" exam_time=" + exam.getTime());
        sql.append(" where id='" + exam.getExamId() + "'");
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
    public void deleteExam(String examId) throws SQLException {
        String sql = "delete from exam_description where id='" + examId + "'";
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
