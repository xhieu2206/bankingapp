package fpt.banking.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fpt.banking.system.dao.MessageDAO;
import fpt.banking.system.model.Conversation;
import fpt.banking.system.model.Message;
import fpt.banking.system.model.User;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageDAO messageDAO;

	@Override
	@Transactional
	public List<Message> getMessagesByConservation(long conversationId) {
		return messageDAO.getMessagesByConversation(conversationId);
	}

	@Override
	@Transactional
	public void saveMessage(Conversation conversation, User user, String messageDetail) {
		messageDAO.saveMessage(conversation, user, messageDetail);
	}
}
