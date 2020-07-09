package fpt.banking.system.payload;

public class LoanProfileFromUserRequestPayload {
	private long amount;
	private String description;
	private long loanInterestRateId;
	private long accountId;
	private long transasctionOfficeId;
	public LoanProfileFromUserRequestPayload() {
	}
	public LoanProfileFromUserRequestPayload(long amount, String description, long loanInterestRateId, long accountId,
			long transasctionOfficeId) {
		this.amount = amount;
		this.description = description;
		this.loanInterestRateId = loanInterestRateId;
		this.accountId = accountId;
		this.transasctionOfficeId = transasctionOfficeId;
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
	public long getTransasctionOfficeId() {
		return transasctionOfficeId;
	}
	public void setTransasctionOfficeId(long transasctionOfficeId) {
		this.transasctionOfficeId = transasctionOfficeId;
	}
	
}
