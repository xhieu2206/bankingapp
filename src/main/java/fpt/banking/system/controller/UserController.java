package fpt.banking.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.model.User;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/test")
	@PreAuthorize("hasRole('ROLE_USER')")
    public String getMessage() {
        return "Hello from USER API controller";
    }
	
	@GetMapping("/current")
	@PreAuthorize("hasRole('ROLE_USER')")
	public UserPrincipal getUser(@AuthenticationPrincipal UserPrincipal user) {
		return user;
	}
}
