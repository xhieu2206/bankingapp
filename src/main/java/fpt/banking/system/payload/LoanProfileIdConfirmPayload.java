package fpt.banking.system.payload;

public class LoanProfileIdConfirmPayload {

	private long loanProfileId;

	public long getLoanProfileId() {
		return loanProfileId;
	}

	public void setLoanProfileId(long loanProfileId) {
		this.loanProfileId = loanProfileId;
	}

	public LoanProfileIdConfirmPayload(long loanProfileId) {
		this.loanProfileId = loanProfileId;
	}

	public LoanProfileIdConfirmPayload() {
	}
	
}
