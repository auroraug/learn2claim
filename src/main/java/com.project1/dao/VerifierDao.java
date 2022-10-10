package com.project1.dao;

import com.project1.vo.Verifier;

import java.sql.SQLException;
import java.util.List;

public interface VerifierDao {

    List<Verifier> queryVerifier(Verifier verifier, int pageNum, int lineNum) throws SQLException;

    int queryVerifierCount(Verifier verifier)throws SQLException;

    Verifier queryVerifierBy(String address)throws SQLException;

    void add(Verifier verifier)throws SQLException;

    void edit(Verifier verifier) throws SQLException;

    void deleteVerifier(String address)throws SQLException;
}
