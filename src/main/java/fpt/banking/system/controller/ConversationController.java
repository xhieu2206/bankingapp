package fpt.banking.system.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.response.ConversationForUserResponse;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.ConversationService;

@RestController
@RequestMapping("/api/users")
public class ConversationController {

	@Autowired
	private ConversationService conversationService;

	@GetMapping("/current/conversations")
	@PreAuthorize("hasRole('ROLE_USER')")
	public List<ConversationForUserResponse> getConversationsForUser(
			@AuthenticationPrincipal UserPrincipal currentUser) {
		return conversationService.getConversationsForUser(currentUser.getId());
	}
}
