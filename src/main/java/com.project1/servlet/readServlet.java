package com.project1.servlet;

import com.project1.db.MysqlDB;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

public class readServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        String address = (String) session.getAttribute("addr");   //取出用户唯一性标识
        String exam_description_idStr = req.getParameter("exam_description_id") ;//取出试题的唯一性标识
        int exam_description_id = Integer.parseInt(exam_description_idStr);

        MysqlDB mysql = new MysqlDB();
        Connection connection = mysql.getConnect();
        //查询数据库
        Statement statement = null;
        //获取结果
        ResultSet resultSet = null;
        //连接数据库
        /*String url = this.getServletContext().getInitParameter("url");
        String user = this.getServletContext().getInitParameter("user");
        String pwd = this.getServletContext().getInitParameter("password");*/

        try {
            PreparedStatement preparedStatement = null;
            //更新数据库服务器信息
            preparedStatement = connection.prepareStatement("insert into readrecord(address,id) values(?,?)");
            preparedStatement.setString(1,address);
            preparedStatement.setInt(2,exam_description_id);
            if (preparedStatement.executeUpdate()==1){  //插入成功，带成功提示参数重定向
                switch (exam_description_idStr){
                    case "1":
                        resp.sendRedirect("https://www.campaign.token.im/zh/ethereum");
                        break;
                    case "2":
                        resp.sendRedirect("https://www.campaign.token.im/zh/basic-1");
                        break;
                    case "3":
                        resp.sendRedirect("https://www.campaign.token.im/zh/intermediate-1");
                        break;
                    case "4":
                        resp.sendRedirect("https://www.campaign.token.im/zh/advanced-1");
                        break;
                }
            }
            else {  //插入失败，带失败提示参数重定向
                resp.sendRedirect("../scholarPage/learn.jsp?message=fail");
            }
            connection.close(); //关闭数据库连接
        }
        catch (SQLException e){
            e.printStackTrace();
        }
/*        resp.addHeader("refresh","5");
        //5秒后跳转
        resp.setHeader("refresh","3;https://www.baidu.com");
        //3秒后刷新并跳转到百度*/
//        resp.sendRedirect("https://www.baidu.com");
    }
}
