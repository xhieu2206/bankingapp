package fpt.banking.system.service;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twilio.rest.proxy.v1.service.PhoneNumberUpdater;

import fpt.banking.system.dao.AccountDAO;
import fpt.banking.system.dao.TransactionDAO;
import fpt.banking.system.dao.TransactionQueueInternalDAO;
import fpt.banking.system.model.Account;
import fpt.banking.system.model.TransactionQueueInternal;
import fpt.banking.system.util.MD5;
import fpt.banking.system.util.MobilePhoneUtil;
import fpt.banking.system.util.RandomGenerator;
import fpt.banking.system.constants.TimerConstants;
import fpt.banking.system.util.SendSms;

@Service
public class TranferServiceImpl implements TranferService {
	
	@Autowired
	private TransactionDAO transactionDAO;
	
	@Autowired
	private AccountDAO accountDAO;
	
	@Autowired
	private TransactionQueueInternalDAO transactionQueueInternalDAO;

	@Override
	@Transactional
	public void tranferInternal(long tranferAccountId, long receiveAccountId, long amount,
			String description) {
		Account tranferAccount = accountDAO.getAccount(tranferAccountId);
		Account receiveAccount = accountDAO.getAccount(receiveAccountId);
		transactionDAO.saveTransaction(tranferAccountId, amount * (-1), tranferAccount.getAmount() - amount, 1, description);
		transactionDAO.saveTransaction(receiveAccountId, amount, receiveAccount.getAmount() + amount, 3, description);
		accountDAO.changeAmount(tranferAccountId, tranferAccount.getAmount() - amount);
		accountDAO.changeAmount(receiveAccountId, receiveAccount.getAmount() + amount);
	}

	@Override
	@Transactional
	public String saveTransactionQueueInternal(long tranferAccountId, long receiverAccountId, long amount, String description) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis() + 300000 + TimerConstants.VIETNAM_TIMEZONE_TIMESTAMP);
		String otpCode = RandomGenerator.generateOTP();
		String phone = accountDAO.getAccount(tranferAccountId).getUser().getPhone();
		System.out.println(MobilePhoneUtil.convertPhone(phone, "+84"));
		SendSms.sendSms(MobilePhoneUtil.convertPhone(phone, "+84"), "OTP: " + otpCode);
		String id = transactionQueueInternalDAO.saveTransactionQueueInternal(MD5.getMd5(otpCode), tranferAccountId, receiverAccountId, amount, new Date(timestamp.getTime()), description);
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
