package fpt.banking.system.dao;

import java.util.List;

import fpt.banking.system.model.Transaction;
import fpt.banking.system.payload.TransactionsResponse;

public interface TransactionDAO {

	public List<Transaction> getTransactions(int accountId, int page);
	
	public int getTotalTransactions(int accountId);
}
