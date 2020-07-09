package fpt.banking.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.model.TransactionOffice;
import fpt.banking.system.service.TransactionOfficeService;

@RestController
@RequestMapping("/api")
public class TransactionOfficeController {

	@Autowired
	private TransactionOfficeService transactionOfficeService;

	@GetMapping("/transaction-offices")
	public List<TransactionOffice> getAllTransactionOffices() {
		return transactionOfficeService.listAllTransactionOffices();
	}
}
