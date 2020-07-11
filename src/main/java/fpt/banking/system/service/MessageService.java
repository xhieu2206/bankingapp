package fpt.banking.system.service;

import java.util.List;

import fpt.banking.system.model.Conversation;
import fpt.banking.system.model.Message;
import fpt.banking.system.model.User;

public interface MessageService {

	public List<Message> getMessagesByConservation(long conversationId);

	public void saveMessage(Conversation conversation, User user, String messageDetail);
}
