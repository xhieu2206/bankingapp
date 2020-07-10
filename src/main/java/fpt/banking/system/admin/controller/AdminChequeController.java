package fpt.banking.system.admin.controller;

import java.io.IOException;
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

import fpt.banking.system.exception.ChequesNotFound;
import fpt.banking.system.exception.NotEnoughMoneyException;
import fpt.banking.system.model.Account;
import fpt.banking.system.model.Cheque;
import fpt.banking.system.model.User;
import fpt.banking.system.payload.ChequeIdRequestPayload;
import fpt.banking.system.payload.ChequesResponsePayload;
import fpt.banking.system.payload.DepositChequeRequestPayload;
import fpt.banking.system.response.SuccessfulResponse;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.AccountService;
import fpt.banking.system.service.ChequeService;
import fpt.banking.system.service.NotificationService;
import fpt.banking.system.service.TransactionService;
import fpt.banking.system.service.UserService;
import fpt.banking.system.util.SendEmail;

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
	
	@PostMapping("/admin/employee/cheques")
	@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	public List<Cheque> getChequesForReceiver(
			@RequestBody DepositChequeRequestPayload payload) {
		List<Cheque> cheques = chequeService.findChequesWhenDeposit(payload.getRecieverFullName().trim().toUpperCase(), payload.getRecieverIdCardNumber());
		if (cheques.size() == 0) {
			throw new ChequesNotFound("You don't have any cheques in our system");
		}
		return cheques;
	}
	
	@PostMapping("/admin/cheques/withdraw")
	@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> depositCheque(
			@RequestBody ChequeIdRequestPayload payload,
			@AuthenticationPrincipal UserPrincipal currentEmployee
			) {
		User employee = userService.getUser(currentEmployee.getId());
		Cheque cheque = chequeService.getChequeById(payload.getChequeId());
		if (cheque == null) {
			throw new ChequesNotFound("This cheque id doesn't existed");
		}
		Account account = cheque.getAccount();
		User user = cheque.getAccount().getUser();
		long fees = 0;
		if (user.getMembership().getName().equals("GOLD")) {
			fees = 10000;
		} else if (user.getMembership().getName().equals("PLATINUM")) {
			fees = 5000;
		}
		if (account.getAmount() > fees + cheque.getTransactionAmount()) {
			chequeService.withdrawCheque(cheque.getId(), employee);

			transactionService.saveTransaction(
					account.getId(),
					-1 * (cheque.getTransactionAmount() + fees),
					account.getAmount() - fees - cheque.getTransactionAmount(), 
					7, 
					cheque.getRecieverFullname() + " has withdrawed a cheque from your account");

			accountService.changeAmount(
					account.getId(),
					account.getAmount() - fees - cheque.getTransactionAmount());

			notificationService.saveNotification(
					cheque.getRecieverFullname() + " has withdrawed a cheque from your account with number is " + account.getAccountNumber(),
					user);
			
			try {
				SendEmail.sendEmail(
						user.getEmail(),
						cheque.getRecieverFullname() + " has withdrawed a cheque from your account with number is " + account.getAccountNumber());
			} catch (IOException e) {
				System.out.println("Couldn't send email");
			}

		} else {
			throw new NotEnoughMoneyException("This account doesn't have enough money for this transaction");
		}
		SuccessfulResponse res = new SuccessfulResponse(
				HttpStatus.OK.value(),
				"Deposit " + cheque.getTransactionAmount() + " for " + cheque.getRecieverFullname(),
				System.currentTimeMillis());
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
	
	@GetMapping("/admin/cheques")
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_TRANSACTIONMANAGER', 'ROLE_BRANCHMANAGER', 'ROLE_BANKMANAGER')")
	public ChequesResponsePayload getChequesForAdmin(
			@RequestParam("page") Optional<Long> page,
			@RequestParam("status") Optional<String> status) {
		long pageNumber = 1;
		String statusCheque = "ALL";
		if (page.isPresent()) {
			pageNumber = page.get();
		}
		if (status.isPresent() && (status.get().equals("WAITING") || status.get().equals("CANCELED") || status.get().equals("WITHDRAWED"))) {
			statusCheque = status.get();
		}
		return chequeService.getChequesForAdmin(pageNumber, statusCheque);
	}
}

