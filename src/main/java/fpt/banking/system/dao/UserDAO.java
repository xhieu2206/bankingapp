package fpt.banking.system.dao;

import java.util.List;

import fpt.banking.system.model.User;

public interface UserDAO {

	public List<User> getUsers();
	
	public void saveUser(User user);
	
	public User getUser(int id);
	
	public void deleteUser(int id);
	
	public List<User> searchUsers(String term);
}
