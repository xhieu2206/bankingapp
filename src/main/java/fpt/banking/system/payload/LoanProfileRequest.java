package fpt.banking.system.payload;

public class LoanProfileRequest {

	private long amount;
	private String description;
	private long loanInterestRateId;
	private long accountId;


	public LoanProfileRequest() {
	}

	public LoanProfileRequest(long amount, String description, long loanInterestRateId, long accountId) {
		this.amount = amount;
		this.description = description;
		this.loanInterestRateId = loanInterestRateId;
		this.accountId = accountId;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getLoanInterestRateId() {
		return loanInterestRateId;
	}

	public void setLoanInterestRateId(long loanInterestRateId) {
		this.loanInterestRateId = loanInterestRateId;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
}
