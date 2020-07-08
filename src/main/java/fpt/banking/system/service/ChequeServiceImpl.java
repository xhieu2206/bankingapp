package fpt.banking.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fpt.banking.system.dao.ChequeDAO;
import fpt.banking.system.model.Account;
import fpt.banking.system.model.Cheque;

@Service
public class ChequeServiceImpl implements ChequeService {

	@Autowired
	private ChequeDAO chequeDAO;
	
	@Override
	@Transactional
	public List<Cheque> getCheques(long accountId) {
		return chequeDAO.getCheques(accountId);
	}

	@Override
	@Transactional
	public Cheque getChequeById(long chequeId) {
		return chequeDAO.getChequeById(chequeId);
	}

	@Override
	@Transactional
	public void saveCheque(Account account, String recieverFullname, String recieverIdCardNumber,
			long transactionAmount) {
		chequeDAO.saveCheque(account, recieverFullname, recieverIdCardNumber, transactionAmount);
	}

	@Override
	@Transactional
	public void cancelCheque(long chequeId) {
		chequeDAO.cancelCheque(chequeId);
	}

	@Override
	@Transactional
	public List<Cheque> findChequesWhenDeposit(String recieverFullName, String recieverIdCardNumber) {
		return chequeDAO.findChequesWhenDeposit(recieverFullName, recieverIdCardNumber);
	}

	@Override
	@Transactional
	public void updateCheque(long chequeId, String recieverFullname, String recieverIdCardNumber,
			long transactionAmount) {
		chequeDAO.updateCheque(chequeId, recieverFullname, recieverIdCardNumber, transactionAmount);
	}

	@Override
	@Transactional
	public void depositCheque(long chequeId) {
		chequeDAO.depositCheque(chequeId);
	}
}
