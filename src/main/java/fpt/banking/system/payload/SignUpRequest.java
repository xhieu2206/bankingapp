package fpt.banking.system.payload;

import javax.validation.constraints.*;

public class SignUpRequest {
	@NotBlank
    @Size(min = 3, max = 255)
    private String username;
	
	@NotBlank
    @Size(min = 6, max = 255)
    private String password;
	
	@NotBlank
    @Size(min = 1, max = 255)
    private String fullname;
	
	@NotBlank
    @Size(min = 4, max = 255)
	@Email
    private String email;
	
    @Size(max = 500)
    private String address;
    
    @Size(max = 50)
    private String phone;

	public SignUpRequest(@NotBlank @Size(min = 3, max = 255) String username,
			@NotBlank @Size(min = 6, max = 255) String password, @NotBlank @Size(min = 1, max = 255) String fullname,
			@NotBlank @Size(min = 4, max = 255) @Email String email, @Size(max = 500) String address,
			@Size(max = 50) String phone) {
		super();
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.email = email;
		this.address = address;
		this.phone = phone;
	}

	public SignUpRequest() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
