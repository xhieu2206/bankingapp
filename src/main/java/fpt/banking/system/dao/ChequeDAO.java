package fpt.banking.system.dao;

import java.util.List;

import fpt.banking.system.model.Account;
import fpt.banking.system.model.Cheque;
import fpt.banking.system.model.User;

public interface ChequeDAO {

	public List<Cheque> getCheques(long accountId);
	
	public Cheque getChequeById(long chequeId);
	
	public void saveCheque(Account account, String recieverFullname, String recieverIdCardNumber, long transactionAmount);
	
	public void cancelCheque(long chequeId);
	
	public List<Cheque> findChequesWhenDeposit(String recieverFullName, String recieverIdCardNumber);
	
	public void updateCheque(long chequeId, String recieverFullname, String recieverIdCardNumber, long transactionAmount);
	
	public void withdrawCheque(long chequeId, User employee);
	
	public List<Cheque> getChequesForAdmin(long page, String status);
	
	public long getTotalChequesForAdmin(String status);
}
