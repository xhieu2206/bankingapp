package fpt.banking.system.service;

import java.util.Date;

import fpt.banking.system.model.Membership;
import fpt.banking.system.model.User;

public interface UserService {

	public User getUser(int id);
	
	public User getUser(long id);
	
	public User findUser(String term, String type);
	
	public int increaseAttemptedLoginFail(long userId);
	
	public void lockAnUser(long userId);
	
	public void unlockAnUser(long userId);
	
	public void resetAttemptedLoginFail(long userId);
	
	public void changePassword(long userId, String password);
	
	public long saveUser(String username,
			String email,
			String fullName,
			Date birthday,
			String address,
			String gender,
			String idCardNumber,
			String phone,
			long membershipId,
			String image);
}
