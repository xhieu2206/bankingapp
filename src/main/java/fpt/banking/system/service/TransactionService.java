package fpt.banking.system.service;

import fpt.banking.system.payload.TransactionsResponse;

public interface TransactionService {

	public TransactionsResponse getTransactions(int accountId, int page);
	
	public TransactionsResponse getTransactionsWithSearchTerm(long accountId, int page, String searchTerm);
	
	public void saveTransaction(long accountId, Long amount, Long amountAfterTransaction, int transactionTypeId, String description);
}
