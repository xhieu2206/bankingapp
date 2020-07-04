package fpt.banking.system.payload;

public class SearchByIdCardNumberRequest {

	private String idCardNumber;

	public SearchByIdCardNumberRequest() {
	}

	public SearchByIdCardNumberRequest(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}
	
}
