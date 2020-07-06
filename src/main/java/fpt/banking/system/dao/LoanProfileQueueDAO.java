package fpt.banking.system.dao;

import fpt.banking.system.model.LoanProfileQueue;

public interface LoanProfileQueueDAO {

	public LoanProfileQueue findById(long id);
	
	public long saveLoanProfileQueue(String otpCode, long loanProfileId, java.util.Date expriedAt);
	
	public void deleteLoanProfileQueue(long id);
	
	public LoanProfileQueue findLoanProfileQueueByLoanProfileId(long loanProfileId);
}
