package com.project1.service;

import com.project1.vo.Scholar;

import java.sql.SQLException;
import java.util.List;

public interface ScholarService {

    List<Scholar> queryScholar(Scholar scholar, int pageNum, int lineNum) throws SQLException;

    int queryScholarCount(Scholar scholar)throws SQLException;

    Scholar queryScholarBy(String address)throws SQLException;

    void add(Scholar scholar)throws SQLException;

    void edit(Scholar scholar) throws SQLException;

    void deleteScholar(String address)throws SQLException;
}
