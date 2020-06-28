package fpt.banking.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.model.User;
import fpt.banking.system.payload.FindUserForTranferPayload;
import fpt.banking.system.payload.TranferInternalPayloadByAccountNumber;
import fpt.banking.system.service.UserService;

@RestController
@RequestMapping("/api/users")
public class TranferController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/{userId}/accounts/{accountId}tranferinternal/account")
	@PreAuthorize("hasRole('ROLE_USER')")
	public String tranferInternalByAccountNumber(@PathVariable int userId,
			@PathVariable int accountId, @RequestBody TranferInternalPayloadByAccountNumber
			tranferInternalPayloadByAccountNumber) {
		
		return "";
	}
}
