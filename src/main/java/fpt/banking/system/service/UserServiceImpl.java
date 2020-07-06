package fpt.banking.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fpt.banking.system.dao.UserDAO;
import fpt.banking.system.model.User;
import fpt.banking.system.enums.SearchUserTypeEnum;

@Service
public class UserServiceImpl implements UserService {

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
}
