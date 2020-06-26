package fpt.banking.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpt.banking.system.dao.TransactionDAO;
import fpt.banking.system.model.Transaction;
import fpt.banking.system.payload.TransactionsResponse;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	private TransactionDAO transactionDAO;

	@Override
	public TransactionsResponse getTransactions(int accountId, int page) {
		// TODO Auto-generated method stub
		return transactionDAO.getTransactions(accountId, page);
	}

}
