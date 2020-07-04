package fpt.banking.system.dao;

import java.util.List;

import fpt.banking.system.model.Account;
import fpt.banking.system.model.LoanInterestRate;
import fpt.banking.system.model.LoanProfile;
import fpt.banking.system.model.TransactionOffice;
import fpt.banking.system.model.User;

public interface LoanProfileDAO {

	public LoanProfile findById(long id);
	
	public void saveLoanProfile(long amount, String description, Account account, LoanInterestRate loanInterestRate, User user, TransactionOffice transactionOffice);
	
	public List<LoanProfile> findLoanProfileByUser(User user);
}
