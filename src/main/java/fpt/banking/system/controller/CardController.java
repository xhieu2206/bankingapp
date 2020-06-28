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
import fpt.banking.system.model.Account;
import fpt.banking.system.model.Card;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.AccountService;
import fpt.banking.system.service.CardService;

@RestController
@RequestMapping("/api/users")
public class CardController {

	@Autowired
	private CardService cardService;
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/{userId}/accounts/{accountId}/card")
	@PreAuthorize("hasRole('ROLE_USER')")
	public Card getCard(@PathVariable int accountId, @AuthenticationPrincipal UserPrincipal user) {
		if (accountService.getAccount(accountId).getUser().getId() != user.getId()) {
			throw new AuthorizedException("You don't have permission to access this resource");
		}
		return cardService.getCard(accountId);
	}
}
