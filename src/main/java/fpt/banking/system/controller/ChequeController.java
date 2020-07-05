package fpt.banking.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.exception.AuthorizedException;
import fpt.banking.system.exception.ErrorResponse;
import fpt.banking.system.model.Account;
import fpt.banking.system.model.Cheque;
import fpt.banking.system.payload.ChequeRequestPayload;
import fpt.banking.system.response.SuccessfulResponse;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.AccountService;
import fpt.banking.system.service.ChequeService;
import fpt.banking.system.service.NotificationService;
import fpt.banking.system.service.UserService;

@RestController
@RequestMapping("/api/users")
public class ChequeController {
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private UserService userService;

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
	
	@PostMapping("/{userId}/accounts/{accountId}/cheques")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> createCheque(@PathVariable long accountId, @PathVariable long userId, @AuthenticationPrincipal UserPrincipal user,
			@RequestBody ChequeRequestPayload payload) {
		if (accountService.getAccount(accountId).getUser().getId() != user.getId()) {
			ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "You don't have permission to access this resource",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.UNAUTHORIZED);
		}
		if (userId != user.getId()) {
			ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "You don't have permission to access this resource",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.UNAUTHORIZED);
		}
		if (accountService.getAccount(accountId).isStatus() == false) {
			ErrorResponse error = new ErrorResponse(HttpStatus.LOCKED.value(), "Your account you choosing is locked, please contact your admin for more detail",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.LOCKED);
		}
		Account account = accountService.getAccount(accountId);
		chequeService.saveCheque(account, 
				payload.getRecieverFullname(), 
				payload.getRecieverIdCardNumber(), 
				payload.getTransactionAmount());
		notificationService.saveNotification("You have created a cheque for " +
				payload.getRecieverFullname() + " with id card number is " +
				payload.getRecieverIdCardNumber() + ", amount is " +
				payload.getTransactionAmount(), account.getUser());
		SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "Your cheque has been created successfully", System.currentTimeMillis());
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
}
