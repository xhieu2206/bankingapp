package fpt.banking.system.admin.controller;

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
import fpt.banking.system.model.Account;
import fpt.banking.system.model.User;
import fpt.banking.system.payload.AccountForAdminResponse;
import fpt.banking.system.payload.DepositRequestPayload;
import fpt.banking.system.response.SuccessfulResponse;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.AccountService;
import fpt.banking.system.service.NotificationService;
import fpt.banking.system.service.TransactionService;
import fpt.banking.system.service.UserService;

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
			AccountForAdminResponse result = new AccountForAdminResponse(account.getId(), account.getAccountNumber(), account.getAmount(), account.isStatus(), account.getExpiredAt(), account.getCreatedAt(), account.getUpdatedAt());
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
			AccountForAdminResponse result = new AccountForAdminResponse(account.getId(), account.getAccountNumber(), account.getAmount(), account.isStatus(), account.getExpiredAt(), account.getCreatedAt(), account.getUpdatedAt());
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
		transactionService.saveTransaction(payload.getAccountId(), payload.getAmount(), 
				account.getAmount() + payload.getAmount(), 5, "Deposit " + payload.getAmount() +"VND from transaction office " + employee.getTransactionOffice().getName());
		notificationService.saveNotification("Deposit " + payload.getAmount() +"VND from transaction office " + employee.getTransactionOffice().getName(), user);
		accountService.changeAmount(payload.getAccountId(), payload.getAmount());
		SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "Deposit successfully", System.currentTimeMillis());
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
}
