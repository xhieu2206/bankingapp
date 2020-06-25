package fpt.banking.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fpt.banking.system.dao.AccountDAO;
import fpt.banking.system.model.Account;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDAO accountDAO;
	
	@Override
	@Transactional
	public List<Account> getAccounts(int userId) {
		// TODO Auto-generated method stub
		return accountDAO.getAccounts(userId);
	}

}
