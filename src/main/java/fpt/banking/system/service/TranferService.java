package fpt.banking.system.service;

import fpt.banking.system.model.TransactionQueueInternal;

public interface TranferService {

	public void tranferInternal(long tranferAccountId, long receiveAccountId,
			long amount, String description);
	
	public String saveTransactionQueueInternal(long tranferAccountId, long receiverAccountId, long amount, String description);
	
	public void deleteTransactionQueueInternal(long transactionQueueInternalId);
	
	public TransactionQueueInternal findTransactionQueueById(long id);
}
