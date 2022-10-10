package com.project1.service;

import com.project1.dao.VerifierDao;
import com.project1.dao.VerifierDaoImpl;
import com.project1.vo.Verifier;

import java.sql.SQLException;
import java.util.List;

public class VerifierServiceImpl implements VerifierService{

    VerifierDao verifierDao = new VerifierDaoImpl();
    @Override
    public List<Verifier> queryVerifiers(Verifier verifier, int pageNum, int lineNum) throws SQLException {
        return verifierDao.queryVerifier(verifier,pageNum,lineNum);
    }

    @Override
    public Verifier queryVerifierById(String address) throws SQLException {
        return verifierDao.queryVerifierBy(address);
    }

    @Override
    public void addVerifier(Verifier verifier) throws SQLException {
        verifierDao.add(verifier);
    }

    @Override
    public void deleteVerifier(String address) throws SQLException {
        verifierDao.deleteVerifier(address);
    }

    @Override
    public int queryVerifierCount(Verifier v) throws SQLException {
        return verifierDao.queryVerifierCount(v);
    }

    @Override
    public void editVerifier(Verifier verifier) throws SQLException {
        verifierDao.edit(verifier);
    }
}
