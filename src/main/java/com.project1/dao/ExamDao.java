package com.project1.dao;

import com.project1.vo.Exam;
import java.sql.SQLException;
import java.util.List;

public interface ExamDao {

    List<Exam> queryExam(Exam exam, int pageNum, int lineNum) throws SQLException;

    int queryExamCount(Exam exam)throws SQLException;

    Exam queryExamBy(String examId)throws SQLException;

    void add(Exam exam)throws SQLException;

    void edit(Exam exam) throws SQLException;

    void deleteExam(String examId)throws SQLException;
}
