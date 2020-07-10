package fpt.banking.system.payload;

import fpt.banking.system.model.Cheque;

public class ChequeForAdmin {
	private String fullName;
	private String idCardNumber;
	private String withdrawEmployeeFullName;
	private String withdrawAt;
	private Cheque cheque;
	public ChequeForAdmin() {
	}
	public ChequeForAdmin(String fullName, String idCardNumber, Cheque cheque, String withdrawEmployeeFullName,
			String withdrawAt) {
		this.fullName = fullName;
		this.idCardNumber = idCardNumber;
		this.cheque = cheque;
		this.withdrawEmployeeFullName = withdrawEmployeeFullName;
		this.withdrawAt = withdrawAt;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getIdCardNumber() {
		return idCardNumber;
	}
	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}
	public Cheque getCheque() {
		return cheque;
	}
	public void setCheque(Cheque cheque) {
		this.cheque = cheque;
	}
	public String getWithdrawEmployeeFullName() {
		return withdrawEmployeeFullName;
	}
	public void setWithdrawEmployeeFullName(String withdrawEmployeeFullName) {
		this.withdrawEmployeeFullName = withdrawEmployeeFullName;
	}
	public String getWithdrawAt() {
		return withdrawAt;
	}
	public void setWithdrawAt(String withdrawAt) {
		this.withdrawAt = withdrawAt;
	}
	
}
