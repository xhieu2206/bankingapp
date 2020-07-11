package fpt.banking.system.payload;

public class ConversationRequestPayload {

	private String title;
	private String message;
	public ConversationRequestPayload(String title, String message) {
		this.title = title;
		this.message = message;
	}
	public ConversationRequestPayload() {
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
