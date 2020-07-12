package fpt.banking.system.service;

import java.util.List;

import fpt.banking.system.model.Conversation;
import fpt.banking.system.model.User;
import fpt.banking.system.response.ConversationForUserResponse;

public interface ConversationService {

	public List<ConversationForUserResponse> getConversationsForUser(long userId);

	public Conversation findConversationById(long conversationId);

	public void setReadConservationFromUser(long conversationId);

	public void setUnreadConservationFromUser(long conversationId);

	public void setReadConservationFromEmployee(long conversationId);

	public void setUnreadConservationFromEmployee(long conversationId);

	public long saveConversation(String title, User questioner, String message);

	public long getTotalUnreadConversationFromUser(long userId);

	public long getTotalUnreadConversationFromEmployee(long employeeId);
}
