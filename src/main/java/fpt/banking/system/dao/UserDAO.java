package fpt.banking.system.dao;

import java.util.Date;
import java.util.List;

import fpt.banking.system.model.Membership;
import fpt.banking.system.model.User;

public interface UserDAO {
	
	public User getUser(int id);

	public User getUser(long id);
	
	public User findById(long id);
	
	public User findByEmail(String email);
	
	public User findByUsername(String username);
	
	public User findByIdCardNumber(String idCardNumber);
	
	public User findByAccountNumber(String accountNumber);
	
	public User findByCardNumber(String cardNumber);
	
	public User findByPhoneNumber(String phone);
	
	public int increaseAttemptedLoginFail(long userId);

	public void lockAnUser(long userId);
	
	public void unlockAnUser(long userId);
	
	public void resetAttemptedLoginFail(long userId);
	
	public void changePassword(long userId, String passwordEncoder);
	
	public long saveUser(
		String username,
		String email,
		String passwordEncoderString,
		String fullName,
		Date birthday,
		String address,
		String gender,
		String idCardNumber,
		String phone,
		Membership membership,
		String image);
	
	public long getTotalUsers();

	public List<User> getUsersWithPagination(int page);
	
	public long updatedUser(
		long userId,
		String username,
		String email,
		String fullName,
		Date birthday,
		String address,
		String gender,
		String idCardNumber,
		String phone,
		String image);
}
