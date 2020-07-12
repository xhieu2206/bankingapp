package fpt.banking.system.payload;

public class MessageRequestPayload {

	private String message;

	public MessageRequestPayload() {
	}

	public MessageRequestPayload(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
