package fpt.banking.system.controller;

import java.io.IOException;
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

import fpt.banking.system.exception.AccountNotFound;
import fpt.banking.system.exception.AuthorizedException;
import fpt.banking.system.exception.ErrorResponse;
import fpt.banking.system.model.Account;
import fpt.banking.system.model.User;
import fpt.banking.system.payload.AccountIdRequestPayload;
import fpt.banking.system.response.SuccessfulResponse;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.AccountService;
import fpt.banking.system.service.NotificationService;
import fpt.banking.system.service.UserService;
import fpt.banking.system.util.SendEmail;

@RestController
@RequestMapping("/api/users")
public class AccountController {
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private AccountService accountService;
	
	@GetMapping("/{userId}/accounts")
	@PreAuthorize("hasRole('ROLE_USER')")
	public List<Account> getAccounts(@PathVariable int userId,
			@AuthenticationPrincipal UserPrincipal user) {
		if (user.getId() != userId) {
			throw new AuthorizedException("You don't have permission to access this resource");
		}
		return accountService.getAccounts(userId);
	}
	
	@GetMapping("/{userId}/useableAccounts")
	@PreAuthorize("hasRole('ROLE_USER')")
	public List<Account> getUseableAccounts(@PathVariable long userId,
			@AuthenticationPrincipal UserPrincipal user) {
		if (user.getId() != userId) {
			throw new AuthorizedException("You don't have permission to access this resource");
		}
		return accountService.getUseableAccounts(userId);
	}
	
	@PostMapping("/current/account/lock")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> lockAccount(
			@RequestBody AccountIdRequestPayload payload,
			@AuthenticationPrincipal UserPrincipal currentUser) {
		if (accountService.getAccount(payload.getAccountId()) == null) {
			throw new AccountNotFound("This account doesn't existed");
		}
		if (accountService.getAccount(payload.getAccountId()).getUser().getId() != currentUser.getId()) {
			throw new AuthorizedException("You don't have permission to access this resource");
		}
		if (accountService.getAccount(payload.getAccountId()).isStatus() == false) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "This account has been locked already", System.currentTimeMillis());
			return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		User user = userService.getUser(currentUser.getId());
		accountService.lockAccount(payload.getAccountId());

		notificationService.saveNotification(
				"Ban da khoa tai khoan ngan hang cua minh tai FPT Banking voi so tai khoan la " + accountService.getAccount(payload.getAccountId()).getAccountNumber(), 
				user);

		try {
			SendEmail.sendEmail(
					user.getEmail(),
					"Ban da khoa tai khoan ngan hang cua minh tai FPT Banking voi so tai khoan la " + accountService.getAccount(payload.getAccountId()).getAccountNumber());
		} catch (IOException e) {
			System.out.println("Couldn't send email");
		}

		SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "Your account has been locked, please contact admin to unlock your account", System.currentTimeMillis());
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
}
