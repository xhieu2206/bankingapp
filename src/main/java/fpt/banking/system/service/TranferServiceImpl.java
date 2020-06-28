package fpt.banking.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fpt.banking.system.dao.AccountDAO;
import fpt.banking.system.dao.TransactionDAO;
import fpt.banking.system.model.Account;

@Service
public class TranferServiceImpl implements TranferService {
	
	@Autowired
	private TransactionDAO transactionDAO;
	
	@Autowired
	private AccountDAO accountDAO;

	@Override
	@Transactional
	public void tranferInternalByAccountNumber(long tranferAccountId, long receiveAccountId, long amount,
			String description) {
		Account tranferAccount = accountDAO.getAccount(tranferAccountId);
		Account receiveAccount = accountDAO.getAccount(receiveAccountId);
		transactionDAO.saveTransaction(tranferAccountId, amount * (-1), tranferAccount.getAmount() - amount, 1, description);
		transactionDAO.saveTransaction(receiveAccountId, amount, receiveAccount.getAmount() + amount, 3, description);
		accountDAO.changeAmount(tranferAccountId, tranferAccount.getAmount() - amount);
		accountDAO.changeAmount(receiveAccountId, receiveAccount.getAmount() + amount);
	}

}
