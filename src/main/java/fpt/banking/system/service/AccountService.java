package fpt.banking.system.service;

import java.util.List;

import fpt.banking.system.model.Account;

public interface AccountService {

	public List<Account> getAccounts(long userId);
	
	public List<Account> getUseableAccounts(long userId);
	
	public Account getAccount(int accountId);
	
	public Account getAccount(long accountId);
	
	public Account findByAccountNumber(String accountNumber);
	
	public Account findByCardNumber(String cardNumber);
	
	public void changeAmount(long accountId, Long amount);
	
	public void lockAccount(long accountId);
}
