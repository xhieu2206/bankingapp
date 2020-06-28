package fpt.banking.system.dao;

import java.util.List;

import fpt.banking.system.model.Transaction;
import fpt.banking.system.payload.TransactionsResponse;

public interface TransactionDAO {

	public List<Transaction> getTransactions(long accountId, int page);
	
	public int getTotalTransactions(long accountId);
	
	public void saveTransaction(long accountId, Long amount, Long 
			amountAfterTransaction, int transactionTypeId,
			String description);
}
