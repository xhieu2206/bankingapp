package fpt.banking.system.dao;

import java.util.List;

import fpt.banking.system.model.Conversation;
import fpt.banking.system.model.User;

public interface ConversationDAO {

	public List<Conversation> getConversationsForUser(long userId);

	public List<Conversation> getConversationsForEmployee(long employeeId);

	public Conversation findConversationById(long conversationId);

	public void setReadConversationFromUser(long conversationId);

	public void setUnreadConversationFromUser(long conversationId);

	public void setReadConversationFromEmployee(long conversationId);

	public void setUnreadConversationFromEmployee(long conversationId);

	public long saveConversation(String title, User questioner);

	public long getTotalUnreadConversationFromUser(long userId);

	public long getTotalUnreadConversationFromEmployee(long employeeId);
}
