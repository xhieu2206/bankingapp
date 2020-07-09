package fpt.banking.system.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fpt.banking.system.dao.AccountDAO;
import fpt.banking.system.dao.CardDAO;
import fpt.banking.system.dao.MembershipDAO;
import fpt.banking.system.dao.UserDAO;
import fpt.banking.system.model.Account;
import fpt.banking.system.model.Membership;
import fpt.banking.system.model.User;
import fpt.banking.system.payload.UsersResponse;
import fpt.banking.system.util.MD5;
import fpt.banking.system.util.MobilePhoneUtil;
import fpt.banking.system.util.RandomGenerator;
import fpt.banking.system.util.SendSms;
import fpt.banking.system.util.SendSmsWithLib;
import fpt.banking.system.enums.SearchUserTypeEnum;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private CardDAO cardDAO;
	
	@Autowired
	private AccountDAO accountDAO;
	
	@Autowired
	private MembershipDAO membershipDAO;

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public User getUser(int id) {
		return userDAO.getUser(id);
	}

	@Override
	@Transactional
	public User getUser(long id) {
		System.out.println(id + " In SERVICE");
		return userDAO.findById(id);
	}

	@Override
	@Transactional
	public User findUser(String term, String type) {
		if (type.equals(SearchUserTypeEnum.IDCARDNUMBER.toString())) {
			return userDAO.findByIdCardNumber(term);
		} else if (type.equals(SearchUserTypeEnum.ACCOUNTNUMBER.toString())) {
			return userDAO.findByAccountNumber(term);
		} else if (type.equals(SearchUserTypeEnum.EMAIL.toString())) {
			return userDAO.findByEmail(term);
		} else if (type.equals(SearchUserTypeEnum.USERNAME.toString())) {
			return userDAO.findByUsername(term);
		} else if (type.equals(SearchUserTypeEnum.CARDNUMBER.toString())) {
			return userDAO.findByCardNumber(term);
		} else if (type.equals(SearchUserTypeEnum.PHONENUMBER.toString())) {
			return userDAO.findByPhoneNumber(term);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public int increaseAttemptedLoginFail(long userId) {
		return userDAO.increaseAttemptedLoginFail(userId);
	}
	
	@Override
	@Transactional
	public void lockAnUser(long userId) {
		userDAO.lockAnUser(userId);
	}
	
	@Override
	@Transactional
	public void unlockAnUser(long userId) {
		userDAO.unlockAnUser(userId);
	}

	@Override
	@Transactional
	public void resetAttemptedLoginFail(long userId) {
		userDAO.resetAttemptedLoginFail(userId);
	}

	@Override
	@Transactional
	public void changePassword(long userId, String password) {
		String passwordEncodeString = passwordEncoder.encode(password);
		userDAO.changePassword(userId, passwordEncodeString);
	}

	@Override
	@Transactional
	public long saveUser(String username, String email, String fullName, Date birthday, String address,
			String gender, String idCardNumber, String phone, long membershipId, String image) {
		Membership membership = membershipDAO.findMembershipById(membershipId);
		String accountNumber = RandomGenerator.generateAccountNumber();
		String cardNumber = RandomGenerator.generateCardNumber();
		String password = RandomGenerator.generatePassword();
		String otpCode = RandomGenerator.generateOTP();
		long userId = userDAO.saveUser(username, email, passwordEncoder.encode(password), fullName, birthday, address, gender, idCardNumber, phone, membership, image);
//		SendSmsWithLib.sendSms(MobilePhoneUtil.convertPhone(phone, "+84"), "You have created a credential in our banking app with username: " + username + " and password is: " + password
//				+ ", please login into our application to see your new account detail and card detail");
		SendSms.sendSms(MobilePhoneUtil.convertPhone(phone, "+84"), "You have created a credential in our banking app with username: " + username + " and password is: " + password
				+ ", please login into our application to see your new account detail and card detail");
		User user = userDAO.findById(userId);
		long accountId = accountDAO.saveAccount(accountNumber, user, 100000, MD5.getMd5(otpCode));
		Account account = accountDAO.getAccount(accountId);
		long cardId = cardDAO.saveCard(cardNumber, account);
		return accountId;
	}

	@Override
	@Transactional
	public UsersResponse getUsersWithPage(int page) {
		UsersResponse result = new UsersResponse();
		result.setPageNumber(page);
		result.setTotalCount(userDAO.getTotalUsers());
		int totalPage = (int) Math.ceil(userDAO.getTotalUsers() / 5);
		if (userDAO.getTotalUsers() % 5 > 0) {
			totalPage ++;
		}
		result.setTotalPage(totalPage);
		result.setItems(userDAO.getUsersWithPagination(page));
		result.setPageSize(result.getItems().size());
		return result;
	}

	@Override
	@Transactional
	public long updatedUser(long userId, String username, String email, String fullName, Date birthday, String address,
			String gender, String idCardNumber, String phone, String image) {
		return userDAO.updatedUser(userId, username, email, fullName, birthday, address, gender, idCardNumber, phone, image);
	}
}
