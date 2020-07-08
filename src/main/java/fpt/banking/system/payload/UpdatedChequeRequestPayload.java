package fpt.banking.system.payload;

public class UpdatedChequeRequestPayload {

	private long chequeId;
	private String recieverFullname;
	private String recieverIdCardNumber;
	private long transactionAmount;
	public UpdatedChequeRequestPayload() {
	}
	public UpdatedChequeRequestPayload(long chequeId, String recieverFullname, String recieverIdCardNumber,
			long transactionAmount) {
		this.chequeId = chequeId;
		this.recieverFullname = recieverFullname;
		this.recieverIdCardNumber = recieverIdCardNumber;
		this.transactionAmount = transactionAmount;
	}
	public long getChequeId() {
		return chequeId;
	}
	public void setChequeId(long chequeId) {
		this.chequeId = chequeId;
	}
	public String getRecieverFullname() {
		return recieverFullname;
	}
	public void setRecieverFullname(String recieverFullname) {
		this.recieverFullname = recieverFullname;
	}
	public String getRecieverIdCardNumber() {
		return recieverIdCardNumber;
	}
	public void setRecieverIdCardNumber(String recieverIdCardNumber) {
		this.recieverIdCardNumber = recieverIdCardNumber;
	}
	public long getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(long transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
}
