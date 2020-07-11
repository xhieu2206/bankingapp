package fpt.banking.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.exception.AuthorizedException;
import fpt.banking.system.exception.NotFoundException;
import fpt.banking.system.model.Conversation;
import fpt.banking.system.model.Message;
import fpt.banking.system.model.User;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.ConversationService;
import fpt.banking.system.service.MessageService;
import fpt.banking.system.service.UserService;

@RestController
@RequestMapping("/api/users")
public class MessageController {

	@Autowired
	private UserService userService;

	@Autowired
	private ConversationService conversationService;

	@Autowired
	private MessageService messageService;

	@GetMapping("/current/conversations/{conversationId}/messages")
	@PreAuthorize("hasRole('ROLE_USER')")
	public List<Message> readConversation(
			@AuthenticationPrincipal UserPrincipal currentUser,
			@PathVariable long conversationId) {
		Conversation conversation = conversationService.findConversationById(conversationId);
		User user = userService.getUser(currentUser.getId());
		if (conversation == null) {
			throw new NotFoundException("This conversation doesn't existed");
		}
		if (conversation.getQuestioner().getId() != user.getId()) {
			throw new AuthorizedException("You don't have permission to access this resource");
		}
		conversationService.setReadConservationFromUser(conversationId);
		return messageService.getMessagesByConservation(conversationId);
	}
}
