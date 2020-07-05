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

import fpt.banking.system.exception.AccountNotFound;
import fpt.banking.system.exception.ErrorResponse;
import fpt.banking.system.exception.UserNotFoundException;
import fpt.banking.system.model.Account;
import fpt.banking.system.model.Asset;
import fpt.banking.system.model.LoanInterestRate;
import fpt.banking.system.model.LoanProfile;
import fpt.banking.system.model.Role;
import fpt.banking.system.model.TransactionOffice;
import fpt.banking.system.model.User;
import fpt.banking.system.payload.AssetRequestPayload;
import fpt.banking.system.payload.LoanProfileRequestPayload;
import fpt.banking.system.response.SuccessfulResponse;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.AccountService;
import fpt.banking.system.service.LoanService;
import fpt.banking.system.service.NotificationService;
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
	
	@Autowired
	private NotificationService notificationService;

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
		if (accountService.getAccount(payload.getAccountId()).getLoanProfile() != null) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "This account have been used for another loan profile, please choose another account",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		long loanProfileId = loanService.saveLoanProfile(payload.getAmount(), payload.getDescription(), accountService.getAccount(payload.getAccountId()), 
				loanService.findLoanInterestRateById(payload.getLoanInterestRateId()), 
				userService.getUser(userId), 
				transactionOfficeService.findTransactionOfficeById(emp.getTransactionOffice().getId()));
		
		SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), String.valueOf(loanProfileId), System.currentTimeMillis());
		notificationService.saveNotification("You have create a new loan profile, please contact your admin for more info or checking in our banking application", userService.getUser(userId));
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
	
	@PostMapping("/admin/users/{userId}/loanprofiles/{loanProfileId}/assets")
	@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> addAsset(
			@PathVariable long userId,
			@PathVariable long loanProfileId,
			@RequestBody AssetRequestPayload payload,
			@AuthenticationPrincipal UserPrincipal emp) {
		User user = userService.getUser(userId);
		if (userService.getUser(userId) == null) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "User not found",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
		}
		if (loanService.findLoanProfileById(loanProfileId) == null) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Loan profile not found",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
		}
		boolean hasLoanProfile = false;
		List<LoanProfile> loanProfiles = loanService.findLoanProfilesByUser(user);
		for (LoanProfile loanProfile: loanProfiles) {
			if (loanProfile.getId() == loanProfileId) {
				hasLoanProfile = true;
				break;
			}
		}
		if (hasLoanProfile == false) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "This loan profile doesn't belong to this user",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		LoanProfile loanProfile = loanService.findLoanProfileById(loanProfileId);
		long assetId = loanService.saveAsset(
				payload.getName(), 
				payload.getDescription(), 
				payload.getPrice(), 
				loanProfile);
		Asset asset = loanService.findAssetById(assetId);
		for (String url: payload.getImages()) {
			loanService.saveImagesAsset(url, asset);
		}
		SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), String.valueOf(loanProfileId), System.currentTimeMillis());
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
	
	@GetMapping("/loanInterestRates")
	public List<LoanInterestRate> getAllLoanInterestRates() {
		return loanService.getAllLoanInterestRate();
	}
	
	@GetMapping("/admin/users/{userId}/loanprofiles")
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_TRANSACTIONMANAGER', 'ROLE_BRANCHMANAGER', 'ROLE_BANKMANAGER')")
	public List<LoanProfile> getLoanProfileOfAnUser(@PathVariable long userId, @AuthenticationPrincipal UserPrincipal currentAdmin) {
		String role = "";
		User admin = userService.getUser(currentAdmin.getId());
		User user = userService.getUser(userId);
		if (user == null) {
			throw new UserNotFoundException("This user doesn't not existed");
		}
		for (Role r: userService.getUser(admin.getId()).getRoles()) {
			role = r.getName();
		}
		List<LoanProfile> results = new ArrayList<LoanProfile>();
		if (role.equals("ROLE_EMPLOYEE") || role.equals("ROLE_TRANSACTIONMANAGER")) {
			List<LoanProfile> loanProfiles = loanService.findLoanProfilesByUser(user);
			for (LoanProfile loanProfile: loanProfiles) {
				if (loanProfile.getTransactionOffice().getId() == admin.getTransactionOffice().getId()) {
					System.out.println("VAO DAY");
					results.add(loanProfile);
					System.out.println(results.size());
				}
			}
		} else if (role.equals("ROLE_BRANCHMANAGER")) {
			List<TransactionOffice> transactionOffices = new ArrayList<TransactionOffice>();
			for (TransactionOffice transactionOffice: admin.getBranchOffice().getTransactionOffices()) {
				for (LoanProfile loanProfile: transactionOffice.getLoanProfiles()) {
					results.add(loanProfile);
				}
			}
		}
		return results;
	}
}
