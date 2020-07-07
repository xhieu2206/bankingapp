package fpt.banking.system.dao;

import fpt.banking.system.model.TransactionQueueInternal;

public interface TransactionQueueInternalDAO {

	public TransactionQueueInternal findById(long id);
	
	public String saveTransactionQueueInternal(String otpCode, long transferAccountId, long receiverAccountId, long amount, java.util.Date expriedAt, String description);
	
	public void deleteTransactionQueueInternal(long transactionQueueInternalId);
}
