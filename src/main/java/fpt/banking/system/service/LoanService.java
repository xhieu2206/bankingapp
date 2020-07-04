package fpt.banking.system.service;

import fpt.banking.system.model.Account;
import fpt.banking.system.model.LoanInterestRate;
import fpt.banking.system.model.TransactionOffice;
import fpt.banking.system.model.User;

public interface LoanService {

	public void saveLoanProfile(long amount, String description, Account account, LoanInterestRate loanInterestRate, User user, TransactionOffice transactionOffice);
}
