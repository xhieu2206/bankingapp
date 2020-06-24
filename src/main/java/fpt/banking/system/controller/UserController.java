package fpt.banking.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.model.Membership;
import fpt.banking.system.model.User;
import fpt.banking.system.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/test")
	@PreAuthorize("hasRole('ROLE_USER')")
    public String getMessage() {
		System.out.println("SOMETHING");
        return "Hello from Admin API controller";
    }
	
	@GetMapping("/{userId}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public User getUser(@PathVariable int userId) {
		User user = userService.getUser(userId);
		user.setMembership(new Membership(Long.valueOf(1), "GOLD"));
		return user;
	}
}
