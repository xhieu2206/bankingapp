package fpt.banking.system.service;

import java.util.List;

import fpt.banking.system.model.Account;
import fpt.banking.system.model.Cheque;
import fpt.banking.system.model.User;
import fpt.banking.system.payload.ChequeForUserResponse;
import fpt.banking.system.payload.ChequesResponsePayload;

public interface ChequeService {

	public List<ChequeForUserResponse> getCheques(long accountId);
	
	public Cheque getChequeById(long chequeId);
	
	public void saveCheque(Account account, String recieverFullname, String recieverIdCardNumber, long transactionAmount);
	
	public void cancelCheque(long chequeId);
	
	public List<Cheque> findChequesWhenDeposit(String recieverFullName, String recieverIdCardNumber);
	
	public void updateCheque(long chequeId, String recieverFullname, String recieverIdCardNumber, long transactionAmount);
	
	public void withdrawCheque(long chequeId, User employee);
	
	public ChequesResponsePayload getChequesForAdmin(long page, String status);
}
