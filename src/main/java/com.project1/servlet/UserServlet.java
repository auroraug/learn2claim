package com.project1.servlet;

import com.project1.service.*;
import com.project1.vo.Role;
import com.project1.vo.Scholar;
import com.project1.vo.User;
import com.project1.vo.Verifier;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
//传递跳转
public class UserServlet extends HttpServlet {

    UserService userService = new UserServiceImpl();
    VerifierService verifierService = new VerifierServiceImpl();
    ScholarService scholarService = new ScholarServiceImpl();


    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl();
        verifierService = new VerifierServiceImpl();
        scholarService = new ScholarServiceImpl();
    }

    @Override
    public void destroy() {
        userService = null;
        verifierService = null;
        scholarService = null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String type = req.getParameter("type");
        switch (type) {
            case "1"://保存
                addUser(req,resp);
                break;
            case "11"://add verifier
                addVerifier(req,resp);
                break;
            case "21"://add scholar
                addScholar(req,resp);
                break;
            case "2"://编辑
                editUser(req,resp);
                break;
            case "12"://edit verifier
                editVerifier(req,resp);
                break;
            case "22"://edit scholar
                editScholar(req,resp);
                break;
            case "3"://删除
                deleteUser(req,resp);
                /*User user2 = new User();
                userService.deleteUser(user2);
                req.getRequestDispatcher("/_userservlet?type=0").forward(req, resp);*/
                break;
            case "13"://delete verifier
                deleteVerifier(req,resp);
                break;
            case "23"://delete scholar
                deleteScholar(req,resp);
                break;
            case "4"://验证
                verifyUser(req,resp);
                break;
            case "5"://admin页面
                queryVerifier(req, resp);
                break;
            case "15"://verifier页面
                queryScholar(req, resp);
                break;
            case "14"://验证verifier
                verifyteachers(req,resp);
                break;
            case "24"://验证scholar
                verifyScholar(req, resp);
                break;
            case "20"://queryVerifier
                queryVerifier(req,resp);
                break;
            case "25"://query mintNum
                queryMintNum(req,resp);
                break;
            case "32"://mint nft
                mint(req,resp);
                break;
            default://查询
                queryUsers(req,resp);
        }
    }
    public void mint(HttpServletRequest req,HttpServletResponse resp){
        try {
            Scholar scholar = new Scholar();
            scholar.setAddress(req.getParameter("address"));
            String mintNum = req.getParameter("mintnum");
            System.out.println(mintNum);

            if (mintNum != null && !mintNum.equals("")) {
                scholar.setMintnum(Integer.valueOf(1));
                scholar.setStatus(Integer.valueOf(0));
            }
            else{
                return;
            }
            scholar.setRoleId(Integer.valueOf(4));
            scholarService.edit(scholar);
            req.getRequestDispatcher("/_userservlet?type=25&address2="+req.getParameter("address")).forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void queryUsers(HttpServletRequest req, HttpServletResponse resp){
        try {
            User user2 = new User();
            user2.setUserId(req.getParameter("userId"));
            user2.setUserName(req.getParameter("userName"));
            String gender = req.getParameter("gender");
            if(gender == null){
                user2.setGender(0);
            }else {
                user2.setGender(Integer.parseInt(gender));
            }
            String pageNum = req.getParameter("pageNum");
            String changeNum = req.getParameter("changeNum");
            //pl 每页显示的行数 ，pn当前页码，cn（上一页、下一页、查询），tn总页数
            int pl = 10,pn = 1,cn = 0, tn = userService.queryUserCount(user2);

            int tp = tn / pl + (tn % pl == 0 ? 0:1);

            if (tp == 0) {
                tp = 1;
            }
            if(pageNum != null && !"".equals(pageNum)){
                pn = Integer.parseInt(pageNum);
            }
            if(changeNum != null && !"".equals(changeNum)){
                cn = Integer.parseInt(changeNum);
            }
            if(!(pn == 1 && cn == -1) && !(pn == tp && cn == 1)) {
                pn = pn + cn;
            }
            if (pn > tp) {
                pn = tp;
            }
            if(cn < -10){
                pn = 1;
            }
            if(cn > 10){
                pn = tp;
            }
            List<User> list = userService.queryUsers(user2,pn,pl);
            req.setAttribute("user",user2);
            req.setAttribute("pageNum",pn);
            req.setAttribute("totalPage",tp);
            req.setAttribute("totalNum",tn);
            req.setAttribute("userList", list);
            req.getRequestDispatcher("/userList.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void queryVerifier(HttpServletRequest req, HttpServletResponse resp){
        try {
            Verifier verifier = new Verifier();
            verifier.setAddress(req.getParameter("Address"));
            String pageNum = req.getParameter("pageNum");
            String changeNum = req.getParameter("changeNum");
            //pl 每页显示的行数 ，pn当前页码，cn（上一页、下一页、查询），tn总页数
            int pl = 10,pn = 1,cn = 0, tn = verifierService.queryVerifierCount(verifier);

            int tp = tn / pl + (tn % pl == 0 ? 0:1);

            if (tp == 0) {
                tp = 1;
            }
            if(pageNum != null && !"".equals(pageNum)){
                pn = Integer.parseInt(pageNum);
            }
            if(changeNum != null && !"".equals(changeNum)){
                cn = Integer.parseInt(changeNum);
            }
            if(!(pn == 1 && cn == -1) && !(pn == tp && cn == 1)) {
                pn = pn + cn;
            }
            if (pn > tp) {
                pn = tp;
            }
            if(cn < -10){
                pn = 1;
            }
            if(cn > 10){
                pn = tp;
            }
            List<Verifier> list = verifierService.queryVerifiers(verifier,pn,pl);
            req.setAttribute("verifier",verifier);
            req.setAttribute("pageNum",pn);
            req.setAttribute("totalPage",tp);
            req.setAttribute("totalNum",tn);
            req.setAttribute("admin", list);
            req.getRequestDispatcher("/admin.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void queryScholar(HttpServletRequest req, HttpServletResponse resp){
        try {
            Scholar scholar2 = new Scholar();
            scholar2.setAddress(req.getParameter("Address"));

            String status = req.getParameter("status");
            String mintnum = req.getParameter("mintnum");
            if(status != null && !status.equals("")){
                scholar2.setStatus(Integer.parseInt(status));
            }
            if(mintnum != null && !mintnum.equals("")){
                scholar2.setStatus(Integer.parseInt(mintnum));
            }
            String pageNum = req.getParameter("pageNum");
            String changeNum = req.getParameter("changeNum");
            //pl 每页显示的行数 ，pn当前页码，cn（上一页、下一页、查询），tn总页数
            int pl = 10,pn = 1,cn = 0, tn = scholarService.queryScholarCount(scholar2);

            int tp = tn / pl + (tn % pl == 0 ? 0:1);

            if (tp == 0) {
                tp = 1;
            }
            if(pageNum != null && !"".equals(pageNum)){
                pn = Integer.parseInt(pageNum);
            }
            if(changeNum != null && !"".equals(changeNum)){
                cn = Integer.parseInt(changeNum);
            }
            if(!(pn == 1 && cn == -1) && !(pn == tp && cn == 1)) {
                pn = pn + cn;
            }
            if (pn > tp) {
                pn = tp;
            }
            if(cn < -10){
                pn = 1;
            }
            if(cn > 10){
                pn = tp;
            }
            List<Scholar> list = scholarService.queryScholar(scholar2,pn,pl);
//            req.setAttribute("scholar",scholar2);
            req.setAttribute("pageNum",pn);
            req.setAttribute("totalPage",tp);
            req.setAttribute("totalNum",tn);
            req.setAttribute("verifier", list);
            req.getRequestDispatcher("/verifier.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void queryMintNum(HttpServletRequest req, HttpServletResponse resp){
        try {
            Scholar s = new Scholar();
            s.setAddress(req.getParameter("address2"));

            String pageNum = req.getParameter("pageNum");
            String changeNum = req.getParameter("changeNum");
            //pl 每页显示的行数 ，pn当前页码，cn（上一页、下一页、查询），tn总页数
            int pl = 10,pn = 1,cn = 0, tn = scholarService.queryScholarCount(s);

            int tp = tn / pl + (tn % pl == 0 ? 0:1);

            if (tp == 0) {
                tp = 1;
            }
            if(pageNum != null && !"".equals(pageNum)){
                pn = Integer.parseInt(pageNum);
            }
            if(changeNum != null && !"".equals(changeNum)){
                cn = Integer.parseInt(changeNum);
            }
            if(!(pn == 1 && cn == -1) && !(pn == tp && cn == 1)) {
                pn = pn + cn;
            }
            if (pn > tp) {
                pn = tp;
            }
            if(cn < -10){
                pn = 1;
            }
            if(cn > 10){
                pn = tp;
            }
            List<Scholar> list = scholarService.queryScholar(s,pn,pl);
            req.setAttribute("scholar",s);
            req.setAttribute("mintnum",s.getMintnum());
            req.setAttribute("personal", list);
            req.getRequestDispatcher("/scholar.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addUser(HttpServletRequest req, HttpServletResponse resp){
        try {
            User user = new User();
            user.setUserId(req.getParameter("_userId"));
            user.setUserName(req.getParameter("_userName"));
            user.setUserPassword(req.getParameter("_userPassword"));
            String genderStr = req.getParameter("_gender");
            String flag = req.getParameter("flag");
            if (genderStr != null) {
                user.setGender(Integer.valueOf(genderStr));
            }
            String roleIdStr = req.getParameter("_roleId");
            if (roleIdStr == null) {
                user.setRoleId(2);
            } else {
                user.setRoleId(Integer.valueOf(roleIdStr));
            }
            if("1".equals(flag)){
                userService.editUser(user);
            }else {
                userService.addUser(user);
            }
            req.getRequestDispatcher("/_userservlet?type=0").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addVerifier(HttpServletRequest req, HttpServletResponse resp){
        try {
            Verifier verifier = new Verifier();
            verifier.setAddress(req.getParameter("_address"));

            String flag = req.getParameter("flag");
            verifier.setRoleId(Integer.valueOf(3));

//            String roleIdStr = "3";
            /*if (roleIdStr == null) {
                verifier.setRoleId(2);
            } else {
            }*/
            if("1".equals(flag)){
                verifierService.editVerifier(verifier);
            }else {
                verifierService.addVerifier(verifier);
            }
            req.getRequestDispatcher("/_userservlet?type=5").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addScholar(HttpServletRequest req, HttpServletResponse resp){
        try {
            Scholar scholar = new Scholar();
            scholar.setAddress(req.getParameter("_address"));
            String statusStr1 = req.getParameter("_status1");
            String statusStr2 = req.getParameter("_status2");
            String mintNum = req.getParameter("_mintnum");
            String flag = req.getParameter("flag");
            System.out.println(mintNum);
            if (statusStr1 != null && !statusStr1.equals("")) {
                scholar.setStatus(Integer.valueOf(statusStr1));
            }
            if (statusStr2 != null && !statusStr2.equals("")) {
                scholar.setStatus(Integer.valueOf(statusStr2));
            }
            if (mintNum != null && !mintNum.equals("")) {
                scholar.setMintnum(Integer.valueOf(mintNum));
            }
            scholar.setRoleId(Integer.valueOf(4));
            if("1".equals(flag)){
                scholarService.edit(scholar);
            }else {
                scholarService.add(scholar);
            }
            req.getRequestDispatcher("/_userservlet?type=15").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void verifyUser(HttpServletRequest req, HttpServletResponse resp){
        try {
            String userId = req.getParameter("userId");
            User user1 = userService.queryUserById(userId);
            if (user1 == null) {
                resp.getWriter().print("1");
            } else {
                resp.getWriter().print("2");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void verifyteachers(HttpServletRequest req, HttpServletResponse resp){
        try {
            String address = req.getParameter("address");
            Verifier verifier1 = verifierService.queryVerifierById(address);
//            User user1 = userService.queryUserById(userId);
            if (verifier1 == null) {
                resp.getWriter().print("1");
            } else {
                resp.getWriter().print("2");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void verifyScholar(HttpServletRequest req, HttpServletResponse resp){
        try {
            String address = req.getParameter("address");
            Scholar scholar1 = scholarService.queryScholarBy(address);
            if (scholar1 == null) {
                resp.getWriter().print("1");
            } else {
                resp.getWriter().print("2");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void editUser(HttpServletRequest req, HttpServletResponse resp){
        try {
            String userId = req.getParameter("userId");

            if(userId == null || "".equals(userId)){
                //add
            }else {
                //edit
                User user = userService.queryUserById(userId);
                req.setAttribute("user",user);
                req.setAttribute("flag","1");
                req.getRequestDispatcher("/addUser.jsp").forward(req,resp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void editVerifier(HttpServletRequest req, HttpServletResponse resp){
        try {
            String address = req.getParameter("address");

            if(address == null || "".equals(address)){
                //add
            }else {
                //edit
                Verifier verifier = verifierService.queryVerifierById(address);
                req.setAttribute("address",verifier.getAddress());
                req.setAttribute("flag","1");
                req.getRequestDispatcher("/addVerifier.jsp").forward(req,resp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void editScholar(HttpServletRequest req, HttpServletResponse resp){
        try {
            String address = req.getParameter("address");

            if(address == null || "".equals(address)){
                //add
            }else {
                //edit
                Scholar scholar = scholarService.queryScholarBy(address);
                req.setAttribute("address",scholar.getAddress());
                req.setAttribute("flag","1");
                req.getRequestDispatcher("/addScholar.jsp").forward(req,resp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void deleteUser(HttpServletRequest req, HttpServletResponse resp){
        try {
            String userId = req.getParameter("duserId");
            if(userId != null && !"".equals(userId)){
                userService.deleteUser(userId);
            }
            req.getRequestDispatcher("/_userservlet?type=0").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void deleteVerifier(HttpServletRequest req, HttpServletResponse resp){
        try {
            String address = req.getParameter("daddress");
            if(address != null && !"".equals(address)){
                verifierService.deleteVerifier(address);
            }
            req.getRequestDispatcher("/_userservlet?type=5").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void deleteScholar(HttpServletRequest req, HttpServletResponse resp){
        try {
            String address = req.getParameter("daddress");
            if(address != null && !"".equals(address)){
                scholarService.deleteScholar(address);
            }
            req.getRequestDispatcher("/_userservlet?type=15").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
