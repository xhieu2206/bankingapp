package fpt.banking.system.service;

import java.util.List;

import fpt.banking.system.model.Account;
import fpt.banking.system.model.LoanInterestRate;
import fpt.banking.system.model.TransactionOffice;
import fpt.banking.system.model.User;

public interface LoanService {

	public long saveLoanProfile(long amount, String description, Account account, LoanInterestRate loanInterestRate, User user, TransactionOffice transactionOffice);
	
	public LoanInterestRate findLoanInterestRateById(long id);
	
	public List<LoanInterestRate> getAllLoanInterestRate();
}
