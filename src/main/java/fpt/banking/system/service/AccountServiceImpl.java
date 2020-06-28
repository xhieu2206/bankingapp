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

	@Override
	@Transactional
	public Account getAccount(int accountId) {
		// TODO Auto-generated method stub
		return accountDAO.getAccount(accountId);
	}

	@Override
	@Transactional
	public Account findByAccountNumber(String accountNumber) {
		// TODO Auto-generated method stub
		return accountDAO.findByAccountNumber(accountNumber);
	}

	@Override
	@Transactional
	public Account findByCardNumber(String cardNumber) {
		// TODO Auto-generated method stub
		return accountDAO.findByCardNumber(cardNumber);
	}

}
