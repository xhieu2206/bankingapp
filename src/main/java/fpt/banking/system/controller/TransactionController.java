package fpt.banking.system.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.payload.TransactionsResponse;
import fpt.banking.system.service.TransactionService;

@RestController
@RequestMapping("/api/users")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;

	@GetMapping("/{userId}/accounts/{accountId}/transactions")
	@PreAuthorize("hasRole('ROLE_USER')")
	public TransactionsResponse getTransaction(@RequestParam("page") Optional<Integer> page,
			@PathVariable int accountId) {
		int pageNumber = 1;
		if (page.isPresent()) {
			pageNumber = page.get();
		}
		return transactionService.getTransactions(accountId, pageNumber);
	}
}
