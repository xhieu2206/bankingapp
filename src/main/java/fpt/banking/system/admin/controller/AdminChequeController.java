package fpt.banking.system.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.payload.DepositChequeRequestPayload;
import fpt.banking.system.service.AccountService;
import fpt.banking.system.service.ChequeService;
import fpt.banking.system.service.TransactionService;
import fpt.banking.system.service.UserService;

@RestController
@RequestMapping("/api")
public class AdminChequeController {

	@Autowired
	private ChequeService chequeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TransactionService transactionService;
	
	@PostMapping("/admin/deposit/cheque")
	@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> depositCheque(
			@RequestBody DepositChequeRequestPayload payload) {
		
		return null;
	}
	
}

