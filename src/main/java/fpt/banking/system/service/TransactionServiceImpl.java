package fpt.banking.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fpt.banking.system.dao.TransactionDAO;
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
		transactionResponse.setPageNumber(page);
		transactionResponse.setTotalCount(transactionDAO.getTotalTransactions(accountId));
		int totalPage = (int) Math.ceil(transactionDAO.getTotalTransactions(accountId) / 5);
		if (transactionDAO.getTotalTransactions(accountId) % 5 > 0) {
			totalPage ++;
		}
		transactionResponse.setTotalPage(totalPage);
		transactionResponse.setItems(transactionDAO.getTransactions(accountId, page));
		transactionResponse.setPageSize(transactionResponse.getItems().size());
		return transactionResponse;
	}

	@Override
	@Transactional
	public void saveTransaction(long accountId, Long amount, Long amountAfterTransaction, int transactionTypeId,
			String description) {
		transactionDAO.saveTransaction(accountId, amount, amountAfterTransaction, transactionTypeId, description);
	}

	@Override
	@Transactional
	public TransactionsResponse getTransactionsWithSearchTerm(long accountId, int page, String searchTerm) {
		TransactionsResponse transactionResponse = new TransactionsResponse();
		transactionResponse.setPageNumber(page);
		transactionResponse.setTotalCount(transactionDAO.getTotalTransactionsWithSearchTerm(accountId, searchTerm));
		int totalPage = (int) Math.ceil(transactionDAO.getTotalTransactionsWithSearchTerm(accountId, searchTerm) / 5);
		if (transactionDAO.getTotalTransactionsWithSearchTerm(accountId, searchTerm) % 5 > 0) {
			totalPage ++;
		}
		transactionResponse.setTotalPage(totalPage);
		transactionResponse.setItems(transactionDAO.getTransactionsWithSearchTerm(accountId, page, searchTerm));
		transactionResponse.setPageSize(transactionResponse.getItems().size());
		return transactionResponse;
	}

	@Override
	@Transactional
	public TransactionsResponse getTransactionWithTimeFilter(long accountId, int page, int year, int month) {
		TransactionsResponse transactionsResponse = new TransactionsResponse();
		transactionsResponse.setPageNumber(page);
		transactionsResponse.setTotalCount(transactionDAO.getTotalTransactionsWithTimeFileter(accountId, year, month));
		int totalPage = (int) Math.ceil(transactionDAO.getTotalTransactionsWithTimeFileter(accountId, year, month) / 5);
		if (transactionDAO.getTotalTransactionsWithTimeFileter(accountId, year, month) % 5 > 0) {
			totalPage ++;
		}
		transactionsResponse.setTotalPage(totalPage);
		transactionsResponse.setItems(transactionDAO.getTransactionsWithTimeFileter(accountId, page, year, month));
		transactionsResponse.setPageSize(transactionsResponse.getItems().size());
		return transactionsResponse;
	}
}
