package fpt.banking.system.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.dao.AccountDAO;
import fpt.banking.system.exception.AccountNotFound;
import fpt.banking.system.model.Account;
import fpt.banking.system.payload.AccountForAdminResponse;
import fpt.banking.system.service.AccountService;

@RestController
@RequestMapping("/api/admin")
public class AdminAccountController {
	
	@Autowired
	private AccountService accountService;

	@GetMapping("/users/{userId}/accounts")
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_TRANSACTIONMANAGER', 'ROLE_BRANCHMANAGER', 'ROLE_BANKMANAGER')")
	public List<AccountForAdminResponse> getAllAccounts(@PathVariable long userId) {
		List<AccountForAdminResponse> results = new ArrayList<AccountForAdminResponse>();
		List<Account> accounts = accountService.getAccounts(userId);
		if (accounts == null) {
			throw new AccountNotFound("This user doesn't have any useable account");
		}
		for (Account account: accounts) {
			AccountForAdminResponse result = new AccountForAdminResponse(account.getId(), account.getAccountNumber(), account.getAmount(), account.isStatus(), account.getExpiredAt(), account.getCreatedAt(), account.getUpdatedAt());
			results.add(result);
		}
		return results;
	}
	
	@GetMapping("/users/{userId}/useableAccounts")
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_TRANSACTIONMANAGER', 'ROLE_BRANCHMANAGER', 'ROLE_BANKMANAGER')")
	public List<AccountForAdminResponse> getAllUseableAccounts(@PathVariable long userId) {
		List<AccountForAdminResponse> results = new ArrayList<AccountForAdminResponse>();
		List<Account> accounts = accountService.getUseableAccounts(userId);
		System.out.println(accounts);
		if (accounts == null) {
			throw new AccountNotFound("This user doesn't have any useable account");
		}
		for (Account account: accounts) {
			AccountForAdminResponse result = new AccountForAdminResponse(account.getId(), account.getAccountNumber(), account.getAmount(), account.isStatus(), account.getExpiredAt(), account.getCreatedAt(), account.getUpdatedAt());
			results.add(result);
		}
		return results;
	}
}
