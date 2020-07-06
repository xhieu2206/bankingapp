package fpt.banking.system.payload;

public class RegisterUserRequestPayload {

	private String username;
	private String email;
	private String fullName;
	private String birthday;
	private String address;
	private String gender;
	private String idCardNumber;
	private String phone;
	private String image;
	private long membershipId;
	public RegisterUserRequestPayload(String username, String email, String fullName, String birthday, String address,
			String gender, String idCardNumber, String phone, String image, long membershipId) {
		this.username = username;
		this.email = email;
		this.fullName = fullName;
		this.birthday = birthday;
		this.address = address;
		this.gender = gender;
		this.idCardNumber = idCardNumber;
		this.phone = phone;
		this.image = image;
		this.membershipId = membershipId;
	}
	public RegisterUserRequestPayload() {
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getIdCardNumber() {
		return idCardNumber;
	}
	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public long getMembershipId() {
		return membershipId;
	}
	public void setMembershipId(long membershipId) {
		this.membershipId = membershipId;
	}
	
}
