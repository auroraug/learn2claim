package com.project1.service;

import com.project1.dao.ScholarDao;
import com.project1.dao.ScholarDaoImpl;
import com.project1.vo.Scholar;

import java.sql.SQLException;
import java.util.List;

public class ScholarServiceImpl implements ScholarService {

    ScholarDao scholarDao = new ScholarDaoImpl();

    @Override
    public List<Scholar> queryScholar(Scholar scholar, int pageNum, int lineNum) throws SQLException {
        return scholarDao.queryScholar(scholar,pageNum,lineNum);
    }

    @Override
    public int queryScholarCount(Scholar scholar) throws SQLException {
        return scholarDao.queryScholarCount(scholar);
    }

    @Override
    public Scholar queryScholarBy(String address) throws SQLException {
        return scholarDao.queryScholarBy(address);
    }

    @Override
    public void add(Scholar scholar) throws SQLException {
        scholarDao.add(scholar);
    }

    @Override
    public void edit(Scholar scholar) throws SQLException {
        scholarDao.edit(scholar);
    }

    @Override
    public void deleteScholar(String address) throws SQLException {
        scholarDao.deleteScholar(address);
    }
}
