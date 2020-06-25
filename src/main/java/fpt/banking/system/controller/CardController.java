package fpt.banking.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.model.Account;
import fpt.banking.system.model.Card;
import fpt.banking.system.service.CardService;

@RestController
@RequestMapping("/api/users")
public class CardController {

	@Autowired
	private CardService cardService;
	
	@GetMapping("/{userId}/accounts/{accountId}/card")
	@PreAuthorize("hasRole('ROLE_USER')")
	public Card getCard(@PathVariable int accountId) {
		return cardService.getCard(accountId);
	}
}
