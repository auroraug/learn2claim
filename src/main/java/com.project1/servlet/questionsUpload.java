package com.project1.servlet;

import util.ReadExcelUtil;
import util.UploadFileUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Map;
import com.project1.db.MysqlDB;

public class questionsUpload extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String TARGET_DIRECTORY="excelfile";        //上传文件的目录*
        int UPLOAD_MAX_FILE_SIZE = 1024 * 1024 * 25;    //最大文件上传值25MB
        int MEMORY_THRESHOLD = 1024 * 1024 * 10;        //临时内存临界值10MB
        String uploadPath = this.getServletContext().getRealPath("/")+ File.separator+TARGET_DIRECTORY;
        Map<String,String> map = UploadFileUtil.uploadFileForm(uploadPath,req,UPLOAD_MAX_FILE_SIZE,MEMORY_THRESHOLD);
        String message = "";  //用作重定向提示参数
        if (map!=null){//如果上传文件成功，读取文件写入数据库
            String excelFilePath = uploadPath+File.separator+map.get("fileName");
            List<List<String>> rowList = ReadExcelUtil.readExcelList(excelFilePath);  //读取Excel文件
            String exam_description_id = map.get("exam_description_id");
            String exam_description = map.get("exam_description");
            String exam_timeStr = map.get("exam_time");
            int exam_time = Integer.parseInt(exam_timeStr);
            try {
                MysqlDB mysql = new MysqlDB();
                Connection connection = mysql.getConnect();
                //查询数据库
                Statement statement = null;
                //获取结果
                ResultSet resultSet = null;
                //连接数据库
                /*String url = this.getServletContext().getInitParameter("url");
                String user = this.getServletContext().getInitParameter("user");
                String pwd = this.getServletContext().getInitParameter("password");
                Connection connection = DriverManager.getConnection(url, user, pwd);*/
                //把数据插入到记录问题描述的表exams_description
                PreparedStatement preparedStatement = connection.prepareStatement("insert into" +
                        " exam_description(id,exam_description,exam_time) values(?,?,?)");
                preparedStatement.setString(1,exam_description_id);
                preparedStatement.setString(2,exam_description);
                preparedStatement.setInt(3,exam_time);
                preparedStatement.executeUpdate();//由于数据库exam_description_id为自增，插入数据后id+1
                //查询表exams_description中存储的试题描述ID
                /*preparedStatement = connection.prepareStatement("select examId from examInfo" +
                        " where description=?");
                preparedStatement.setString(1,exam_description);
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                String exam_description_id1 = resultSet.getString(1);*/
                //把excel读取出来的临时数据rowList存储到questions表中
                for (List<String> row:rowList){
                    preparedStatement = connection.prepareStatement("insert into questions(exam_description_id," +
                            "question,options,correct_answer,min_score,type) values(?,?,?,?,?,?)");
                    preparedStatement.setString(1,exam_description_id);
                    preparedStatement.setString(2,row.get(0));
                    preparedStatement.setString(3,row.get(1));
                    preparedStatement.setString(4,row.get(2));
                    preparedStatement.setInt(5, Integer.parseInt(row.get(3)));
                    preparedStatement.setString(6,row.get(4));
                    preparedStatement.executeUpdate();
                }
                System.out.println(preparedStatement);
                //存储完成，把上传的excel文件删除
                File excelFile = new File(excelFilePath);
                if (excelFile.exists()){
                    excelFile.delete();
                }
                message = "success";
            }
            catch (SQLException e){
                File excelFile = new File(excelFilePath);
                if (excelFile.exists()){
                    excelFile.delete();
                }
                message = "error";
                e.printStackTrace();
            }
        }
        else {
            message = "error";
        }
        resp.sendRedirect("/_examservlet?type=4");
    }
}
