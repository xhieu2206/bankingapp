package fpt.banking.system.dao;

import java.time.LocalDate;
import java.time.ZoneId;
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
	
	ZoneId defaultZoneId = ZoneId.systemDefault();
	
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

	@Override
	public Account getAccount(int accountId) {
		Session session = entityManager.unwrap(Session.class);
		Account account = session.get(Account.class, Long.valueOf(accountId));
		return account;
	}

	@Override
	public void changeAmount(int accountId, Long amount) {
		Session session = entityManager.unwrap(Session.class);
		Account account = session.get(Account.class, accountId);
		Long newAmount = account.getAmount() + amount;
		LocalDate updatedAt = LocalDate.now();
		account.setAmount(newAmount);
		account.setUpdatedAt(java.sql.Date.valueOf(updatedAt));
		session.saveOrUpdate(account);
	}

}
