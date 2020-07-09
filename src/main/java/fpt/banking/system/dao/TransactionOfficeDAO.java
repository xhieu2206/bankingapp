package fpt.banking.system.dao;

import java.util.List;

import fpt.banking.system.model.TransactionOffice;

public interface TransactionOfficeDAO {
	public TransactionOffice findById(long id);
	
	public List<TransactionOffice> findTransactionOfficesOfABranchOffice(long branchOfficeId);
	
	public List<TransactionOffice> listAllTransactionOffices();
}
