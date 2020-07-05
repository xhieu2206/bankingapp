package fpt.banking.system.payload;

public class RejectedLoanProfileRequestPayload {

	private long loanProfileId;
	private String rejectedReason;
	public RejectedLoanProfileRequestPayload() {
	}
	public RejectedLoanProfileRequestPayload(long loanProfileId, String rejectedReason) {
		this.loanProfileId = loanProfileId;
		this.rejectedReason = rejectedReason;
	}
	public long getLoanProfileId() {
		return loanProfileId;
	}
	public void setLoanProfileId(long loanProfileId) {
		this.loanProfileId = loanProfileId;
	}
	public String getRejectedReason() {
		return rejectedReason;
	}
	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}
	
}
