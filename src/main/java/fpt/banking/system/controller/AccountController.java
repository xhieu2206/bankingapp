package fpt.banking.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.model.Account;
import fpt.banking.system.service.AccountService;

@RestController
@RequestMapping("/api/users")
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@GetMapping("/{userId}/accounts")
	@PreAuthorize("hasRole('ROLE_USER')")
	public List<Account> getAccounts(@PathVariable int userId) {
		System.out.println(userId);
		return accountService.getAccounts(userId);
	}
}