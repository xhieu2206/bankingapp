package fpt.banking.system.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twilio.exception.ApiException;

import fpt.banking.system.constants.TimerConstants;
import fpt.banking.system.dao.AssetDAO;
import fpt.banking.system.dao.ImagesAssetDAO;
import fpt.banking.system.dao.LoanInterestRateDAO;
import fpt.banking.system.dao.LoanProfileDAO;
import fpt.banking.system.dao.LoanProfileQueueDAO;
import fpt.banking.system.model.Account;
import fpt.banking.system.model.Asset;
import fpt.banking.system.model.LoanInterestRate;
import fpt.banking.system.model.LoanProfile;
import fpt.banking.system.model.LoanProfileQueue;
import fpt.banking.system.model.TransactionOffice;
import fpt.banking.system.model.User;
import fpt.banking.system.payload.LoanProfilesResponsePayload;
import fpt.banking.system.util.MD5;
import fpt.banking.system.util.MobilePhoneUtil;
import fpt.banking.system.util.RandomGenerator;
import fpt.banking.system.util.SendEmail;
import fpt.banking.system.util.SendSms;
import fpt.banking.system.util.SendSmsWithLib;

@Service
public class LoanServiceImpl implements LoanService {
	@Autowired
	private LoanProfileQueueDAO loanProfileQueueDAO;

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
	@Transactional
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

	@Override
	@Transactional
	public LoanProfileQueue findLoanProfileQueueById(long id) {
		return loanProfileQueueDAO.findById(id);
	}

	@Override
	@Transactional
	public long saveLoanProfileQueue(long loanProfileId) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis() + 300000 + TimerConstants.VIETNAM_TIMEZONE_TIMESTAMP);
		String otpCode = RandomGenerator.generateOTP();
		String phone = loanProfileDAO.findById(loanProfileId).getUser().getPhone();
		String email = loanProfileDAO.findById(loanProfileId).getUser().getEmail();
//		SendSmsWithLib.sendSms(MobilePhoneUtil.convertPhone(phone, "+84"), "OTP: " + otpCode);
		try {
			SendSms.sendSms(MobilePhoneUtil.convertPhone(phone, "+84"), "OTP: " + otpCode);
		} catch (Exception e) {
			System.out.println("Phone was not verified");
			try {
				SendEmail.sendEmail(email, "OTP: " + otpCode);
			} catch (IOException e1) {
				System.out.println("Could not send email");
			}
		}
		long id = loanProfileQueueDAO.saveLoanProfileQueue(MD5.getMd5(otpCode), loanProfileId, new Date(timestamp.getTime()));
		return id;
	}

	@Override
	@Transactional
	public void confirmLoanProfile(long loanProfileId, String employeeConfirmedName, long employeeConfirmedId) {
		loanProfileDAO.confirmLoanProfile(loanProfileId, employeeConfirmedName, employeeConfirmedId);
	}

	@Override
	@Transactional
	public void deleteLoanProfileQueue(long loanProfileQueueId) {
		loanProfileQueueDAO.deleteLoanProfileQueue(loanProfileQueueId);
	}

	@Override
	@Transactional
	public long approvedLoanProfileByTransactionManager(long loanProfileId) {
		return loanProfileDAO.approvedLoanProfileByTransactionManager(loanProfileId);
	}

	@Override
	@Transactional
	public long approvedLoanProfileByBranchManager(long loanProfileId) {
		return loanProfileDAO.approvedLoanProfileByBranchManager(loanProfileId);
	}

	@Override
	@Transactional
	public void rejectLoanProffile(long loanProfileId, String rejectedReason) {
		loanProfileDAO.rejectLoanProffile(loanProfileId, rejectedReason);
	}

	@Override
	@Transactional
	public long getTotalLoanProfilesOfTransactionOffice(long transactionOfficeId) {
		return 0;
	}

	@Override
	@Transactional
	public long getTotalLoanProfilesOfBranchOffice(long branchOfficeId) {
		return 0;
	}

	@Override
	@Transactional
	public LoanProfilesResponsePayload getLoanProfilesOfTransactionOffice(long transactionOfficeId, int page, String status) {
		LoanProfilesResponsePayload results = new LoanProfilesResponsePayload();
		results.setPageNumber(page);
		results.setTotalCount(loanProfileDAO.getTotalLoanProfilesByTransactionOffice(transactionOfficeId, status));
		int totalPage = (int) Math.ceil(loanProfileDAO.getTotalLoanProfilesByTransactionOffice(transactionOfficeId, status) / 5);
		if (loanProfileDAO.getTotalLoanProfilesByTransactionOffice(transactionOfficeId, status) % 5 > 0) {
			totalPage ++;
		}
		results.setTotalPage(totalPage);
		results.setItems(loanProfileDAO.getLoanProfilesByTransactionOffice(transactionOfficeId, page, status));
		results.setPageSize(results.getItems().size());
		return results;
	}

	@Override
	@Transactional
	public LoanProfilesResponsePayload getLoanProfilesOfBranchOffice(long transactionOfficeId, int page) {
		LoanProfilesResponsePayload results = new LoanProfilesResponsePayload();
		return results;
	}

	@Override
	@Transactional
	public LoanProfileQueue findLoanProfileQueueByLoanProfileId(long loanProfileId) {
		return loanProfileQueueDAO.findLoanProfileQueueByLoanProfileId(loanProfileId);
	}

	@Override
	@Transactional
	public LoanProfilesResponsePayload getLoanProfilesForEmployee(User employee, int page) {
		LoanProfilesResponsePayload results = new LoanProfilesResponsePayload();
		results.setPageNumber(page);
		results.setTotalCount(loanProfileDAO.getTotalLoanProfilesForAnEmployee(employee));
		int totalPage = (int) Math.ceil(results.getTotalCount() / 5);
		if (results.getTotalCount() % 5 > 0) {
			totalPage ++;
		}
		results.setTotalPage(totalPage);
		results.setItems(loanProfileDAO.getLoanProfilesForAnEmployee(employee, page));
		results.setPageSize(results.getItems().size());
		return results;
	}

	@Override
	@Transactional
	public void removeAsset(long assetId) {
		assetDAO.removeAsset(assetId);
	}
}
