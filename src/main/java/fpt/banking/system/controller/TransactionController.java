package fpt.banking.system.controller;

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
import fpt.banking.system.exception.BadRequestException;
import fpt.banking.system.payload.TransactionsResponse;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.AccountService;
import fpt.banking.system.service.TransactionService;

@RestController
@RequestMapping("/api/users")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private AccountService accountService;

	@GetMapping("/{userId}/accounts/{accountId}/transactions")
	@PreAuthorize("hasRole('ROLE_USER')")
	public TransactionsResponse getTransaction(
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("month") Optional<Integer> month,
			@RequestParam("year") Optional<Integer> year,
			@PathVariable int accountId,
			@AuthenticationPrincipal UserPrincipal user) {
		if (accountService.getAccount(accountId).getUser().getId() != user.getId()) {
			throw new AuthorizedException("You don't have permission to access this resource");
		}
		int pageNumber = 1;
		if (page.isPresent()) {
			pageNumber = page.get();
		}
		if (!month.isPresent() && !year.isPresent()) {
			return transactionService.getTransactions(accountId, pageNumber);
		} else if (month.isPresent() && !year.isPresent()) {
			throw new BadRequestException("You must provide year");
		} else {
			int yearFilter = year.get();
			int monthFilter = 0;
			if (month.isPresent()) {
				monthFilter = month.get();
			}
			System.out.println(pageNumber);
			return transactionService.getTransactionWithTimeFilter(accountId, pageNumber, yearFilter, monthFilter);
		}
	}
	
	@GetMapping("/current/accounts/{accountId}/transactions/search")
	@PreAuthorize("hasRole('ROLE_USER')")
	public TransactionsResponse searchTransactions(
			@AuthenticationPrincipal UserPrincipal currentUser,
			@PathVariable int accountId,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("term") Optional<String> term) {
		if (accountService.getAccount(accountId).getUser().getId() != currentUser.getId()) {
			throw new AuthorizedException("You don't have permission to access this resource");
		}
		int pageNumber = 1;
		if (page.isPresent()) {
			pageNumber = page.get();
		}
		String searchTerm = "";
		if (term.isPresent()) {
			searchTerm = term.get();
		}
		if (searchTerm.trim().equals("")) {
			return transactionService.getTransactions(accountId, pageNumber);
		}
		return transactionService.getTransactionsWithSearchTerm(Long.valueOf(accountId), pageNumber, searchTerm);
	}
}
