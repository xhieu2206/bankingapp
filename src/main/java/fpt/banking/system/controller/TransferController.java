package fpt.banking.system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.exception.AccountIsNotActive;
import fpt.banking.system.exception.AccountNotFound;
import fpt.banking.system.exception.AuthorizedException;
import fpt.banking.system.exception.NotEnoughMoneyException;
import fpt.banking.system.exception.NullDescriptionException;
import fpt.banking.system.exception.PinCodeIncorrectedException;
import fpt.banking.system.model.User;
import fpt.banking.system.payload.TransferInternalPayloadByAccountNumber;
import fpt.banking.system.payload.TransferInternalPayloadByCardNumber;
import fpt.banking.system.response.SuccessfulResponse;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.AccountService;
import fpt.banking.system.service.NotificationService;
import fpt.banking.system.service.TransferService;
import fpt.banking.system.service.UserService;
import fpt.banking.system.util.MD5;

@RestController
@RequestMapping("/api/users")
public class TransferController {
	
	@Autowired
	private TransferService transferService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/{userId}/accounts/{accountId}/transferInternal/accountNumber")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> transferInternalByAccountNumber(@PathVariable int userId,
			@PathVariable int accountId, @RequestBody TransferInternalPayloadByAccountNumber
			transferInternalPayloadByAccountNumber, @AuthenticationPrincipal UserPrincipal user) {
		if (accountService.getAccount(accountId).getUser().getId() != user.getId()) {
			throw new AuthorizedException("You don't have permission to access this resource");
		}
		if (transferInternalPayloadByAccountNumber.getAmount() > accountService.getAccount(accountId).getAmount() - 50000) {
			throw new NotEnoughMoneyException("You don't have enough money to transfer, you must have at least 50000vnd after transaction");
		}
		if (accountService.findByAccountNumber(transferInternalPayloadByAccountNumber.getAccountNumber()) == null ||
			!accountService.findByAccountNumber(transferInternalPayloadByAccountNumber.getAccountNumber()).getUser().getFullname().equals(transferInternalPayloadByAccountNumber.getFullName().trim().toUpperCase())) {
			throw new AccountNotFound("Account Not Found");
		}
		String pinCode = MD5.getMd5(transferInternalPayloadByAccountNumber.getPin());
		if (!accountService.getAccount(accountId).getPinCode().equals(pinCode)) {
			throw new PinCodeIncorrectedException("Your pin is incorrected");
		}
		if (accountService.getAccount(accountId).isStatus() == false) {
			throw new AccountIsNotActive("Your account you want to send money to is locked, please contact the owner instead");
		}
		if (transferInternalPayloadByAccountNumber.getDescription() == null || transferInternalPayloadByAccountNumber.getDescription().equals("")) {
			throw new NullDescriptionException("Message cannot be empty");
		}
		if (accountService.getAccount(Long.valueOf(accountId)).isOtpTransferEnabled()) {
			String id = transferService.saveTransactionQueueInternal(
					accountId, 
					accountService.findByAccountNumber(transferInternalPayloadByAccountNumber.getAccountNumber()).getId(), 
					transferInternalPayloadByAccountNumber.getAmount(),
					transferInternalPayloadByAccountNumber.getDescription());
			SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), id.toString(), System.currentTimeMillis());
			return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
		}
		transferService.transferInternal(
				accountId,
				accountService.findByAccountNumber(transferInternalPayloadByAccountNumber.getAccountNumber()).getId(),
				transferInternalPayloadByAccountNumber.getAmount(),
				transferInternalPayloadByAccountNumber.getDescription());
		User transferUser = accountService.getAccount(accountId).getUser();
		User revieverUser = accountService.findByAccountNumber(transferInternalPayloadByAccountNumber.getAccountNumber()).getUser();
		notificationService.saveNotification(
				"You have transfered " + transferInternalPayloadByAccountNumber.getAmount().toString() + " to account with account number is " +
				transferInternalPayloadByAccountNumber.getAccountNumber(), transferUser);
		notificationService.saveNotification(
				"You have revieved " + transferInternalPayloadByAccountNumber.getAmount().toString() + " from account with account number is " +
				accountService.getAccount(accountId).getAccountNumber(), revieverUser);
		SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "Transfer successfully", System.currentTimeMillis());
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
	
	@PostMapping("/{userId}/accounts/{accountId}/transferInternal/cardNumber")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> transferInternalByAccountNumber(@PathVariable int userId,
			@PathVariable int accountId, @RequestBody TransferInternalPayloadByCardNumber
			transferInternalPayloadByCardNumber, @AuthenticationPrincipal UserPrincipal user) {
		if (accountService.getAccount(accountId).getUser().getId() != user.getId()) {
			throw new AuthorizedException("You don't have permission to access this resource");
		}
		if (transferInternalPayloadByCardNumber.getAmount() > accountService.getAccount(accountId).getAmount() - 50000) {
			throw new NotEnoughMoneyException("You don't have enough money to transfer, you must have at least 50000vnd after transaction");
		}
		if (accountService.findByCardNumber(transferInternalPayloadByCardNumber.getCardNumber()) == null ||
			!accountService.findByCardNumber(transferInternalPayloadByCardNumber.getCardNumber()).getUser().getFullname().equals(transferInternalPayloadByCardNumber.getFullName().trim().toUpperCase())) {
			throw new AccountNotFound("Account Not Found");
		}
		String pinCode = MD5.getMd5(transferInternalPayloadByCardNumber.getPin());
		if (!accountService.getAccount(accountId).getPinCode().equals(pinCode)) {
			throw new PinCodeIncorrectedException("Your pin is incorrected");
		}
		if (accountService.getAccount(accountId).isStatus() == false) {
			throw new AccountIsNotActive("Your account you want to send money to is locked, please contact the owner instead");
		}
		if (transferInternalPayloadByCardNumber.getDescription() == null || transferInternalPayloadByCardNumber.getDescription().equals("")) {
			throw new NullDescriptionException("Message cannot be empty");
		}
		if (accountService.getAccount(Long.valueOf(accountId)).isOtpTransferEnabled()) {
			String id = transferService.saveTransactionQueueInternal(
					accountId, 
					accountService.findByCardNumber(transferInternalPayloadByCardNumber.getCardNumber()).getId(), 
					transferInternalPayloadByCardNumber.getAmount(),
					transferInternalPayloadByCardNumber.getDescription());
			SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), id.toString(), System.currentTimeMillis());
			return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
		}
		transferService.transferInternal(
				accountId,
				accountService.findByCardNumber(transferInternalPayloadByCardNumber.getCardNumber()).getId(),
				transferInternalPayloadByCardNumber.getAmount(),
				transferInternalPayloadByCardNumber.getDescription());
		SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "Transfer successfully", System.currentTimeMillis());
		User transferUser = accountService.getAccount(accountId).getUser();
		User revieverUser = accountService.findByCardNumber(transferInternalPayloadByCardNumber.getCardNumber()).getUser();
		notificationService.saveNotification(
				"You have transfered " + transferInternalPayloadByCardNumber.getAmount().toString() + " to account with card number is " +
						transferInternalPayloadByCardNumber.getCardNumber(), transferUser);
		notificationService.saveNotification(
				"You have revieved " + transferInternalPayloadByCardNumber.getAmount().toString() + " from account with card number is " +
				accountService.getAccount(accountId).getAccountNumber(), revieverUser);
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
}
