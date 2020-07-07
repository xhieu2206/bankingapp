package fpt.banking.system.service;

import java.util.List;

import fpt.banking.system.model.Account;
import fpt.banking.system.model.Cheque;

public interface ChequeService {

	public List<Cheque> getCheques(long accountId);
	
	public Cheque getChequeById(long chequeId);
	
	public void saveCheque(Account account, String recieverFullname, String recieverIdCardNumber, long transactionAmount);
	
	public void cancelCheque(long chequeId);
	
	public Cheque findChequeWhenDeposit(String transferFullName, String recieverIdCardNumber, String recieverFullName);
}
