package fpt.banking.system.dao;

import fpt.banking.system.model.Message;

public interface MessageDAO {

	public Message getLatestMessageOfAConversation(long conversationId);
}
