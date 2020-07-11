package fpt.banking.system.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fpt.banking.system.dao.ConversationDAO;
import fpt.banking.system.dao.MessageDAO;
import fpt.banking.system.model.Conversation;
import fpt.banking.system.response.ConversationForUserResponse;

@Service
public class ConversationServiceImpl implements ConversationService {

	@Autowired
	private ConversationDAO conversationDAO;

	@Autowired
	private MessageDAO messageDAO;

	@Override
	@Transactional
	public List<ConversationForUserResponse> getConversationsForUser(long userId) {
		List<ConversationForUserResponse> results = new ArrayList<ConversationForUserResponse>();
		List<Conversation> conversations = conversationDAO.getConversationsForUser(userId);
		for (Conversation conversation : conversations) {
			ConversationForUserResponse conversationForUserResponse = new ConversationForUserResponse();
			conversationForUserResponse.setConversationId(conversation.getId());
			conversationForUserResponse.setTitle(conversation.getTitle());
			conversationForUserResponse.setRead(conversation.isReadFromQuestioner());
			conversationForUserResponse.setLastMessage(messageDAO.getLatestMessageOfAConversation(conversation.getId()).getMessageDetail());
			System.out.println(conversationForUserResponse.getLastMessage());
			conversationForUserResponse.setLastMessageAt(messageDAO.getLatestMessageOfAConversation(conversation.getId()).getCreatedAt());
			results.add(conversationForUserResponse);
		}
		return results;
	}

}