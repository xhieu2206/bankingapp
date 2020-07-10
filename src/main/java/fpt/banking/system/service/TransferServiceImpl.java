package fpt.banking.system.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twilio.exception.ApiException;
import com.twilio.rest.proxy.v1.service.PhoneNumberUpdater;

import fpt.banking.system.dao.AccountDAO;
import fpt.banking.system.dao.TransactionDAO;
import fpt.banking.system.dao.TransactionQueueInternalDAO;
import fpt.banking.system.model.Account;
import fpt.banking.system.model.TransactionQueueInternal;
import fpt.banking.system.util.MD5;
import fpt.banking.system.util.MobilePhoneUtil;
import fpt.banking.system.util.RandomGenerator;
import fpt.banking.system.util.SendEmail;
import fpt.banking.system.constants.TimerConstants;
import fpt.banking.system.util.SendSms;
import fpt.banking.system.util.SendSmsWithLib;

@Service
public class TransferServiceImpl implements TransferService {
	
	@Autowired
	private TransactionDAO transactionDAO;
	
	@Autowired
	private AccountDAO accountDAO;
	
	@Autowired
	private TransactionQueueInternalDAO transactionQueueInternalDAO;

	@Override
	@Transactional
	public void transferInternal(long transferAccountId, long receiveAccountId, long amount,
			String description) {
		Account transferAccount = accountDAO.getAccount(transferAccountId);
		Account receiveAccount = accountDAO.getAccount(receiveAccountId);
		transactionDAO.saveTranserTransaction(transferAccountId, amount * (-1), transferAccount.getAmount() - amount, 1, description, receiveAccount.getUser().getFullname(), receiveAccount.getAccountNumber());
		transactionDAO.saveTranserTransaction(receiveAccountId, amount, receiveAccount.getAmount() + amount, 3, description, transferAccount.getUser().getFullname(), transferAccount.getAccountNumber());
		accountDAO.changeAmount(transferAccountId, transferAccount.getAmount() - amount);
		accountDAO.changeAmount(receiveAccountId, receiveAccount.getAmount() + amount);
	}

	@Override
	@Transactional
	public String saveTransactionQueueInternal(long transferAccountId, long receiverAccountId, long amount, String description) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis() + 300000 + TimerConstants.VIETNAM_TIMEZONE_TIMESTAMP);
		String otpCode = RandomGenerator.generateOTP();
		String phone = accountDAO.getAccount(transferAccountId).getUser().getPhone();
		String email = accountDAO.getAccount(transferAccountId).getUser().getEmail();
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
		String id = transactionQueueInternalDAO.saveTransactionQueueInternal(MD5.getMd5(otpCode), transferAccountId, receiverAccountId, amount, new Date(timestamp.getTime()), description);
		return id;
	}

	@Override
	@Transactional
	public void deleteTransactionQueueInternal(long transactionQueueInternalId) {
		transactionQueueInternalDAO.deleteTransactionQueueInternal(transactionQueueInternalId);
	}

	@Override
	@Transactional
	public TransactionQueueInternal findTransactionQueueById(long id) {
		return transactionQueueInternalDAO.findById(id);
	}
}
