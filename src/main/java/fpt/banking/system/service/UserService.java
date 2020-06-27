package fpt.banking.system.service;

import fpt.banking.system.model.User;

public interface UserService {

	public User getUser(int id);
	
	public User findUser(String term, String type);
}
