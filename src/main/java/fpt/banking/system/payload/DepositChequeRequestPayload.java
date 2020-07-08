package fpt.banking.system.payload;

public class DepositChequeRequestPayload {

	private String recieverIdCardNumber;
	private String recieverFullName;
	public DepositChequeRequestPayload() {
	}
	public DepositChequeRequestPayload(String recieverIdCardNumber, String recieverFullName) {
		this.recieverIdCardNumber = recieverIdCardNumber;
		this.recieverFullName = recieverFullName;
	}
	public String getRecieverIdCardNumber() {
		return recieverIdCardNumber;
	}
	public void setRecieverIdCardNumber(String recieverIdCardNumber) {
		this.recieverIdCardNumber = recieverIdCardNumber;
	}
	public String getRecieverFullName() {
		return recieverFullName;
	}
	public void setRecieverFullName(String recieverFullName) {
		this.recieverFullName = recieverFullName;
	}
}
