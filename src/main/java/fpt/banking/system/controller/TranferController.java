package fpt.banking.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.model.User;
import fpt.banking.system.payload.FindUserForTranferPayload;
import fpt.banking.system.service.UserService;

@RestController
@RequestMapping("/api/users")
public class TranferController {
	
	@Autowired
	private UserService userService; 

	@PostMapping("/find")
	@PreAuthorize("hasRole('ROLE_USER')")
	public User find(@RequestBody FindUserForTranferPayload
			findUserForTranferPayload) {
		return userService.findUser(findUserForTranferPayload.getTerm(),
				findUserForTranferPayload.getType());
	}
	
}
