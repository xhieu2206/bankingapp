package fpt.banking.system.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.model.TransactionOffice;
import fpt.banking.system.model.User;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.TransactionOfficeService;
import fpt.banking.system.service.UserService;

@RestController
@RequestMapping("/api")
public class AdminTransactionOfficeController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private TransactionOfficeService transactionOfficeService;

	@GetMapping("/admin/branch-manager/transaction-offices")
	public List<TransactionOffice> getTransactionOfficesForBranchManager(
			@AuthenticationPrincipal UserPrincipal user) {
		User branchOfficeManager = userService.getUser(user.getId());
		return transactionOfficeService.findTransactionOfficesByBranchOfficeId(branchOfficeManager.getBranchOffice().getId());
	}
}
