package fpt.banking.system.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.model.Account;
import fpt.banking.system.model.Cheque;
import fpt.banking.system.model.User;
import fpt.banking.system.payload.DepositChequeRequestPayload;
import fpt.banking.system.response.SuccessfulResponse;
import fpt.banking.system.service.AccountService;
import fpt.banking.system.service.ChequeService;
import fpt.banking.system.service.NotificationService;
import fpt.banking.system.service.TransactionService;
import fpt.banking.system.service.UserService;

@RestController
@RequestMapping("/api")
public class AdminChequeController {

	@Autowired
	private ChequeService chequeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private NotificationService notificationService;
	
	@PostMapping("/admin/deposit/cheque")
	@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> depositCheque(
			@RequestBody DepositChequeRequestPayload payload) {
		List<Cheque> cheques = chequeService.findChequesWhenDeposit(payload.getRecieverFullName(), payload.getRecieverIdCardNumber());
		if (cheques.size() == 0) {
			SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "You doesn't have any cheques in our system", System.currentTimeMillis());
			return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
		}
		long totalAmount = 0;
		for (Cheque cheque : cheques) {
			Account account = cheque.getAccount();
			User user = account.getUser();
			long fees = 0;
			if (user.getMembership().getName().equals("GOLD")) {
				fees = 10000;
			} else if (user.getMembership().getName().equals("PLATINUM")) {
				fees = 5000;
			}
			if (account.getAmount() > fees + cheque.getTransactionAmount()) {
				System.out.println("ABC");
				totalAmount = totalAmount + cheque.getTransactionAmount();
				chequeService.depositCheque(cheque.getId());
				transactionService.saveTransaction(
						account.getId(),
						-1 * (cheque.getTransactionAmount() + fees),
						account.getAmount() - fees - cheque.getTransactionAmount(), 
						7, 
						cheque.getRecieverFullname() + " has deposited a cheque from your account");
				accountService.changeAmount(account.getId(), account.getAmount() - fees - cheque.getTransactionAmount());
				notificationService.saveNotification(
						cheque.getRecieverFullname() + " has deposited a cheque from your account with number is " + account.getAccountNumber(),
						user);
			}
		}
		if (totalAmount == 0) {
			SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "You doesn't have any cheques in our system", System.currentTimeMillis());
			return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
		} else {
			SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "You has deposit " + totalAmount + " from your cheques", System.currentTimeMillis());
			return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
		}
	}
	
}

