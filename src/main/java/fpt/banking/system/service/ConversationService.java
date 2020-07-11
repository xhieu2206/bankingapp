package fpt.banking.system.service;

import java.util.List;

import fpt.banking.system.response.ConversationForUserResponse;

public interface ConversationService {

	public List<ConversationForUserResponse> getConversationsForUser(long userId);
}
