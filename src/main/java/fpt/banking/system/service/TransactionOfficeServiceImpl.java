package fpt.banking.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fpt.banking.system.dao.TransactionOfficeDAO;
import fpt.banking.system.model.TransactionOffice;

@Service
public class TransactionOfficeServiceImpl implements TransactionOfficeService {
	
	@Autowired
	private TransactionOfficeDAO transactionOfficeDAO;

	@Override
	@Transactional
	public TransactionOffice findTransactionOfficeById(long id) {
		return transactionOfficeDAO.findById(id);
	}

	@Override
	@Transactional
	public List<TransactionOffice> findTransactionOfficesByBranchOfficeId(long branchOfficeId) {
		return transactionOfficeDAO.findTransactionOfficesOfABranchOffice(branchOfficeId);
	}

}
