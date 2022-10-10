package com.project1.servlet;

import com.project1.service.UserService;
import com.project1.service.UserServiceImpl;
import com.project1.vo.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class Servlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //字符串索引以name为主
        String username = this.getInitParameter("username");
        String _username = req.getParameter("username");
        String _password = req.getParameter("password");

//        System.out.println("username ="+_username);
//        System.out.println("password ="+_password);

        //根据用户查询数据库，如果没有结果，说明用户不存在
        //如果查找到了，再对比密码，密码不对，返回错误提示
        try {
            UserService userService = new UserServiceImpl();
            User user = userService.queryUserById(_username);

            //测试： 这里先设置用户名为admin 密码为666666
            if(user != null && user.getUserPassword().equals(_password)){
                //密码正确，跳转到系统主页
                req.setAttribute("username",_username);

                req.getRequestDispatcher("/_userservlet?type=20").forward(req,resp);//页面跳转，internal跳转传递
    //            resp.sendRedirect("https://www.baidu.com");
            }
            else {
                //用户名密码错误，提示
                req.setAttribute("error","用户名或密码错误，请重新输入！");
                //并进行回传，即为username仍保持不变
                //回传数据需要在前端页面text框中赋值，即在后面添加value="${username}"
                req.setAttribute("username",_username);
                req.getRequestDispatcher("/login.jsp").forward(req,resp);
    //            resp.sendRedirect("https//www.baidu.com");//external 跳转传递
    //            resp.sendRedirect("/index.jsp/?username=admin");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


/*
if("admin".equals(_username)&&"666666".equals(_password)){
        //密码正确，跳转到系统主页
        req.setAttribute("username",_username);
        req.getRequestDispatcher("/_userservlet").forward(req,resp);//页面跳转，internal跳转传递
        //            resp.sendRedirect("https://www.baidu.com");
        }else {
        //用户名密码错误，提示
        req.setAttribute("error","用户名或密码错误，请重新输入！");
        //并进行回传，即为username仍保持不变
        //回传数据需要在前端页面text框中赋值，即在后面添加value="${username}"
        req.setAttribute("username",_username);
        req.getRequestDispatcher("/login.jsp").forward(req,resp);
        //            resp.sendRedirect("https//www.baidu.com");//external 跳转传递
        //            resp.sendRedirect("/index.jsp/?username=admin");
        }*/
