package fpt.banking.system.service;

import fpt.banking.system.model.TransactionQueueInternal;

public interface TransferService {

	public void transferInternal(long transferAccountId, long receiveAccountId,
			long amount, String description);
	
	public String saveTransactionQueueInternal(long transferAccountId, long receiverAccountId, long amount, String description);
	
	public void deleteTransactionQueueInternal(long transactionQueueInternalId);
	
	public TransactionQueueInternal findTransactionQueueById(long id);
}
