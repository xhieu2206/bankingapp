package fpt.banking.system.service;

public interface TranferService {

	public void tranferInternalByAccountNumber(long tranferAccountId, long receiveAccountId,
			long amount, String description);
}
