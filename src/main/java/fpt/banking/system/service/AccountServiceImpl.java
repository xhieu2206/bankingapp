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
	public List<Account> getAccounts(long userId) {
		return accountDAO.getAccounts(userId);
	}

	@Override
	@Transactional
	public Account getAccount(int accountId) {
		return accountDAO.getAccount(accountId);
	}

	@Override
	@Transactional
	public Account getAccount(long accountId) {
		return accountDAO.getAccount(accountId);
	}

	@Override
	@Transactional
	public List<Account> getUseableAccounts(long userId) {
		return accountDAO.getUseableAccounts(userId);
	}

	@Override
	@Transactional
	public Account findByAccountNumber(String accountNumber) {
		return accountDAO.findByAccountNumber(accountNumber);
	}

	@Override
	@Transactional
	public Account findByCardNumber(String cardNumber) {
		return accountDAO.findByCardNumber(cardNumber);
	}

	@Override
	@Transactional
	public void changeAmount(long accountId, Long amount) {
		accountDAO.changeAmount(accountId, amount);
	}

	@Override
	@Transactional
	public void lockAccount(long accountId) {
		accountDAO.lockAccount(accountId);
	}

}
