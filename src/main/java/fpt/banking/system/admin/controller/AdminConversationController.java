package fpt.banking.system.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//	@GetMapping
}
