package fpt.banking.system.service;

import java.util.List;

import fpt.banking.system.model.TransactionOffice;

public interface TransactionOfficeService {

	public TransactionOffice findTransactionOfficeById(long id);
	
	public List<TransactionOffice> findTransactionOfficesByBranchOfficeId(long branchOfficeId);
}
