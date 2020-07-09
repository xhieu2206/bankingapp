package fpt.banking.system.model;

import java.util.Date;

public class UserForAdmin {
	private Long id;
	
	private String email;
	
	private String username;
	
	private String fullname;
	
	private Date birthday;
	
	private String address;
	
	private String gender;
	
	private String image;
	
	private String idCardNumber;
	
	private String phone;
	
	private boolean locked;
	
	private Membership membership;

	public UserForAdmin() {
	}

	public UserForAdmin(Long id, String email, String username, String fullname, Date birthday, String address,
			String gender, String image, String idCardNumber, String phone, boolean locked, Membership membership) {
		this.id = id;
		this.email = email;
		this.username = username;
		this.fullname = fullname;
		this.birthday = birthday;
		this.address = address;
		this.gender = gender;
		this.image = image;
		this.idCardNumber = idCardNumber;
		this.phone = phone;
		this.locked = locked;
		this.membership = membership;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Membership getMembership() {
		return membership;
	}

	public void setMembership(Membership membership) {
		this.membership = membership;
	}
	
	
}
