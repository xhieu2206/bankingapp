package fpt.banking.system.payload;

public class DepositChequeRequestPayload {

	private String transferFullName;
	private String recieverIdCardNumber;
	private String recieverFullName;
	public DepositChequeRequestPayload() {
	}
	public DepositChequeRequestPayload(String transferFullName, String recieverIdCardNumber, String recieverFullName) {
		this.transferFullName = transferFullName;
		this.recieverIdCardNumber = recieverIdCardNumber;
		this.recieverFullName = recieverFullName;
	}
	public String getTransferFullName() {
		return transferFullName;
	}
	public void setTransferFullName(String transferFullName) {
		this.transferFullName = transferFullName;
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
