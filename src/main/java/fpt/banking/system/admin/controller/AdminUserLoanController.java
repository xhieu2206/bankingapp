package fpt.banking.system.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.exception.AccountNotFound;
import fpt.banking.system.exception.AuthorizedException;
import fpt.banking.system.exception.ErrorResponse;
import fpt.banking.system.exception.NotFoundException;
import fpt.banking.system.exception.UserNotFoundException;
import fpt.banking.system.model.Account;
import fpt.banking.system.model.Asset;
import fpt.banking.system.model.LoanInterestRate;
import fpt.banking.system.model.LoanProfile;
import fpt.banking.system.model.LoanProfileQueue;
import fpt.banking.system.model.Role;
import fpt.banking.system.model.TransactionOffice;
import fpt.banking.system.model.User;
import fpt.banking.system.payload.AssetRequestPayload;
import fpt.banking.system.payload.LoanProfileIdConfirmPayload;
import fpt.banking.system.payload.LoanProfileRequestPayload;
import fpt.banking.system.payload.LoanProfilesResponsePayload;
import fpt.banking.system.payload.RejectedLoanProfileRequestPayload;
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
	private TransactionService transactionService;
	
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
	public ResponseEntity<?> addLoanProfile(
			@PathVariable long userId,
			@RequestBody LoanProfileRequestPayload payload,
			@AuthenticationPrincipal UserPrincipal currentEmployee) {
		User emp = userService.getUser(currentEmployee.getId());
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
					results.add(loanProfile);
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
	
	@PostMapping("/admin/users/{userId}/loanprofiles/confirm")
	@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> confirm(
		@PathVariable long userId,
		@RequestBody LoanProfileIdConfirmPayload payload
			) {
		LoanProfile loanProfile = loanService.findLoanProfileById(payload.getLoanProfileId());
		if (loanProfile == null) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "This loan profile doesn't existed",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
		}
		if (loanProfile.getUser().getId() != userId) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "This loan profile doesn't belong to this user",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		long id = loanService.saveLoanProfileQueue(payload.getLoanProfileId());
		LoanProfileQueue loanProfileQueue = loanService.findLoanProfileQueueById(id);
		long loanProfileId = loanProfileQueue.getLoanProfileId();
		SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), String.valueOf(loanProfileId), System.currentTimeMillis());
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
	
	@GetMapping("/admin/transaction-office/loan-profiles")
	@PreAuthorize("hasAnyRole('ROLE_TRANSACTIONMANAGER', 'ROLE_EMPLOYEE')")
	public LoanProfilesResponsePayload getLoanProfilesForTransactionManager(
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("status") Optional<Integer> status,
			@AuthenticationPrincipal UserPrincipal currentAdmin
			) {
		int pageNumber = 1;
		String statusLoanProfile = "ALL";
		if (page.isPresent()) {
			pageNumber = page.get();
		}
		if (status.isPresent()) {
			statusLoanProfile = String.valueOf(status.get());
		}
		User admin = userService.getUser(currentAdmin.getId());
		TransactionOffice transactionOffice = admin.getTransactionOffice();
		return loanService.getLoanProfilesOfTransactionOffice(transactionOffice.getId(), pageNumber, statusLoanProfile);
	}
	
	@PostMapping("/admin/loan-profiles/approved")
	@PreAuthorize("hasAnyRole('ROLE_TRANSACTIONMANAGER', 'ROLE_BRANCHMANAGER')")
	public ResponseEntity<?> approvedLoanProfile(
			@RequestBody LoanProfileIdConfirmPayload payload,
			@AuthenticationPrincipal UserPrincipal currentAdmin) {
		User admin = userService.getUser(currentAdmin.getId());
		LoanProfile loanProfile = loanService.findLoanProfileById(payload.getLoanProfileId());
		if (loanProfile == null) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Loan profile not found",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
		}
		if (loanProfile.isApproved() == true) {
			SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "This loan profile has already been approved", System.currentTimeMillis());
			return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
		}
		if (loanProfile.isRejected() == true) {
			SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "This loan profile has already been rejected, cannot approved it again", System.currentTimeMillis());
			return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
		}
		String role = "";
		for (Role r: admin.getRoles()) {
			role = r.getName();
		}
		if (role.equals("ROLE_TRANSACTIONMANAGER") == true) {
			if (loanProfile.getTransactionOffice().getId() != admin.getTransactionOffice().getId()) {
				ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "You don't have this permission",
	    				System.currentTimeMillis());
	    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.UNAUTHORIZED);
			}
			loanService.approvedLoanProfileByTransactionManager(loanProfile.getId());
			SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "APPROVED BY TRANSACTION MANAGER", System.currentTimeMillis());
			return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
		} else if (role.equals("ROLE_BRANCHMANAGER") == true) {
			if (loanProfile.getTransactionOffice().getBranchOffice().getId() != admin.getBranchOffice().getId()) {
				ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "You don't have this permission",
	    				System.currentTimeMillis());
	    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.UNAUTHORIZED);
			}
			loanService.approvedLoanProfileByBranchManager(loanProfile.getId());
			SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "APPROVED BY BRANCH MANAGER", System.currentTimeMillis());
			notificationService.saveNotification(
					"Your loan profile has been approved." +
					" Please check account with account number " + loanProfile.getAccount().getAccountNumber() +
					" for more detail", loanProfile.getUser());
			transactionService.saveTransaction(
					loanProfile.getAccount().getId(),
					loanProfile.getAmount(),
					loanProfile.getAccount().getAmount() + loanProfile.getAmount(),
					4, 
					"You has got " + loanProfile.getAmount() + " from your approved loaning");
			accountService.changeAmount(loanProfile.getAccount().getId(), loanProfile.getAmount() + loanProfile.getAccount().getAmount());
			return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
		}
		return null;
	}
	
	@GetMapping("admin/branch-office/transaction-offices/{transactionOfficeId}/loan-profiles")
	@PreAuthorize("hasRole('ROLE_BRANCHMANAGER')")
	public LoanProfilesResponsePayload getLoanProfilesForBranchManager(
			@PathVariable long transactionOfficeId,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("status") Optional<Integer> status,
			@AuthenticationPrincipal UserPrincipal currentBranchManager
			) {
		TransactionOffice transactionOffice = transactionOfficeService.findTransactionOfficeById(transactionOfficeId);
		User branchManager = userService.getUser(currentBranchManager.getId());
		if (transactionOffice == null) {
			throw new NotFoundException("Transaction office not found");
		}
		if (transactionOffice.getBranchOffice().getId() != branchManager.getBranchOffice().getId()) {
			throw new AuthorizedException("You don't have this permission");
		}
		int pageNumber = 1;
		String statusLoanProfile = "";
		if (page.isPresent()) {
			pageNumber = page.get();
		}
		if (status.isPresent()) {
			statusLoanProfile = String.valueOf(status.get());
		}
		return loanService.getLoanProfilesOfTransactionOffice(transactionOfficeId, pageNumber, statusLoanProfile);
	}
	
	@PostMapping("/admin/loan-profiles/rejected")
	@PreAuthorize("hasAnyRole('ROLE_TRANSACTIONMANAGER', 'ROLE_BRANCHMANAGER')")
	public ResponseEntity<?> rejectedLoanProfile(
			@RequestBody RejectedLoanProfileRequestPayload payload,
			@AuthenticationPrincipal UserPrincipal currentAdmin) {
		System.out.println(payload.getLoanProfileId());
		User admin = userService.getUser(currentAdmin.getId());
		LoanProfile loanProfile = loanService.findLoanProfileById(payload.getLoanProfileId());
		if (loanProfile == null) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Loan profile not found",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
		}
		if (loanProfile.isApproved() == true) {
			SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "This loan profile has already been approved and cannot be rejected again", System.currentTimeMillis());
			return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
		}
		if (loanProfile.isRejected() == true) {
			SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "This loan profile has already been rejected", System.currentTimeMillis());
			return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
		}
		String role = "";
		for (Role r: admin.getRoles()) {
			role = r.getName();
		}
		if (role.equals("ROLE_TRANSACTIONMANAGER") == true) {
			if (loanProfile.getTransactionOffice().getId() != admin.getTransactionOffice().getId()) {
				ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "You don't have this permission",
	    				System.currentTimeMillis());
	    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.UNAUTHORIZED);
			}
			loanService.rejectLoanProffile(loanProfile.getId(), payload.getRejectedReason());
			SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "REJECTED BY TRANSACTION MANAGER", System.currentTimeMillis());
			notificationService.saveNotification(
					"Your loan profile has been rejected." +
					" Please check your loan profile for more information", loanProfile.getUser());
			return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
		} else if (role.equals("ROLE_BRANCHMANAGER") == true) {
			if (loanProfile.getTransactionOffice().getBranchOffice().getId() != admin.getBranchOffice().getId()) {
				ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "You don't have this permission",
	    				System.currentTimeMillis());
	    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.UNAUTHORIZED);
			}
			loanService.rejectLoanProffile(loanProfile.getId(), payload.getRejectedReason());
			SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "REJECTED", System.currentTimeMillis());
			notificationService.saveNotification(
					"Your loan profile has been rejected." +
					" Please check your loan profile for more information", loanProfile.getUser());
			return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
		}
		return null;
	}
}
