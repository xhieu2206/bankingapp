package fpt.banking.system.dao;

import java.util.List;

import fpt.banking.system.model.LoanInterestRate;

public interface LoanInterestRateDAO {
	public LoanInterestRate findById(long id);
	
	public List<LoanInterestRate> getAllLoanInterestRate();
}
