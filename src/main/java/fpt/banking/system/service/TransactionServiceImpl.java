package fpt.banking.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fpt.banking.system.dao.TransactionDAO;
import fpt.banking.system.model.Transaction;
import fpt.banking.system.payload.TransactionsResponse;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	private TransactionDAO transactionDAO;

	@Override
	@Transactional
	public TransactionsResponse getTransactions(int accountId, int page) {
		TransactionsResponse transactionResponse =
				new TransactionsResponse();
		transactionResponse.setCurrentPage(page);
		transactionResponse.setTotalTransaction(transactionDAO.getTotalTransactions(accountId));
		int totalPage = (int) Math.ceil(transactionDAO.getTotalTransactions(accountId) / 5);
		if (transactionDAO.getTotalTransactions(accountId) % 5 > 0) {
			totalPage ++;
		}
		transactionResponse.setTotalPage(totalPage);
		transactionResponse.setTransactions(transactionDAO.getTransactions(accountId, page));
		return transactionResponse;
	}
}
