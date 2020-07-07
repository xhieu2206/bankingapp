package fpt.banking.system.dao;

import java.util.List;

import fpt.banking.system.model.Account;
import fpt.banking.system.model.User;

public interface AccountDAO {

	public List<Account> getAccounts(int userId);
	
	public List<Account> getAccounts(long userId);
	
	public List<Account> getUseableAccounts(long userId);

	public void changeAmount(long accountId, Long amount);
	
	public Account getAccount(long accountId);
	
	public Account findByAccountNumber(String accountNumber);
	
	public Account findByCardNumber(String cardNumber);
	
	public long saveAccount(String accountNumber, User user, long amount, String pinCodeEncoderString);

	public void lockAccount(long accountId);
}
