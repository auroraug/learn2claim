package com.project1.service;

import com.project1.vo.Exam;

import java.sql.SQLException;
import java.util.List;

public interface ExamService {
    List<Exam> queryExam(Exam exam, int pageNum, int lineNum) throws SQLException;

    int queryExamCount(Exam exam)throws SQLException;

    Exam queryExamBy(String examid)throws SQLException;

    void add(Exam exam)throws SQLException;

    void edit(Exam exam) throws SQLException;

    void deleteExam(String examid)throws SQLException;
}
