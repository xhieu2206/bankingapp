package fpt.banking.system.admin.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.model.User;
import fpt.banking.system.response.ConversationForEmployeeResponse;
import fpt.banking.system.response.ConversationsResponse;
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

	@GetMapping("/current/employee/conversations")
	@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	public List<ConversationForEmployeeResponse> getConversationsForEmployee(
			@AuthenticationPrincipal UserPrincipal currentEmployee) {
		User employee = userService.getUser(currentEmployee.getId());
		return conversationService.getConversationsForEmployee(employee.getId());
	}

	@GetMapping("/no-response-conversations")
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_TRANSACTIONMANAGER', 'ROLE_BRANCHMANAGER', 'ROLE_BANKMANAGER')")
	public ConversationsResponse getNoResponseConversations(
			@RequestParam("page") Optional<Long> page,
			@AuthenticationPrincipal UserPrincipal currentAdmin) {
		long pageNumber = 1;
		if (page.isPresent()) {
			pageNumber = page.get();
		}
		return conversationService.getNoResponseConversations(pageNumber);
	}

	@GetMapping("/responsed-conversations")
	@PreAuthorize("hasAnyRole('ROLE_TRANSACTIONMANAGER', 'ROLE_BRANCHMANAGER', 'ROLE_BANKMANAGER')")
	public ConversationsResponse getResponsedConversations(
			@RequestParam("page") Optional<Long> page,
			@AuthenticationPrincipal UserPrincipal currentAdmin) {
		long pageNumber = 1;
		if (page.isPresent()) {
			pageNumber = page.get();
		}
		return conversationService.getResponsedConversations(pageNumber);
	}

}
