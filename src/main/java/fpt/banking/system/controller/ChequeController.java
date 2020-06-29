package fpt.banking.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.exception.AuthorizedException;
import fpt.banking.system.model.Cheque;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.AccountService;
import fpt.banking.system.service.ChequeService;

@RestController
@RequestMapping("/api/users")
public class ChequeController {

	@Autowired
	private ChequeService chequeService;
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/{userId}/accounts/{accountId}/cheques")
	@PreAuthorize("hasRole('ROLE_USER')")
	public List<Cheque> getCheques(@PathVariable int accountId, @AuthenticationPrincipal UserPrincipal user) {
		if (accountService.getAccount(accountId).getUser().getId() != user.getId()) {
			throw new AuthorizedException("You don't have permission to access this resource");
		}
		return chequeService.getCheques(accountId);
	}
}
