package fpt.banking.system.dao;

import java.util.List;

import fpt.banking.system.model.Transaction;

public interface TransactionDAO {

	public List<Transaction> getTransactions(int accountId, int page);
}
