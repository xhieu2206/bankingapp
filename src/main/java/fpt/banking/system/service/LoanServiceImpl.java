package fpt.banking.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fpt.banking.system.dao.LoanInterestRateDAO;
import fpt.banking.system.dao.LoanProfileDAO;
import fpt.banking.system.model.Account;
import fpt.banking.system.model.LoanInterestRate;
import fpt.banking.system.model.TransactionOffice;
import fpt.banking.system.model.User;

@Service
public class LoanServiceImpl implements LoanService {
	
	@Autowired
	private LoanProfileDAO loanProfileDAO;
	
	@Autowired
	private LoanInterestRateDAO loanInterestRateDAO;

	@Override
	@Transactional
	public void saveLoanProfile(long amount, String description, Account account, LoanInterestRate loanInterestRate,
			User user, TransactionOffice transactionOffice) {
		loanProfileDAO.saveLoanProfile(amount, description, account, loanInterestRate, user, transactionOffice);
	}

	@Override
	@Transactional
	public LoanInterestRate findLoanInterestRateById(long id) {
		return loanInterestRateDAO.findById(id);
	}

}
