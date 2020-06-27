package fpt.banking.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fpt.banking.system.dao.UserDAO;
import fpt.banking.system.model.User;
import fpt.banking.system.enums.SearchUserTypeEnum;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Override
	@Transactional
	public User getUser(int id) {
		// TODO Auto-generated method stub
		return userDAO.getUser(id);
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
		} else {
			return null;
		}
	}
}
