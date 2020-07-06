package fpt.banking.system.payload;

public class UserIdRequestPayload {

	private long userId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public UserIdRequestPayload(long userId) {
		this.userId = userId;
	}

	public UserIdRequestPayload() {
	}
	
}
