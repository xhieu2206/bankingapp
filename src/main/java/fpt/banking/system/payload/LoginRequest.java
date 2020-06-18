package fpt.banking.system.payload;

import javax.validation.constraints.*;

public class LoginRequest {
	@NotBlank
	@Size(max = 255)
	private String usernameOrEmail;
	
	@NotBlank
	@Size(max = 255)
	private String password;
	
	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}
	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
