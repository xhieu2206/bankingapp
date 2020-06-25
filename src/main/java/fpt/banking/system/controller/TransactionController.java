package fpt.banking.system.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.payload.TransactionsResponse;

@RestController
@RequestMapping("/api/users")
public class TransactionController {

	@GetMapping("/{userId}/accounts/{accountId}/transactions")
	@PreAuthorize("hasRole('ROLE_USER')")
	public List<TransactionsResponse> getTransaction(@RequestParam int page,
			@PathVariable int accountId) {
		return null;
	}
}
