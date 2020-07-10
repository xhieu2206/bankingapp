package fpt.banking.system.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
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

import fpt.banking.system.dao.AccountDAO;
import fpt.banking.system.exception.AccountNotFound;
import fpt.banking.system.exception.ErrorResponse;
import fpt.banking.system.exception.UserNotFoundException;
import fpt.banking.system.model.Account;
import fpt.banking.system.model.Role;
import fpt.banking.system.model.User;
import fpt.banking.system.payload.AccountForAdminResponse;
import fpt.banking.system.payload.DepositRequestPayload;
import fpt.banking.system.payload.WithdrawRequestPayload;
import fpt.banking.system.response.SuccessfulResponse;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.AccountService;
import fpt.banking.system.service.NotificationService;
import fpt.banking.system.service.TransactionService;
import fpt.banking.system.service.UserService;
import fpt.banking.system.util.SendEmail;

@RestController
@RequestMapping("/api/admin")
public class AdminAccountController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private NotificationService notificationService;

	@GetMapping("/users/{userId}/accounts")
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_TRANSACTIONMANAGER', 'ROLE_BRANCHMANAGER', 'ROLE_BANKMANAGER')")
	public List<AccountForAdminResponse> getAllAccounts(@PathVariable long userId) {
		List<AccountForAdminResponse> results = new ArrayList<AccountForAdminResponse>();
		List<Account> accounts = accountService.getAccounts(userId);
		if (accounts == null) {
			throw new AccountNotFound("This user doesn't have any useable account");
		}
		for (Account account: accounts) {
			AccountForAdminResponse result = new AccountForAdminResponse(
					account.getId(),
					account.getAccountNumber(),
					account.getAmount(),
					account.isStatus(),
					account.getExpiredAt(),
					account.getCreatedAt(),
					account.getUpdatedAt());
			results.add(result);
		}
		return results;
	}
	
	@GetMapping("/users/{userId}/useableAccounts")
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_TRANSACTIONMANAGER', 'ROLE_BRANCHMANAGER', 'ROLE_BANKMANAGER')")
	public List<AccountForAdminResponse> getAllUseableAccounts(@PathVariable long userId) {
		List<AccountForAdminResponse> results = new ArrayList<AccountForAdminResponse>();
		List<Account> accounts = accountService.getUseableAccounts(userId);
		System.out.println(accounts);
		if (accounts == null) {
			throw new AccountNotFound("This user doesn't have any useable account");
		}
		for (Account account: accounts) {
			AccountForAdminResponse result = new AccountForAdminResponse(
					account.getId(),
					account.getAccountNumber(),
					account.getAmount(),
					account.isStatus(),
					account.getExpiredAt(),
					account.getCreatedAt(),
					account.getUpdatedAt());
			results.add(result);
		}
		return results;
	}
	
	@PostMapping("/account/deposit")
	@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> deposit(
			@RequestBody DepositRequestPayload payload,
			@AuthenticationPrincipal UserPrincipal emp) {
		User user = userService.getUser(payload.getUserId());
		Account account = accountService.getAccount(payload.getAccountId());
		if (user == null || account == null) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "User or Account not found",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
		}
		User employee = userService.getUser(emp.getId());
		if (account.getUser().getId() != user.getId()) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "This account doesn't belong to this user",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		if (account.isStatus() == false) {
			ErrorResponse error = new ErrorResponse(HttpStatus.LOCKED.value(), "This account has been locked",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.LOCKED);
		}
		transactionService.saveTransaction(
				payload.getAccountId(),
				payload.getAmount(), 
				account.getAmount() + payload.getAmount(), 
				5,
				"Deposit " + payload.getAmount() +"VND from transaction office " + employee.getTransactionOffice().getName());

		notificationService.saveNotification(
				"Deposit " + payload.getAmount() +"VND from transaction office " + employee.getTransactionOffice().getName(),
				user);
		
		try {
			SendEmail.sendEmail(
					user.getEmail(),
					"Deposit " + payload.getAmount() +"VND from transaction office " + employee.getTransactionOffice().getName());
		} catch (IOException e) {
			System.out.println("Couldn't send email");
		}

		accountService.changeAmount(payload.getAccountId(), account.getAmount() + payload.getAmount());
		SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "Deposit successfully", System.currentTimeMillis());
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
	
	@PostMapping("/account/withdraw")
	@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> withdraw(
			@RequestBody WithdrawRequestPayload payload,
			@AuthenticationPrincipal UserPrincipal emp) {
		User user = userService.getUser(payload.getUserId());
		Account account = accountService.getAccount(payload.getAccountId());
		long amount = payload.getAmount();
		if (user == null || account == null) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "User or Account not found",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
		}
		User employee = userService.getUser(emp.getId());
		if (account.getUser().getId() != user.getId()) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "This account doesn't belong to this user",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		if (account.isStatus() == false) {
			ErrorResponse error = new ErrorResponse(HttpStatus.LOCKED.value(), "This account has been locked",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.LOCKED);
		}
		if (user.getMembership().getName() == "GOLD") {
			amount = amount + 10000;
		} else if (user.getMembership().getName() == "PLATINUM") {
			amount = amount + 5000;
		}
		if (amount > account.getAmount()) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "This account doesn't have enough money for this transaction",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		notificationService.saveNotification(
				"Withdraw " + payload.getAmount() +"VND from transaction office " + employee.getTransactionOffice().getName(), 
				user);
		try {
			SendEmail.sendEmail(
					user.getEmail(),
					"Withdraw " + payload.getAmount() +"VND from transaction office " + employee.getTransactionOffice().getName());
		} catch (IOException e) {
			System.out.println("Couldn't send email");
		}

		transactionService.saveTransaction(account.getId(), amount * -1, account.getAmount() - amount, 6, "Withdraw " + payload.getAmount() +"VND from transaction office " + employee.getTransactionOffice().getName());
		accountService.changeAmount(account.getId(), account.getAmount() - amount);
		SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "Withdraw successfully", System.currentTimeMillis());
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
}
