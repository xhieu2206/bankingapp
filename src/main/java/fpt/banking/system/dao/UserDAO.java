package fpt.banking.system.dao;

import fpt.banking.system.model.User;

public interface UserDAO {
	
	public User getUser(int id);
	
	public User findByEmail(String email);
	
	public User findByUsername(String username);
	
	public User findByIdCardNumber(String idCardNumber);
	
	public User findByAccountNumber(String accountNumber);
	
	public User findByCardNumber(String cardNumber);
}
