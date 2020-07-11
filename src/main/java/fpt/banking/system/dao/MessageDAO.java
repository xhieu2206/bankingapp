package fpt.banking.system.dao;

import java.util.List;

import fpt.banking.system.model.Conversation;
import fpt.banking.system.model.Message;
import fpt.banking.system.model.User;

public interface MessageDAO {

	public Message getLatestMessageOfAConversation(long conversationId);

	public Message getMessageById(long messageId);

	public List<Message> getMessagesByConversation(long conversationId);

	public void saveMessage(Conversation conversation, User user, String messageDetail);
}
