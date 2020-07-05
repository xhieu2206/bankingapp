package fpt.banking.system.admin.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.exception.AuthorizedException;
import fpt.banking.system.payload.TransactionsResponse;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.AccountService;
import fpt.banking.system.service.TransactionService;

@RestController
@RequestMapping("/api/admin")
public class AdminUserTransactionController {

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private AccountService accountService;

	@GetMapping("/users/{userId}/accounts/{accountId}/transactions")
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_TRANSACTIONMANAGER', 'ROLE_BRANCHMANAGER', 'ROLE_BANKMANAGER')")
	public TransactionsResponse getTransasctionsOfAnAccount(
			@RequestParam("page") Optional<Integer> page,
			@PathVariable int accountId,
			@PathVariable long userId,
			@AuthenticationPrincipal UserPrincipal user
			) {
		if (accountService.getAccount(accountId).getUser().getId() != userId) {
			throw new AuthorizedException("This account doesn't belong to this user");
		}
		int pageNumber = 1;
		if (page.isPresent()) {
			pageNumber = page.get();
		}
		return transactionService.getTransactions(accountId, pageNumber);
	}
}
