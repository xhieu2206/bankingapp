package fpt.banking.system.payload;

public class SearchByPhoneNumberRequest {
	private String phone;

	public SearchByPhoneNumberRequest() {
	}

	public SearchByPhoneNumberRequest(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
