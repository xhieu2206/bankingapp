package fpt.banking.system.dao;

import java.util.List;

import fpt.banking.system.model.Transaction;
import fpt.banking.system.payload.TransactionsResponse;

public interface TransactionDAO {

	public List<Transaction> getTransactions(long accountId, int page);
	
	public List<Transaction> getTransactionsWithSearchTerm(long accountId, int page, String term);
	
	public int getTotalTransactions(long accountId);
	
	public int getTotalTransactionsWithSearchTerm(long accountId, String term);
	
	public void saveTransaction(long accountId, Long changeAmount, Long 
			amountAfterTransaction, int transactionTypeId,
			String description);

	public void saveTranserTransaction(long accountId, long changeAmount, long amountAfterTransaction, int transactionTypeId, String description,
			String fullname, String accountNumber);
}
