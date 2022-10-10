package com.project1.servlet;

import com.project1.service.ScholarService;
import com.project1.service.ScholarServiceImpl;
import com.project1.service.UserService;
import com.project1.service.UserServiceImpl;
import com.project1.vo.Scholar;
import com.project1.vo.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class scholarservlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //字符串索引以name为主
        String _address = req.getParameter("address2").toLowerCase();
        String _networkName = req.getParameter("networkname2");

        String addfront = _address.substring(0,6);
        String addbehind = _address.substring(_address.length()-4);
        try {
            ScholarService scholarService = new ScholarServiceImpl();
            Scholar scholar = scholarService.queryScholarBy(_address);

            //测试：
            if(scholar != null){
                //查询成功
                req.setAttribute("Accountsvalue",addfront+"..."+addbehind);
                req.setAttribute("addr",_address);
                req.setAttribute("Accountsbutton","已连接");
                req.setAttribute("network",_networkName);
//            req.setAttribute("verifier","Mint Verifier");
                req.getRequestDispatcher("/scholarPage/scholarHome.jsp").forward(req,resp);
            }
            else {
                req.setAttribute("error2","You‘re not this academy's scholar");
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
