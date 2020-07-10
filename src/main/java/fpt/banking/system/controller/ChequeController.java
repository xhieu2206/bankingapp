package fpt.banking.system.controller;

import java.io.IOException;
import java.sql.Timestamp;
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
import fpt.banking.system.model.User;
import fpt.banking.system.payload.ChequeForUserResponse;
import fpt.banking.system.payload.ChequeRequestPayload;
import fpt.banking.system.payload.UpdatedChequeRequestPayload;
import fpt.banking.system.response.SuccessfulResponse;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.AccountService;
import fpt.banking.system.service.ChequeService;
import fpt.banking.system.service.NotificationService;
import fpt.banking.system.service.UserService;
import fpt.banking.system.util.SendEmail;

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
	public List<ChequeForUserResponse> getCheques(@PathVariable int accountId, @AuthenticationPrincipal UserPrincipal user) {
		if (accountService.getAccount(accountId).getUser().getId() != user.getId()) {
			throw new AuthorizedException("You don't have permission to access this resource");
		}
		return chequeService.getCheques(accountId);
	}
	
	@PostMapping("/{userId}/accounts/{accountId}/cheques")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> createCheque(
			@PathVariable long accountId,
			@PathVariable long userId,
			@AuthenticationPrincipal UserPrincipal user,
			@RequestBody ChequeRequestPayload payload) {
		if (accountService.getAccount(accountId).getUser().getId() != user.getId() || accountService.getAccount(accountId) == null) {
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
				payload.getRecieverFullname().trim().toUpperCase(), 
				payload.getRecieverIdCardNumber(), 
				payload.getTransactionAmount());

		notificationService.saveNotification(
				"You have created a cheque for " +
				payload.getRecieverFullname() + " with id card number is " +
				payload.getRecieverIdCardNumber() + ", amount is " +
				payload.getTransactionAmount(), account.getUser());

		try {
			SendEmail.sendEmail(
					account.getUser().getEmail(),
					"You have created a cheque for " +
					payload.getRecieverFullname() + " with id card number is " +
					payload.getRecieverIdCardNumber() + ", amount is " +
					payload.getTransactionAmount());
		} catch (IOException e) {
			System.out.println("Couldn't send email");
		}

		SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "Your cheque has been created successfully", System.currentTimeMillis());
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
	
	@GetMapping("/{userId}/accounts/{accountId}/cheques/{chequeId}/cancel")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> cancelCheque(
			@PathVariable long accountId,
			@PathVariable long userId,
			@PathVariable long chequeId,
			@AuthenticationPrincipal UserPrincipal user) {
		if (accountService.getAccount(accountId).getUser().getId() != user.getId() || accountService.getAccount(accountId) == null) {
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
		Cheque cheque = chequeService.getChequeById(chequeId);
		if (cheque == null || cheque.getAccount().getId() != accountId) {
			ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "You don't have permission to access this resource",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.UNAUTHORIZED);
		}
		chequeService.cancelCheque(chequeId);

		notificationService.saveNotification("Your cheque for " + cheque.getRecieverFullname() + ", amount " + cheque.getTransactionAmount() + " VND has been canceled by"
				+ " yourself",
				cheque.getAccount().getUser());

		try {
			SendEmail.sendEmail(
					cheque.getAccount().getUser().getEmail(),
					"Your cheque for " + cheque.getRecieverFullname() + ", amount " + cheque.getTransactionAmount() + " VND has been canceled by"
					+ " yourself");
		} catch (IOException e) {
			System.out.println("Couldn't send email");
		}

		SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "Your cheque has been canceled", System.currentTimeMillis());
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
	
	@PostMapping("/current/accounts/cheques/edit")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> editCheque(
			@AuthenticationPrincipal UserPrincipal currentUser,
			@RequestBody UpdatedChequeRequestPayload payload) {
		Cheque cheque = chequeService.getChequeById(payload.getChequeId());
		Timestamp now = new Timestamp(System.currentTimeMillis() + 300000);
		User user = userService.getUser(currentUser.getId());
		if (cheque == null) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "This cheque doesn't existed", System.currentTimeMillis());
			return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
		}
		if (cheque.getAccount().getUser().getId() != user.getId()) {
			ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "This cheque doesn't belong to you", System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.UNAUTHORIZED);
		}
		if (cheque.isCanceled() == true) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "This cheque has been canceled", System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		if (cheque.isStatus() == true) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "This cheque has been deposited", System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		if (cheque.getExpiredDate().getTime() < now.getTime()) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "This cheque has been expired", System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		if (payload.getRecieverFullname().trim().equals("") || payload.getRecieverIdCardNumber().trim().equals("")) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Fields cannot be empty", System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		if (payload.getTransactionAmount() <= 0) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Transaction amount must be greater than 0", System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		chequeService.updateCheque(
				payload.getChequeId(),
				payload.getRecieverFullname().trim().toUpperCase(),
				payload.getRecieverIdCardNumber().trim(),
				payload.getTransactionAmount());

		notificationService.saveNotification(
				"You have updated your cheque", 
				user);

		try {
			SendEmail.sendEmail(
					user.getEmail(),
					"You have updated your cheque");
		} catch (IOException e) {
			System.out.println("Couldn't send email");
		}

		SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "Your cheque has been updated", System.currentTimeMillis());
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
}
