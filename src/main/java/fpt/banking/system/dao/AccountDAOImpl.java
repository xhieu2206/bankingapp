package fpt.banking.system.dao;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fpt.banking.system.model.Account;
import fpt.banking.system.model.Card;
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
	public Account getAccount(long accountId) {
		Session session = entityManager.unwrap(Session.class);
		Account account = session.get(Account.class, Long.valueOf(accountId));
		return account;
	}

	@Override
	public void changeAmount(long accountId, Long amount) {
		Session session = entityManager.unwrap(Session.class);
		Account account = session.get(Account.class, accountId);
		LocalDate updatedAt = LocalDate.now();
		account.setAmount(amount);
		account.setUpdatedAt(java.sql.Date.valueOf(updatedAt));
		session.saveOrUpdate(account);
	}

	@Override
	public Account findByAccountNumber(String accountNumber) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT a FROM Account a "
				+ "WHERE account_number = :accountNumber";
		Query<Account> q = session.createQuery(sql, Account.class);
		q.setParameter("accountNumber", accountNumber);
		try {
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Account findByCardNumber(String cardNumber) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT c FROM Card c "
				+ "WHERE card_number = :cardNumber";
		Query<Card> q = session.createQuery(sql, Card.class);
		q.setParameter("cardNumber", cardNumber);
		Card card;
		try {
			card = q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return card.getAccount();
	}

}
