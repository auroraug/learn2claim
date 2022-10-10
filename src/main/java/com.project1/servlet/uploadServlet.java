package com.project1.servlet;

import util.ReadExcelUtil;
import util.UploadFileUtil;
import com.project1.db.MysqlDB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class uploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String type = req.getParameter("type");

        switch (type){
            case "1"://scholar
                scholar(req,resp);
                break;
            case "2"://exam
                exam(req,resp);
                break;
        }
    }
    public void scholar(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String TARGET_DIRECTORY="excelfile";        //上传文件的目录*
        String uploadPath = this.getServletContext().getRealPath("/")+ File.separator+TARGET_DIRECTORY;
        String fileName = UploadFileUtil.uploadFile(uploadPath,req);
        String message = "";  //用作重定向提示参数
        if (fileName != null){//如果上传文件成功，读取文件写入数据库
            String excelFilePath = uploadPath + File.separator + fileName;
            List<List<String>> rowList = ReadExcelUtil.readExcelList(excelFilePath);  //读取Excel文件
            int totalRow = rowList.size();
            int successRow = 0;
            //连接数据库
            MysqlDB mysql = new MysqlDB();
            Connection connection = mysql.getConnect();
            //查询数据库
            Statement statement = null;
            //获取结果
            ResultSet resultSet = null;

            /*连接数据库
                String url = this.getServletContext().getInitParameter("url");
                String user = this.getServletContext().getInitParameter("user");
                String pwd = this.getServletContext().getInitParameter("password");
                Connection connection = DriverManager.getConnection(url, user, pwd);*/
            //把数据插入到数据表格
            for (int i = 0; i < rowList.size(); i++) {
                try {
//                    statement = connection.createStatement();
//                    resultSet = statement.executeQuery(sql);//sql查询语句
//                    statement.execute(sql.toString());//updata & insert 语句

                    PreparedStatement preparedStatement = connection.prepareStatement("insert into" +
                            " scholarInfo(address,roleid,status,mintnum) values(?,?,?,?)");
                    preparedStatement.setString(1,rowList.get(i).get(0));
                    preparedStatement.setInt(2,4);
                    preparedStatement.setString(3,rowList.get(i).get(1));
                    preparedStatement.setString(4,rowList.get(i).get(2));
                    preparedStatement.executeUpdate();
                    successRow ++;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            //存储完成，把上传的excel文件删除
            File excelFile = new File(excelFilePath);
            if (excelFile.exists()){
                excelFile.delete();
            }
            message = "?importmessage=success" + "&totalRow=" + totalRow + "&successRow=" + successRow;
            System.out.println(message);
        }
        else {
            resp.sendRedirect("verifier.jsp");
            message = "?importmessage=error";
        }
        resp.sendRedirect("/_userservlet?type=15");
    }
    public void exam(HttpServletRequest req, HttpServletResponse resp)throws IOException{
        String TARGET_DIRECTORY="excelfile";        //上传文件的目录*
        String uploadPath = this.getServletContext().getRealPath("/")+ File.separator+TARGET_DIRECTORY;
        String fileName = UploadFileUtil.uploadFile(uploadPath,req);
        String message = "";  //用作重定向提示参数
        if (fileName != null){//如果上传文件成功，读取文件写入数据库
            String excelFilePath = uploadPath + File.separator + fileName;
            List<List<String>> rowList = ReadExcelUtil.readExcelList(excelFilePath);  //读取Excel文件
            int totalRow = rowList.size();
            int successRow = 0;
            //连接数据库
            MysqlDB mysql = new MysqlDB();
            Connection connection = mysql.getConnect();
            //查询数据库
            Statement statement = null;
            //获取结果
            ResultSet resultSet = null;

            /*连接数据库
                String url = this.getServletContext().getInitParameter("url");
                String user = this.getServletContext().getInitParameter("user");
                String pwd = this.getServletContext().getInitParameter("password");
                Connection connection = DriverManager.getConnection(url, user, pwd);*/
            //把数据插入到数据表格
            for (int i = 0; i < rowList.size(); i++) {
                try {
//                    statement = connection.createStatement();
//                    resultSet = statement.executeQuery(sql);//sql查询语句
//                    statement.execute(sql.toString());//updata & insert 语句

                    PreparedStatement preparedStatement = connection.prepareStatement("insert into" +
                            " exam_description(id,exam_description,exam_time) values(?,?,?)");
                    preparedStatement.setString(1,rowList.get(i).get(0));
                    preparedStatement.setString(2,rowList.get(i).get(1));
                    preparedStatement.setInt(3, Integer.parseInt(rowList.get(i).get(2)));
                    preparedStatement.executeUpdate();
                    successRow ++;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            //存储完成，把上传的excel文件删除
            File excelFile = new File(excelFilePath);
            if (excelFile.exists()){
                excelFile.delete();
            }
            message = "?importmessage=success" + "&totalRow=" + totalRow + "&successRow=" + successRow;
            System.out.println(message);
        }
        else {
            resp.sendRedirect("verifierPage/examManagement.jsp");
            message = "?importmessage=error";
        }
        resp.sendRedirect("/_examservlet?type=4");
    }
}
