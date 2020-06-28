package fpt.banking.system.payload;

public class TranferInternalPayloadByCardNumber {

	private String cardNumber;
	private Long amount;
	private String fullName;
	private String pin;
	private String description;

	public TranferInternalPayloadByCardNumber(String cardNumber, Long amount, String fullName, String pin, String description) {
		this.cardNumber = cardNumber;
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

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
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
		return "TranferInternalPayloadByCardNumber [cardNumber=" + cardNumber + ", amount=" + amount
				+ ", fullName=" + fullName + ", pin=" + pin + ", description=" + description + "]";
	}
}
