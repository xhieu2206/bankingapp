package fpt.banking.system.payload;

public class PasswordRequestPayload {

	private String password;
	private String passwordConfirm;
	public PasswordRequestPayload(String password, String passwordConfirm) {
		this.password = password;
		this.passwordConfirm = passwordConfirm;
	}
	public PasswordRequestPayload() {
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	
}
