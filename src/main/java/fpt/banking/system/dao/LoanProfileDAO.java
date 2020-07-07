package fpt.banking.system.dao;

import java.util.List;

import fpt.banking.system.model.Account;
import fpt.banking.system.model.LoanInterestRate;
import fpt.banking.system.model.LoanProfile;
import fpt.banking.system.model.LoanProfileQueue;
import fpt.banking.system.model.TransactionOffice;
import fpt.banking.system.model.User;
import fpt.banking.system.payload.LoanProfilesResponsePayload;

public interface LoanProfileDAO {

	public LoanProfile findById(long id);
	
	public long saveLoanProfile(long amount, String description, Account account, LoanInterestRate loanInterestRate, User user, TransactionOffice transactionOffice);
	
	public List<LoanProfile> findLoanProfilesByUser(User user);
	
	public void confirmLoanProfile(long id, String employeeConfirmedName, long employeeConfirmedId);
	
	public long approvedLoanProfileByTransactionManager(long loanProfileId);
	
	public long approvedLoanProfileByBranchManager(long loanProfileId);
	
	public void rejectLoanProffile(long loanProfileId, String rejectedReason);
	
	public List<LoanProfile> getLoanProfilesByTransactionOffice(long transactionOfficeId, int page, String status);
	
	public long getTotalLoanProfilesByTransactionOffice(long transactionOfficeId, String status);
	
	public List<LoanProfile> getLoanProfilesForAnEmployee(User employee, int page);
	
	public long getTotalLoanProfilesForAnEmployee(User employee);
}
