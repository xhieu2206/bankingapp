package fpt.banking.system.service;

import java.util.List;

import fpt.banking.system.model.Cheque;

public interface ChequeService {

	public List<Cheque> getCheques(long accountId);
}
