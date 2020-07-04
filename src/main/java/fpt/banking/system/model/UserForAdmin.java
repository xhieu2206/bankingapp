package fpt.banking.system.model;

import java.util.Date;

public class UserForAdmin {
	private Long id;
	
	private String fullname;
	
	private Date birthday;
	
	private String address;
	
	private String gender;
	
	private String image;
	
	private String idCardNumber;
	
	private String phone;
	
	private boolean locked;

	public UserForAdmin() {
	}

	public UserForAdmin(Long id, String fullname, Date birthday, String address, String gender, String image,
			String idCardNumber, String phone, boolean locked) {
		this.id = id;
		this.fullname = fullname;
		this.birthday = birthday;
		this.address = address;
		this.gender = gender;
		this.image = image;
		this.idCardNumber = idCardNumber;
		this.phone = phone;
		this.locked = locked;
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
}
