package fpt.banking.system.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fpt.banking.system.dao.ChequeDAO;
import fpt.banking.system.model.Account;
import fpt.banking.system.model.Cheque;
import fpt.banking.system.model.User;
import fpt.banking.system.payload.ChequeForAdmin;
import fpt.banking.system.payload.ChequeForUserResponse;
import fpt.banking.system.payload.ChequesResponsePayload;

@Service
public class ChequeServiceImpl implements ChequeService {

	@Autowired
	private ChequeDAO chequeDAO;
	
	@Override
	@Transactional
	public List<ChequeForUserResponse> getCheques(long accountId) {
		List<Cheque> cheques = chequeDAO.getCheques(accountId);
		List<ChequeForUserResponse> items = new ArrayList<ChequeForUserResponse>();
		for (Cheque cheque : cheques) {
			ChequeForUserResponse item = new ChequeForUserResponse();
			item.setCheque(cheque);
			if (cheque.getWithdrawBy() != null) {
				item.setWithdrawEmployeeFullName(cheque.getWithdrawBy().getFullname());
				item.setWithdrawAt(cheque.getWithdrawBy().getTransactionOffice().getName());
			}
			items.add(item);
		}
		return items;
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
	public void withdrawCheque(long chequeId, User employee) {
		chequeDAO.withdrawCheque(chequeId, employee);
	}

	@Override
	@Transactional
	public ChequesResponsePayload getChequesForAdmin(long page, String status) {
		ChequesResponsePayload results = new ChequesResponsePayload();
		results.setPageNumber(page);
		results.setTotalCount(chequeDAO.getTotalChequesForAdmin(status));
		long totalPage = (long) Math.ceil(results.getTotalCount() / 5);
		if (results.getTotalCount() % 5 > 0) {
			totalPage ++;
		}
		results.setTotalPage(totalPage);
		List<ChequeForAdmin> items = new ArrayList<ChequeForAdmin>();
		for (Cheque cheque : chequeDAO.getChequesForAdmin(page, status)) {
			ChequeForAdmin item = new ChequeForAdmin();
			item.setCheque(cheque);
			item.setFullName(cheque.getAccount().getUser().getFullname());
			item.setIdCardNumber(cheque.getAccount().getUser().getIdCardNumber());
			if (cheque.getWithdrawBy() != null) {
				item.setWithdrawEmployeeFullName(cheque.getWithdrawBy().getFullname());
				item.setWithdrawAt(cheque.getWithdrawBy().getTransactionOffice().getName());
			}
			items.add(item);
		}
		results.setItems(items);
		results.setPageSize(results.getItems().size());
		return results;
	}
}
