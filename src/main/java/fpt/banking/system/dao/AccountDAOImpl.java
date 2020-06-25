package fpt.banking.system.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fpt.banking.system.model.Account;
import fpt.banking.system.model.User;

@Repository
public class AccountDAOImpl implements AccountDAO {
	
	// define field for entitymanager	
	private EntityManager entityManager;
	
	// set up constructor injection
	@Autowired
	public AccountDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<Account> getAccounts(int userId) {
		Session session = entityManager.unwrap(Session.class);
		User user = session.get(User.class, Long.valueOf(userId));
		return user.getAccounts();
	}

}
