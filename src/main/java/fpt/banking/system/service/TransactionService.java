package fpt.banking.system.service;

import java.util.List;

import fpt.banking.system.model.Transaction;
import fpt.banking.system.payload.TransactionsResponse;

public interface TransactionService {

	public TransactionsResponse getTransactions(int accountId, int page);
}
