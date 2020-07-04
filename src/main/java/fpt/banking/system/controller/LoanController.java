package fpt.banking.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.exception.NotFoundException;
import fpt.banking.system.model.LoanProfile;
import fpt.banking.system.model.User;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.UserService;

@RestController
@RequestMapping("/api/users")
public class LoanController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/current/loanProfiles")
	@PreAuthorize("hasRole('ROLE_USER')")
	public List<LoanProfile> getAll(@AuthenticationPrincipal UserPrincipal currentUser) {
		User user = userService.getUser(currentUser.getId());
		if (user.getLoanProfiles().size() == 0) {
			throw new NotFoundException("You don't have any loan profile");
		}
		return user.getLoanProfiles();
	}
}
