package fpt.banking.system.service;

import java.util.List;

import fpt.banking.system.model.Transaction;

public interface TransactionService {

	public List<Transaction> getTransactions(int accountId, int page);
}
