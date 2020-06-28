package fpt.banking.system.service;

public interface TranferService {

	public void tranferInternal(long tranferAccountId, long receiveAccountId,
			long amount, String description);
}
