package fpt.banking.system.dao;

import java.util.List;

import fpt.banking.system.model.Account;
import fpt.banking.system.model.Cheque;

public interface ChequeDAO {

	public List<Cheque> getCheques(long accountId);
	
	public Cheque getChequeById(long chequeId);
	
	public void saveCheque(Account account, String recieverFullname, String recieverIdCardNumber, long transactionAmount);
}
