package fpt.banking.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.exception.ErrorResponse;
import fpt.banking.system.exception.NotFoundException;
import fpt.banking.system.model.Account;
import fpt.banking.system.model.LoanProfile;
import fpt.banking.system.model.TransactionOffice;
import fpt.banking.system.model.User;
import fpt.banking.system.payload.LoanProfileFromUserRequestPayload;
import fpt.banking.system.payload.LoanProfileRequestPayload;
import fpt.banking.system.response.SuccessfulResponse;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.AccountService;
import fpt.banking.system.service.LoanService;
import fpt.banking.system.service.NotificationService;
import fpt.banking.system.service.TransactionOfficeService;
import fpt.banking.system.service.UserService;

@RestController
@RequestMapping("/api/users")
public class LoanController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TransactionOfficeService transactionOfficeService;
	
	@Autowired
	private LoanService loanService;
	
	@Autowired
	private NotificationService notificationService;
	
	@GetMapping("/current/loanProfiles")
	@PreAuthorize("hasRole('ROLE_USER')")
	public List<LoanProfile> getAll(@AuthenticationPrincipal UserPrincipal currentUser) {
		User user = userService.getUser(currentUser.getId());
		if (user.getLoanProfiles().size() == 0) {
			throw new NotFoundException("You don't have any loan profile");
		}
		return user.getLoanProfiles();
	}
	
	@PostMapping("/current/loanProfiles")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> createLoanProfile(
			@AuthenticationPrincipal UserPrincipal currentUser,
			@RequestBody LoanProfileFromUserRequestPayload payload) {
		User user = userService.getUser(currentUser.getId());
		TransactionOffice transactionOffice = transactionOfficeService.findTransactionOfficeById(payload.getTransasctionOfficeId());
		Account account = accountService.getAccount(payload.getAccountId());
		if (account == null) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "This account doesn't existed",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
		}
		boolean hasAccount = false;
		List<Account> accounts = accountService.getUseableAccounts(user.getId());
		for (Account a: accounts) {
			if (a.getId() == account.getId()) {
				hasAccount = true;
				break;
			}
		}
		if (hasAccount == false) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "This account doesn't belong to you or this account has been locked",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		if (account.getLoanProfile() != null) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "This account have been used for another loan profile, please choose another account",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		long loanProfileId = loanService.saveLoanProfile(
				payload.getAmount(), payload.getDescription(),
				account, 
				loanService.findLoanInterestRateById(payload.getLoanInterestRateId()), 
				user, 
				transactionOffice);
		notificationService.saveNotification(
				"You have create a new loan profile, please contact your admin for more info or checking in our banking application",
				user);
		SuccessfulResponse res = new SuccessfulResponse(
				HttpStatus.OK.value(),
				"You have create a new loan profile successfully, please contact your admin to confirmed and add asset for this profile",
				System.currentTimeMillis());
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
	
}
