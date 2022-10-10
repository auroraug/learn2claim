package com.project1.service;

import com.project1.dao.ExamDao;
import com.project1.dao.ExamDaoImpl;
import com.project1.vo.Exam;

import java.sql.SQLException;
import java.util.List;

public class ExamServiceImpl implements ExamService {

    ExamDao examDao = new ExamDaoImpl();

    @Override
    public List<Exam> queryExam(Exam exam, int pageNum, int lineNum) throws SQLException {
        return examDao.queryExam(exam, pageNum, lineNum);
    }

    @Override
    public int queryExamCount(Exam exam) throws SQLException {
        return examDao.queryExamCount(exam);
    }

    @Override
    public Exam queryExamBy(String examid) throws SQLException {
        return examDao.queryExamBy(examid);
    }

    @Override
    public void add(Exam exam) throws SQLException {
        examDao.add(exam);
    }

    @Override
    public void edit(Exam exam) throws SQLException {
        examDao.edit(exam);
    }

    @Override
    public void deleteExam(String examid) throws SQLException {
        examDao.deleteExam(examid);
    }
}
