package com.project1.servlet;

import com.project1.service.*;
import com.project1.vo.admin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class adminservlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //字符串索引以name为主
        String _address = req.getParameter("address").toLowerCase();
        String _networkName = req.getParameter("networkname");
        System.out.println(_networkName);
        String addfront = _address.substring(0,6);
        String addbehind = _address.substring(_address.length()-4);
        System.out.println(addfront+"..."+addbehind);
        try {
            AdminService adminService = new AdminServiceImpl();
            admin admin = adminService.queryAdminBy(_address);

            //测试：
            if(admin != null){
                //查询成功
//              返回的值赋给jsp中的id
                req.setAttribute("Accountsvalue",addfront+"..."+addbehind);
                req.setAttribute("Accountsbutton","已连接");
                req.setAttribute("network",_networkName);
//              req.setAttribute("verifier","Mint Verifier");
                req.getRequestDispatcher("/adminPage/adminHome.jsp").forward(req,resp);
            }
            else {
                req.setAttribute("error","You‘re not administrator!");
                //并进行回传，即为username仍保持不变
                //回传数据需要在前端页面text框中赋值，即在后面添加value="${username}"
//                req.setAttribute("username",_username);
                req.getRequestDispatcher("/login.jsp").forward(req,resp);
                //            resp.sendRedirect("https//www.baidu.com");//external 跳转传递
                //            resp.sendRedirect("/index.jsp/?username=admin");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
