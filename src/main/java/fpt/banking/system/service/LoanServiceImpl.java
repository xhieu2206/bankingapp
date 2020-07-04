package fpt.banking.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fpt.banking.system.dao.AssetDAO;
import fpt.banking.system.dao.ImagesAssetDAO;
import fpt.banking.system.dao.LoanInterestRateDAO;
import fpt.banking.system.dao.LoanProfileDAO;
import fpt.banking.system.model.Account;
import fpt.banking.system.model.Asset;
import fpt.banking.system.model.LoanInterestRate;
import fpt.banking.system.model.LoanProfile;
import fpt.banking.system.model.TransactionOffice;
import fpt.banking.system.model.User;

@Service
public class LoanServiceImpl implements LoanService {
	
	@Autowired
	private LoanProfileDAO loanProfileDAO;
	
	@Autowired
	private LoanInterestRateDAO loanInterestRateDAO;
	
	@Autowired
	private AssetDAO assetDAO;
	
	@Autowired
	private ImagesAssetDAO imagesAssetDAO;
	
	@Override
	@Transactional
	public LoanProfile findLoanProfileById(long loanProfileId) {
		return loanProfileDAO.findById(loanProfileId);
	}
	
	@Override
	public Asset findAssetById(long assetId) {
		return assetDAO.findById(assetId);
	}
	
	@Override
	@Transactional
	public long saveLoanProfile(long amount, String description, Account account, LoanInterestRate loanInterestRate,
			User user, TransactionOffice transactionOffice) {
		return loanProfileDAO.saveLoanProfile(amount, description, account, loanInterestRate, user, transactionOffice);
	}

	@Override
	@Transactional
	public LoanInterestRate findLoanInterestRateById(long id) {
		return loanInterestRateDAO.findById(id);
	}

	@Override
	@Transactional
	public List<LoanInterestRate> getAllLoanInterestRate() {
		return loanInterestRateDAO.getAllLoanInterestRate();
	}

	@Override
	@Transactional
	public long saveAsset(String name, String description, long price, LoanProfile loanProfile) {
		return assetDAO.saveAsset(name, description, price, loanProfile);
	}

	@Override
	@Transactional
	public List<LoanProfile> findLoanProfilesByUser(User user) {
		return loanProfileDAO.findLoanProfilesByUser(user);
	}

	@Override
	@Transactional
	public void saveImagesAsset(String url, Asset asset) {
		imagesAssetDAO.saveImagesAsset(url, asset);
	}
}
