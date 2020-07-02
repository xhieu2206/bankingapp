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
import fpt.banking.system.payload.TranferInternalPayloadByAccountNumber;
import fpt.banking.system.payload.TranferInternalPayloadByCardNumber;
import fpt.banking.system.response.SuccessfulResponse;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.AccountService;
import fpt.banking.system.service.TranferService;
import fpt.banking.system.util.MD5;

@RestController
@RequestMapping("/api/users")
public class TranferController {
	
	@Autowired
	private TranferService tranferService;
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/{userId}/accounts/{accountId}/tranferInternal/accountNumber")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> tranferInternalByAccountNumber(@PathVariable int userId,
			@PathVariable int accountId, @RequestBody TranferInternalPayloadByAccountNumber
			tranferInternalPayloadByAccountNumber, @AuthenticationPrincipal UserPrincipal user) {
		if (accountService.getAccount(accountId).getUser().getId() != user.getId()) {
			throw new AuthorizedException("You don't have permission to access this resource");
		}
		if (tranferInternalPayloadByAccountNumber.getAmount() > accountService.getAccount(accountId).getAmount() - 50000) {
			throw new NotEnoughMoneyException("You don't have enough money to tranfer, you must have at least 50000vnd after transaction");
		}
		if (accountService.findByAccountNumber(tranferInternalPayloadByAccountNumber.getAccountNumber()) == null ||
			!accountService.findByAccountNumber(tranferInternalPayloadByAccountNumber.getAccountNumber()).getUser().getFullname().equals(tranferInternalPayloadByAccountNumber.getFullName())) {
			throw new AccountNotFound("Account Not Found");
		}
		String pinCode = MD5.getMd5(tranferInternalPayloadByAccountNumber.getPin());
		if (!accountService.getAccount(accountId).getPinCode().equals(pinCode)) {
			throw new PinCodeIncorrectedException("Your pin is incorrected");
		}
		if (accountService.getAccount(accountId).isStatus() == false) {
			throw new AccountIsNotActive("Your account you want to send money to is locked, please contact the owner instead");
		}
		if (tranferInternalPayloadByAccountNumber.getDescription() == null || tranferInternalPayloadByAccountNumber.getDescription().equals("")) {
			throw new NullDescriptionException("Message cannot be empty");
		}
		if (accountService.getAccount(Long.valueOf(accountId)).isOtpTranferEnabled()) {
			String id = tranferService.saveTransactionQueueInternal(
					accountId, 
					accountService.findByAccountNumber(tranferInternalPayloadByAccountNumber.getAccountNumber()).getId(), 
					tranferInternalPayloadByAccountNumber.getAmount(),
					tranferInternalPayloadByAccountNumber.getDescription());
			SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), id.toString(), System.currentTimeMillis());
			return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
		}
		tranferService.tranferInternal(
				accountId,
				accountService.findByAccountNumber(tranferInternalPayloadByAccountNumber.getAccountNumber()).getId(),
				tranferInternalPayloadByAccountNumber.getAmount(),
				tranferInternalPayloadByAccountNumber.getDescription());
		SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "Tranfer successfully", System.currentTimeMillis());
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
	
	@PostMapping("/{userId}/accounts/{accountId}/tranferInternal/cardNumber")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> tranferInternalByAccountNumber(@PathVariable int userId,
			@PathVariable int accountId, @RequestBody TranferInternalPayloadByCardNumber
			tranferInternalPayloadByCardNumber, @AuthenticationPrincipal UserPrincipal user) {
		if (accountService.getAccount(accountId).getUser().getId() != user.getId()) {
			throw new AuthorizedException("You don't have permission to access this resource");
		}
		if (tranferInternalPayloadByCardNumber.getAmount() > accountService.getAccount(accountId).getAmount() - 50000) {
			throw new NotEnoughMoneyException("You don't have enough money to tranfer, you must have at least 50000vnd after transaction");
		}
		if (accountService.findByCardNumber(tranferInternalPayloadByCardNumber.getCardNumber()) == null ||
			!accountService.findByCardNumber(tranferInternalPayloadByCardNumber.getCardNumber()).getUser().getFullname().equals(tranferInternalPayloadByCardNumber.getFullName())) {
			throw new AccountNotFound("Account Not Found");
		}
		String pinCode = MD5.getMd5(tranferInternalPayloadByCardNumber.getPin());
		if (!accountService.getAccount(accountId).getPinCode().equals(pinCode)) {
			throw new PinCodeIncorrectedException("Your pin is incorrected");
		}
		if (accountService.getAccount(accountId).isStatus() == false) {
			throw new AccountIsNotActive("Your account you want to send money to is locked, please contact the owner instead");
		}
		if (tranferInternalPayloadByCardNumber.getDescription() == null || tranferInternalPayloadByCardNumber.getDescription().equals("")) {
			throw new NullDescriptionException("Message cannot be empty");
		}
		if (accountService.getAccount(Long.valueOf(accountId)).isOtpTranferEnabled()) {
			String id = tranferService.saveTransactionQueueInternal(
					accountId, 
					accountService.findByCardNumber(tranferInternalPayloadByCardNumber.getCardNumber()).getId(), 
					tranferInternalPayloadByCardNumber.getAmount(),
					tranferInternalPayloadByCardNumber.getDescription());
			SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), id.toString(), System.currentTimeMillis());
			return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
		}
		tranferService.tranferInternal(
				accountId,
				accountService.findByCardNumber(tranferInternalPayloadByCardNumber.getCardNumber()).getId(),
				tranferInternalPayloadByCardNumber.getAmount(),
				tranferInternalPayloadByCardNumber.getDescription());
		SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "Tranfer successfully", System.currentTimeMillis());
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
}
