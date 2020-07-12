package fpt.banking.system.response;

public class ConversationForEmployee {

	private long id;
	private String questionerName;
	private String respondentName;
	private String title;
	private String message;
	public ConversationForEmployee() {
	}
	public ConversationForEmployee(long id, String questionerName, String respondentName, String title,
			String message) {
		this.id = id;
		this.questionerName = questionerName;
		this.respondentName = respondentName;
		this.title = title;
		this.message = message;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getQuestionerName() {
		return questionerName;
	}
	public void setQuestionerName(String questionerName) {
		this.questionerName = questionerName;
	}
	public String getRespondentName() {
		return respondentName;
	}
	public void setRespondentName(String respondentName) {
		this.respondentName = respondentName;
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
