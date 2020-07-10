package fpt.banking.system.payload;

import fpt.banking.system.model.Cheque;

public class ChequeForUserResponse {
	private String withdrawEmployeeFullName;
	private String withdrawAt;
	private Cheque cheque;
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
	public Cheque getCheque() {
		return cheque;
	}
	public void setCheque(Cheque cheque) {
		this.cheque = cheque;
	}
	public ChequeForUserResponse(String withdrawEmployeeFullName, String withdrawAt, Cheque cheque) {
		this.withdrawEmployeeFullName = withdrawEmployeeFullName;
		this.withdrawAt = withdrawAt;
		this.cheque = cheque;
	}
	public ChequeForUserResponse() {
	}
}
