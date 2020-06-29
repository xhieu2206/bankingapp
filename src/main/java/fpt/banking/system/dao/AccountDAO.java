package fpt.banking.system.dao;

import java.util.List;

import fpt.banking.system.model.Account;

public interface AccountDAO {

	public List<Account> getAccounts(int userId);
	
	public List<Account> getAccounts(long userId);

	public void changeAmount(long accountId, Long amount);
	
	public Account getAccount(long accountId);
	
	public Account findByAccountNumber(String accountNumber);
	
	public Account findByCardNumber(String cardNumber);
}
