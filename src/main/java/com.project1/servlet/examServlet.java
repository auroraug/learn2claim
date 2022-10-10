package com.project1.servlet;

import com.project1.service.ExamService;
import com.project1.service.ExamServiceImpl;
import com.project1.vo.Exam;
import com.project1.vo.Scholar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class examServlet extends HttpServlet {

    ExamService examService = new ExamServiceImpl();

    @Override
    public void init() throws ServletException {
        examService = new ExamServiceImpl();
    }

    @Override
    public void destroy() {
        examService = null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String type = req.getParameter("type");
        switch (type) {
            case "1"://保存
                addExam(req,resp);
                break;
            case "2"://编辑
                editExam(req,resp);
                break;
            case "3"://删除
                deleteExam(req,resp);
                break;
            case "4"://查询
                queryExam(req,resp);
                break;
            case "5"://验证
                verfyExamId(req,resp);
                break;
        }
    }
    public void addExam(HttpServletRequest req, HttpServletResponse resp){
        try {
            Exam exam = new Exam();
            exam.setExamId(req.getParameter("_examid"));

            String flag = req.getParameter("flag");
            exam.setDescription(req.getParameter("_description"));

            String time = req.getParameter("_time");
            if (time != null) {
                exam.setTime(Integer.parseInt(time));
            } else {
                req.setAttribute("msg","考试时间未填写");
                req.getRequestDispatcher("examManagement.jsp").forward(req, resp);
            }
            if("1".equals(flag)){
                examService.edit(exam);
            }else {
                examService.add(exam);
            }
            req.getRequestDispatcher("/_examservlet?type=4").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void editExam(HttpServletRequest req, HttpServletResponse resp){
        try {
            String examId = req.getParameter("examid");

            if(examId == null || "".equals(examId)){
                //add
            }else {
                //edit
                Exam exam = examService.queryExamBy(examId);
                req.setAttribute("examId",exam.getExamId());
                req.setAttribute("description",exam.getDescription());
                req.setAttribute("time",exam.getTime());
                req.setAttribute("flag","1");
                req.getRequestDispatcher("/addExam.jsp").forward(req,resp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void deleteExam(HttpServletRequest req, HttpServletResponse resp){
        try {
            String examid = req.getParameter("dexamid");
            if(examid != null && !"".equals(examid)){
                examService.deleteExam(examid);
            }
            req.getRequestDispatcher("/_examservlet?type=4").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void queryExam(HttpServletRequest req, HttpServletResponse resp){
        try {
            Exam exam = new Exam();
            exam.setExamId(req.getParameter("ExamId"));
            exam.setDescription(req.getParameter("description"));
            String pageNum = req.getParameter("pageNum");
            String changeNum = req.getParameter("changeNum");
            //pl 每页显示的行数 ，pn当前页码，cn（上一页、下一页、查询），tn总页数
            int pl = 10,pn = 1,cn = 0, tn = examService.queryExamCount(exam);

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
            List<Exam> list = examService.queryExam(exam,pn,pl);
            req.setAttribute("pageNum",pn);
            req.setAttribute("totalPage",tp);
            req.setAttribute("totalNum",tn);
            req.setAttribute("exam",list);
            req.getRequestDispatcher("verifierPage/examManagement.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void verfyExamId(HttpServletRequest req, HttpServletResponse resp){
        try {
            String examId = req.getParameter("examid");
            Exam exam1 = examService.queryExamBy(examId);
            if (exam1 == null) {
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
}
