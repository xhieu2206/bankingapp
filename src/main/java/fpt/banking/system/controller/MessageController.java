package fpt.banking.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.exception.AuthorizedException;
import fpt.banking.system.exception.ErrorResponse;
import fpt.banking.system.exception.NotFoundException;
import fpt.banking.system.model.Conversation;
import fpt.banking.system.model.Message;
import fpt.banking.system.model.User;
import fpt.banking.system.payload.MessageRequestPayload;
import fpt.banking.system.response.SuccessfulResponse;
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

	@PostMapping("/current/conversations/{conversationId}/messages")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> replyMessage(
			@AuthenticationPrincipal UserPrincipal currentUser,
			@PathVariable long conversationId,
			@RequestBody MessageRequestPayload payload) {
		Conversation conversation = conversationService.findConversationById(conversationId);
		User user = userService.getUser(currentUser.getId());
		if (conversation == null) {
			ErrorResponse res = new ErrorResponse(
					HttpStatus.NOT_FOUND.value(),
					"Conversation Not Found",
					System.currentTimeMillis());
			return new ResponseEntity<ErrorResponse>(res, HttpStatus.OK);
		}
		if (conversation.getQuestioner().getId() != user.getId()) {
			ErrorResponse res = new ErrorResponse(
					HttpStatus.UNAUTHORIZED.value(),
					"You don't have permission to access this resource",
					System.currentTimeMillis());
			return new ResponseEntity<ErrorResponse>(res, HttpStatus.UNAUTHORIZED);
		}
		if (payload.getMessage().trim().length() == 0) {
			ErrorResponse res = new ErrorResponse(
					HttpStatus.NOT_ACCEPTABLE.value(),
					"Empty message",
					System.currentTimeMillis());
			return new ResponseEntity<ErrorResponse>(res, HttpStatus.NOT_ACCEPTABLE);
		}
		messageService.saveMessage(conversation, user, payload.getMessage());
		SuccessfulResponse res = new SuccessfulResponse(
				HttpStatus.OK.value(),
				"Reply successfully",
				System.currentTimeMillis());
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
}
