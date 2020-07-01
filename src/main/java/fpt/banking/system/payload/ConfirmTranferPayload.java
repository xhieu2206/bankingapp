package fpt.banking.system.payload;

public class ConfirmTranferPayload {

	private long transactionQueueId;
	private String otpCode;

	public ConfirmTranferPayload(long transactionQueueId, String otpCode) {
		this.transactionQueueId = transactionQueueId;
		this.otpCode = otpCode;
	}

	public long getTransactionQueueId() {
		return transactionQueueId;
	}

	public void setTransactionQueueid(long transactionQueueId) {
		this.transactionQueueId = transactionQueueId;
	}

	public String getOtpCode() {
		return otpCode;
	}

	public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
	}
}
