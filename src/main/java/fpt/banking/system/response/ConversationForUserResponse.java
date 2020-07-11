package fpt.banking.system.response;

import java.util.Date;

public class ConversationForUserResponse {

	private long conversationId;
	private String title;
	private String lastMessage;
	private boolean isRead;
	private Date lastMessageAt;
	public ConversationForUserResponse() {
	}
	public ConversationForUserResponse(long conversationId, String title, String lastMessage, boolean isRead,
			Date lastMessageAt) {
		this.conversationId = conversationId;
		this.title = title;
		this.lastMessage = lastMessage;
		this.isRead = isRead;
		this.lastMessageAt = lastMessageAt;
	}
	public long getConversationId() {
		return conversationId;
	}
	public void setConversationId(long conversationId) {
		this.conversationId = conversationId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLastMessage() {
		return lastMessage;
	}
	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}
	public boolean isRead() {
		return isRead;
	}
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	public Date getLastMessageAt() {
		return lastMessageAt;
	}
	public void setLastMessageAt(Date lastMessageAt) {
		this.lastMessageAt = lastMessageAt;
	}
}
