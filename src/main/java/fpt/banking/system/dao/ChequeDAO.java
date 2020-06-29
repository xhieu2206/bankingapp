package fpt.banking.system.dao;

import java.util.List;

import fpt.banking.system.model.Cheque;

public interface ChequeDAO {

	public List<Cheque> getCheques(long accountId);
}
