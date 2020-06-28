package fpt.banking.system.service;

public interface TranferService {

	public void tranferInternalByAccountNumber(int tranferAccountId, int receiveAccountId,
			long amount, String description);
}
