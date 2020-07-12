package fpt.banking.system.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.model.User;
import fpt.banking.system.response.SuccessfulResponse;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.ConversationService;
import fpt.banking.system.service.MessageService;
import fpt.banking.system.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminConversationController {
	@Autowired
	private ConversationService conversationService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private UserService userService;

	@GetMapping("/current/total-unread")
	@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> getTotalUnreadConversation(
			@AuthenticationPrincipal UserPrincipal currentEmployee) {
		User employee = userService.getUser(currentEmployee.getId());
		long totalUnread = conversationService.getTotalUnreadConversationFromEmployee(employee.getId());
		SuccessfulResponse res = new SuccessfulResponse(
				HttpStatus.OK.value(),
				String.valueOf(totalUnread),
				System.currentTimeMillis());
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
}
