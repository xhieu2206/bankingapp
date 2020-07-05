package fpt.banking.system.service;

import java.util.List;

import fpt.banking.system.model.Account;
import fpt.banking.system.model.Asset;
import fpt.banking.system.model.LoanInterestRate;
import fpt.banking.system.model.LoanProfile;
import fpt.banking.system.model.LoanProfileQueue;
import fpt.banking.system.model.TransactionOffice;
import fpt.banking.system.model.User;
import fpt.banking.system.payload.LoanProfilesResponsePayload;

public interface LoanService {
	
	public LoanProfile findLoanProfileById(long loanProfileId);
	
	public Asset findAssetById(long assetId);

	public long saveLoanProfile(long amount, String description, Account account, LoanInterestRate loanInterestRate, User user, TransactionOffice transactionOffice);
	
	public LoanInterestRate findLoanInterestRateById(long id);
	
	public List<LoanInterestRate> getAllLoanInterestRate();
	
	public long saveAsset(String name, String description, long price, LoanProfile loanProfile);
	
	public List<LoanProfile> findLoanProfilesByUser(User user);
	
	public void saveImagesAsset(String url, Asset asset);
	
	public LoanProfileQueue findLoanProfileQueueById(long id);
	
	public long saveLoanProfileQueue(long loanProfileId);
	
	public void confirmLoanProfile(long loanProfileId);
	
	public void deleteLoanProfileQueue(long loanProfileQueueId);
	
	public long approvedLoanProfileByTransactionManager(long loanProfileId);
	
	public long approvedLoanProfileByBranchManager(long loanProfileId);
	
	public void rejectLoanProffile(long loanProfileId, String rejectedReason);
	
	public long getTotalLoanProfilesOfTransactionOffice(long transactionOfficeId);
	
	public long getTotalLoanProfilesOfBranchOffice(long branchOfficeId);
	
	public LoanProfilesResponsePayload getLoanProfilesOfTransactionOffice(long transactionOfficeId, int page);
	
	public LoanProfilesResponsePayload getLoanProfilesOfBranchOffice(long transactionOfficeId, int page);
}
