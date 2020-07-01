package fpt.banking.system.exception;

public class ErrorResponseWithLoggedInFailed {
	private int status;
	private String message;
	private long timeStamp;
	private int LoggedInFailedTime;
	
	public ErrorResponseWithLoggedInFailed() { }

	public ErrorResponseWithLoggedInFailed(int status, String message, long timeStamp, int loggedInFailedTime) {
		this.status = status;
		this.message = message;
		this.timeStamp = timeStamp;
		LoggedInFailedTime = loggedInFailedTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getLoggedInFailedTime() {
		return LoggedInFailedTime;
	}

	public void setLoggedInFailedTime(int loggedInFailedTime) {
		LoggedInFailedTime = loggedInFailedTime;
	}
	
	
}
