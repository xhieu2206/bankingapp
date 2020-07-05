package fpt.banking.system.payload;

public class ConfirmLoanProfilePayload {

	private long loanProfileId;
	private String otpCode;
	public ConfirmLoanProfilePayload(long loanProfileId, String otpCode) {
		this.loanProfileId = loanProfileId;
		this.otpCode = otpCode;
	}
	public ConfirmLoanProfilePayload() {
	}
	public long getLoanProfileId() {
		return loanProfileId;
	}
	public void setLoanProfileId(long loanProfileId) {
		this.loanProfileId = loanProfileId;
	}
	public String getOtpCode() {
		return otpCode;
	}
	public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
	}
}
