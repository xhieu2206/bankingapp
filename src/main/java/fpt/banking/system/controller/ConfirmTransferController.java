package fpt.banking.system.controller;

import java.io.IOException;
import java.sql.Timestamp;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.constants.TimerConstants;
import fpt.banking.system.exception.AccountNotFound;
import fpt.banking.system.exception.ExpriedOTP;
import fpt.banking.system.exception.WrongOTPCode;
import fpt.banking.system.model.TransactionQueueInternal;
import fpt.banking.system.model.User;
import fpt.banking.system.payload.ConfirmTransferPayload;
import fpt.banking.system.response.SuccessfulResponse;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.AccountService;
import fpt.banking.system.service.NotificationService;
import fpt.banking.system.service.TransferService;
import fpt.banking.system.util.MD5;
import fpt.banking.system.util.SendEmail;

@RestController
@RequestMapping("/api/transfer")
public class ConfirmTransferController {
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TransferService transferService;
	
	@PostMapping("/confirm")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> confirm(
			@RequestBody ConfirmTransferPayload confirmTransferPayload
			, @AuthenticationPrincipal UserPrincipal user) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis() + 300000);
		if (transferService.findTransactionQueueById(confirmTransferPayload.getTransactionQueueId()).getExpriedAt().getTime() < timestamp.getTime()) {
			throw new ExpriedOTP("Your OTP Code have been expried");
		}
		if (!transferService.findTransactionQueueById(confirmTransferPayload.getTransactionQueueId()).getOtpCode().equals(MD5.getMd5(confirmTransferPayload.getOtpCode()))) {
			throw new WrongOTPCode("Your OTP code you input was wrong");
		}
		if (transferService.findTransactionQueueById(confirmTransferPayload.getTransactionQueueId()) == null) {
			throw new AccountNotFound("Transaction not found");
		}
		TransactionQueueInternal transactionQueueInternal = transferService.findTransactionQueueById(confirmTransferPayload.getTransactionQueueId());
		transferService.transferInternal(
				transactionQueueInternal.getTransferAccountId(),
				transactionQueueInternal.getReceiverAccountId(),
				transactionQueueInternal.getAmount(),
				transactionQueueInternal.getDescription());
		User transferUser = accountService.getAccount(transactionQueueInternal.getTransferAccountId()).getUser();
		User reveiverUser = accountService.getAccount(transactionQueueInternal.getReceiverAccountId()).getUser();

		transferService.deleteTransactionQueueInternal(confirmTransferPayload.getTransactionQueueId());

		notificationService.saveNotification(
				"Ban da chuyen " + transactionQueueInternal.getAmount() + "VND den tai khoan voi so tai khoan la " +
				accountService.getAccount(transactionQueueInternal.getReceiverAccountId()).getAccountNumber(), transferUser);

		try {
			SendEmail.sendEmail(
					transferUser.getEmail(),
					"Ban da chuyen " + transactionQueueInternal.getAmount() + "VND den tai khoan voi so tai khoan la " +
					accountService.getAccount(transactionQueueInternal.getReceiverAccountId()).getAccountNumber());
		} catch (IOException e) {
			System.out.println("Couldn't send email");
		}

		notificationService.saveNotification(
				"Ban da nhan dc " + transactionQueueInternal.getAmount() + "VND tu tai khoan voi so tai khoan la " +
				accountService.getAccount(transactionQueueInternal.getTransferAccountId()).getAccountNumber(), reveiverUser);

		try {
			SendEmail.sendEmail(
					reveiverUser.getEmail(),
					"Ban da nhan dc " + transactionQueueInternal.getAmount() + "VND tu tai khoan voi so tai khoan la " +
					accountService.getAccount(transactionQueueInternal.getTransferAccountId()).getAccountNumber());
		} catch (IOException e) {
			System.out.println("Couldn't send email");
		}

		SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "Transfer successfully", System.currentTimeMillis());
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
}
