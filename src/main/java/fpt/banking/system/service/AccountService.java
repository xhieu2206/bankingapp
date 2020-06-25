package fpt.banking.system.service;

import java.util.List;

import fpt.banking.system.model.Account;

public interface AccountService {

	public List<Account> getAccounts(int userId);
}
