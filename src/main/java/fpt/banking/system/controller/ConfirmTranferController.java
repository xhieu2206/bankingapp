package fpt.banking.system.controller;

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
import fpt.banking.system.payload.ConfirmTranferPayload;
import fpt.banking.system.response.SuccessfulResponse;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.TranferService;
import fpt.banking.system.util.MD5;

@RestController
@RequestMapping("/api/tranfer")
public class ConfirmTranferController {

	@Autowired
	private TranferService tranferService;
	
	@PostMapping("/confirm")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> confirm(
			@RequestBody ConfirmTranferPayload confirmTranferPayload
			, @AuthenticationPrincipal UserPrincipal user) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis() + 300000);
		if (tranferService.findTransactionQueueById(confirmTranferPayload.getTransactionQueueId()).getExpriedAt().getTime() < timestamp.getTime()) {
			throw new ExpriedOTP("Your OTP Code have been expried");
		}
		if (!tranferService.findTransactionQueueById(confirmTranferPayload.getTransactionQueueId()).getOtpCode().equals(MD5.getMd5(confirmTranferPayload.getOtpCode()))) {
			throw new WrongOTPCode("Your OTP code you input was wrong");
		}
		if (tranferService.findTransactionQueueById(confirmTranferPayload.getTransactionQueueId()) == null) {
			throw new AccountNotFound("Transaction not found");
		}
		TransactionQueueInternal transactionQueueInternal = tranferService.findTransactionQueueById(confirmTranferPayload.getTransactionQueueId());
		tranferService.tranferInternal(
				transactionQueueInternal.getTranferAccountId(),
				transactionQueueInternal.getReceiverAccountId(),
				transactionQueueInternal.getAmount(),
				transactionQueueInternal.getDescription());
		tranferService.deleteTransactionQueueInternal(confirmTranferPayload.getTransactionQueueId());
		SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "Tranfer successfully", System.currentTimeMillis());
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
}
