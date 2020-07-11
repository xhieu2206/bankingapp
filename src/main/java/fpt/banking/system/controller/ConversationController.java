package fpt.banking.system.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.exception.ErrorResponse;
import fpt.banking.system.model.User;
import fpt.banking.system.payload.ConversationRequestPayload;
import fpt.banking.system.response.ConversationForUserResponse;
import fpt.banking.system.response.SuccessfulResponse;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.ConversationService;
import fpt.banking.system.service.MessageService;
import fpt.banking.system.service.UserService;

@RestController
@RequestMapping("/api/users")
public class ConversationController {

	@Autowired
	private ConversationService conversationService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private UserService userService;

	@GetMapping("/current/conversations")
	@PreAuthorize("hasRole('ROLE_USER')")
	public List<ConversationForUserResponse> getConversationsForUser(
			@AuthenticationPrincipal UserPrincipal currentUser) {
		return conversationService.getConversationsForUser(currentUser.getId());
	}

	@PostMapping("/current/conversations")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> createConversation(
			@AuthenticationPrincipal UserPrincipal currentUser,
			@RequestBody ConversationRequestPayload payload) {
		User user = userService.getUser(currentUser.getId());

		if (payload.getTitle().trim().equals("") || payload.getMessage().trim().equals("")) {
			ErrorResponse res = new ErrorResponse(
					HttpStatus.NOT_ACCEPTABLE.value(),
					"Cannot leave any field empty",
					System.currentTimeMillis());
			return new ResponseEntity<ErrorResponse>(res, HttpStatus.OK);
		}
		long conversationId = conversationService.saveConversation(
				payload.getTitle(),
				user,
				payload.getMessage());

		SuccessfulResponse res = new SuccessfulResponse(
				HttpStatus.OK.value(),
				String.valueOf(conversationId),
				System.currentTimeMillis());
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
}
