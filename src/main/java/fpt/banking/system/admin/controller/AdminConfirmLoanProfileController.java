package fpt.banking.system.admin.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.exception.AccountNotFound;
import fpt.banking.system.exception.ExpriedOTP;
import fpt.banking.system.exception.WrongOTPCode;
import fpt.banking.system.model.LoanProfileQueue;
import fpt.banking.system.model.User;
import fpt.banking.system.payload.ConfirmLoanProfilePayload;
import fpt.banking.system.payload.LoanProfileRequestPayload;
import fpt.banking.system.response.SuccessfulResponse;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.LoanService;
import fpt.banking.system.service.NotificationService;
import fpt.banking.system.util.MD5;

@RestController
@RequestMapping("api/admin/loanprofile")
public class AdminConfirmLoanProfileController {
	@Autowired
	private NotificationService notificationService;

	@Autowired
	private LoanService loanService;
	
	@PostMapping("/confirm")
	@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> confirm(
			@RequestBody ConfirmLoanProfilePayload payload,
			@AuthenticationPrincipal UserPrincipal currentEmp
			) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis() + 300000);
		LoanProfileQueue loanProfileQueue = loanService.findLoanProfileQueueByLoanProfileId(payload.getLoanProfileId());
		if (loanService.findLoanProfileQueueByLoanProfileId(payload.getLoanProfileId()).getExpriedAt().getTime() < timestamp.getTime()) {
			throw new ExpriedOTP("Your OTP Code have been expried");
		}
		if (!loanService.findLoanProfileQueueByLoanProfileId(payload.getLoanProfileId()).getOtpCode().equals(MD5.getMd5(payload.getOtpCode()))) {
			throw new WrongOTPCode("Your OTP code you input was wrong");
		}
		if (loanService.findLoanProfileQueueByLoanProfileId(payload.getLoanProfileId()) == null) {
			throw new AccountNotFound("Loan profile not found");
		}
		loanService.confirmLoanProfile(payload.getLoanProfileId());
		User user = loanService.findLoanProfileById(payload.getLoanProfileId()).getUser();
		notificationService.saveNotification("Your Loan profile has been confirmed", user);
		loanService.deleteLoanProfileQueue(loanService.findLoanProfileQueueByLoanProfileId(payload.getLoanProfileId()).getId());
		SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "This loan profile has been confirmed", System.currentTimeMillis());
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
}
