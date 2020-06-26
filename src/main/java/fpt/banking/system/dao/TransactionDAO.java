package fpt.banking.system.dao;

import java.util.List;

import fpt.banking.system.model.Transaction;
import fpt.banking.system.payload.TransactionsResponse;

public interface TransactionDAO {

	public TransactionsResponse getTransactions(int accountId, int page);
}
