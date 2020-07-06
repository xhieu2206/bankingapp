package fpt.banking.system.payload;

public class DepositChequeRequestPayload {

	private String tranferFullName;
	private String recieverIdCardNumber;
	private String recieverFullName;
	public DepositChequeRequestPayload() {
	}
	public DepositChequeRequestPayload(String tranferFullName, String recieverIdCardNumber, String recieverFullName) {
		this.tranferFullName = tranferFullName;
		this.recieverIdCardNumber = recieverIdCardNumber;
		this.recieverFullName = recieverFullName;
	}
	public String getTranferFullName() {
		return tranferFullName;
	}
	public void setTranferFullName(String tranferFullName) {
		this.tranferFullName = tranferFullName;
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
