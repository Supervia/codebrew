package com.mycompany.codebrew.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mycompany.codebrew.dao.AccountDao;
import com.mycompany.codebrew.dao.MyPageDao;
import com.mycompany.codebrew.dto.Account;
import com.mycompany.codebrew.dto.Board;
import com.mycompany.codebrew.dto.Pager;
import com.mycompany.codebrew.dto.Product;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MyPageService {
	
	@Autowired
	private MyPageDao myPageDao;
	
	@Autowired
	private AccountDao accountDao;
	
	
	public int getTotalRow(String acId) {
		int totalRows = myPageDao.count(acId);
		return totalRows;
	}

	public List<Board> getMyBoard(Pager pager) {
	
		List<Board> myBoardList = myPageDao.selectMyboard(pager);
		return myBoardList;
	}
	
	public Account getAccount(String acId) {
		Account account = accountDao.selectByAcId(acId);
			return account;
		}

	public void ChangeAccount(Account account) {
		myPageDao.updateAccount(account);
	}
	
	public void myInfoChange(Account account) {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		account.setAcPassword(passwordEncoder.encode(account.getAcPassword()));		
		accountDao.insert(account);
		log.info("prid: " + account.getAcId());
		log.info("acPassword: " + account.getAcPassword());
	}
}
