package fpt.banking.system.payload;

public class TranferInternalPayloadByAccountNumber {

	private String accountNumber;
	private Long amount;
	private String fullName;
	private String pin;
	private String description;

	public TranferInternalPayloadByAccountNumber(String accountNumber, Long amount, String fullName, String pin, String description) {
		this.accountNumber = accountNumber;
		this.amount = amount;
		this.fullName = fullName;
		this.pin = pin;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	@Override
	public String toString() {
		return "TranferInternalPayloadByAccountNumber [accountNumber=" + accountNumber + ", amount=" + amount
				+ ", fullName=" + fullName + ", pin=" + pin + ", description=" + description + "]";
	}
}
