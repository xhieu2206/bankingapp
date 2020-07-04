package fpt.banking.system.service;

import java.util.List;

import fpt.banking.system.model.Account;
import fpt.banking.system.model.Asset;
import fpt.banking.system.model.LoanInterestRate;
import fpt.banking.system.model.LoanProfile;
import fpt.banking.system.model.TransactionOffice;
import fpt.banking.system.model.User;

public interface LoanService {
	
	public LoanProfile findLoanProfileById(long loanProfileId);
	
	public Asset findAssetById(long assetId);

	public long saveLoanProfile(long amount, String description, Account account, LoanInterestRate loanInterestRate, User user, TransactionOffice transactionOffice);
	
	public LoanInterestRate findLoanInterestRateById(long id);
	
	public List<LoanInterestRate> getAllLoanInterestRate();
	
	public long saveAsset(String name, String description, long price, LoanProfile loanProfile);
	
	public List<LoanProfile> findLoanProfilesByUser(User user);
	
	public void saveImagesAsset(String url, Asset asset);
}
