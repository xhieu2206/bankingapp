package fpt.banking.system.dao;

import java.util.List;

import fpt.banking.system.model.Conversation;

public interface ConversationDAO {

	public List<Conversation> getConversationsForUser(long userId);
}
