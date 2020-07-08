package fpt.banking.system.payload;

public class DepositChequeRequestPayload {

	private String recieverFullName;
	private String recieverIdCardNumber;
	public DepositChequeRequestPayload() {
	}
	public DepositChequeRequestPayload(String recieverFullName, String recieverIdCardNumber) {
		this.recieverFullName = recieverFullName;
		this.recieverIdCardNumber = recieverIdCardNumber;
	}
	public String getRecieverFullName() {
		return recieverFullName;
	}
	public void setRecieverFullName(String recieverFullName) {
		this.recieverFullName = recieverFullName;
	}
	public String getRecieverIdCardNumber() {
		return recieverIdCardNumber;
	}
	public void setRecieverIdCardNumber(String recieverIdCardNumber) {
		this.recieverIdCardNumber = recieverIdCardNumber;
	}
}
