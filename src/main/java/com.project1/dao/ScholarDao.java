package com.project1.dao;

import com.project1.vo.Scholar;
import com.project1.vo.Verifier;

import java.sql.SQLException;
import java.util.List;

public interface ScholarDao {

    List<Scholar> queryScholar(Scholar scholar, int pageNum, int lineNum) throws SQLException;

    int queryScholarCount(Scholar scholar)throws SQLException;

    Scholar queryScholarBy(String address)throws SQLException;

    void add(Scholar scholar)throws SQLException;

    void edit(Scholar scholar) throws SQLException;

    void deleteScholar(String address)throws SQLException;
}
