package fpt.banking.system.payload;

public class AccountIdRequestPayload {

	private long accountId;

	public AccountIdRequestPayload() {
	}

	public AccountIdRequestPayload(long accountId) {
		this.accountId = accountId;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	
}
