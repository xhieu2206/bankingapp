package fpt.banking.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.model.User;
import fpt.banking.system.service.UserService;

@RestController
@RequestMapping("/api/private")
public class PrivateController {
	@Autowired
	private UserService userService;
	
	@GetMapping
	public String getMessage() {
		return "Hello from private API controller";
	}
	
	@GetMapping("/test")
	@PreAuthorize("hasRole('ROLE_USER')")
	public User test() {
//		return userService.testFindUser("ABC");
		return null;
	}
}
