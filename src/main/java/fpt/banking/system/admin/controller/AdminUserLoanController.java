package fpt.banking.system.admin.controller;

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
import fpt.banking.system.exception.ErrorResponse;
import fpt.banking.system.model.Account;
import fpt.banking.system.model.LoanInterestRate;
import fpt.banking.system.model.User;
import fpt.banking.system.payload.LoanProfileRequestPayload;
import fpt.banking.system.response.SuccessfulResponse;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.AccountService;
import fpt.banking.system.service.LoanService;
import fpt.banking.system.service.TransactionOfficeService;
import fpt.banking.system.service.TransactionService;
import fpt.banking.system.service.UserService;

@RestController
@RequestMapping("/api")
public class AdminUserLoanController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoanService loanService;
	
	@Autowired
	private TransactionOfficeService transactionOfficeService;

	@PostMapping("/admin/users/{userId}/loanprofiles")
	@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> addLoanProfile(@PathVariable long userId, @RequestBody LoanProfileRequestPayload payload, @AuthenticationPrincipal UserPrincipal user) {
		User emp = userService.getUser(user.getId());
		if (userService.getUser(userId) == null) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "User not found",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
		}
		if (accountService.getAccount(payload.getAccountId()) == null) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "This account doesn't existed",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
		}
		boolean hasAccount = false;
		List<Account> accounts = accountService.getUseableAccounts(userId);
		for (Account account: accounts) {
			if (account.getId() == payload.getAccountId()) {
				hasAccount = true;
				break;
			}
		}
		if (hasAccount == false) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "This account doesn't belong to this user or this account has been locked",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		long loanProfileId = loanService.saveLoanProfile(payload.getAmount(), payload.getDescription(), accountService.getAccount(payload.getAccountId()), 
				loanService.findLoanInterestRateById(payload.getLoanInterestRateId()), 
				userService.getUser(userId), 
				transactionOfficeService.findTransactionOfficeById(emp.getTransactionOffice().getId()));
		SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), String.valueOf(loanProfileId), System.currentTimeMillis());
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
	
//	@PostMapping("/admin/users/{userId}/loanprofiles")
	
	@GetMapping("/loanInterestRates")
	public List<LoanInterestRate> getAllLoanInterestRates() {
		return loanService.getAllLoanInterestRate();
	}
	
}
