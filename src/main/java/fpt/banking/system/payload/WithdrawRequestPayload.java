package fpt.banking.system.payload;

public class WithdrawRequestPayload {
	private long userId;
	private long accountId;
	private long amount;
	public WithdrawRequestPayload() {
	}
	public WithdrawRequestPayload(long userId, long accountId, long amount) {
		this.userId = userId;
		this.accountId = accountId;
		this.amount = amount;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	
}
