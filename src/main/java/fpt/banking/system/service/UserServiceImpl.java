package fpt.banking.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fpt.banking.system.dao.UserDAO;
import fpt.banking.system.model.User;

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

}
