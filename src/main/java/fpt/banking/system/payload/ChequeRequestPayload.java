package fpt.banking.system.payload;

public class ChequeRequestPayload {

	private String recieverFullname;
	private String recieverIdCardNumber;
	private long transactionAmount;
	
	public ChequeRequestPayload() {
	}

	public ChequeRequestPayload(String recieverFullname, String recieverIdCardNumber, long transactionAmount) {
		this.recieverFullname = recieverFullname;
		this.recieverIdCardNumber = recieverIdCardNumber;
		this.transactionAmount = transactionAmount;
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
