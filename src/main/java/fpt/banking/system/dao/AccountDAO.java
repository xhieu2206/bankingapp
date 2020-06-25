package fpt.banking.system.dao;

import java.util.List;

import fpt.banking.system.model.Account;

public interface AccountDAO {

	public List<Account> getAccounts(int userId);
}
