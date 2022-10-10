package com.project1.service;

import com.project1.vo.User;
import com.project1.vo.Verifier;

import java.sql.SQLException;
import java.util.List;

public interface VerifierService {

    List<Verifier> queryVerifiers(Verifier verifier, int pageNum, int lineNum) throws SQLException;

    Verifier queryVerifierById(String address) throws SQLException;

    void addVerifier(Verifier verifier) throws SQLException;

    void  deleteVerifier(String address) throws SQLException;

    int queryVerifierCount(Verifier v) throws SQLException;

    void editVerifier(Verifier verifier)throws SQLException;
}
