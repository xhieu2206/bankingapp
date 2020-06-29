package fpt.banking.system.service;

import fpt.banking.system.model.User;

public interface UserService {

	public User getUser(int id);
	
	public User getUser(long id);
	
	public User findUser(String term, String type);
	
	public int increaseAttemptedLoginFail(long userId);
	
	public void lockAnUser(long userId);
	
	public void unlockAnUser(long userId);
	
	public void resetAttemptedLoginFail(long userId);
}
