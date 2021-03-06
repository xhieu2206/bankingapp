package fpt.banking.system.dao;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
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
	public List<Account> getAccounts(long userId) {
		Session session = entityManager.unwrap(Session.class);
		User user = session.get(User.class, userId);
		return user.getAccounts();
	}
	
	@Override
	public List<Account> getUseableAccounts(long userId) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT a FROM Account a "
				   + "WHERE user_id = :userId "
				   + "AND status = 1 "
				   + "AND expired_date > :date";
		Query<Account> q = session.createQuery(sql, Account.class);
		q.setParameter("userId", userId);
		q.setDate("date",  new java.util.Date());
		if (q.getResultList().size() == 0) {
			return null;
		}
		try {
			return q.getResultList();
		} catch (NoResultException e) {
			return null;
		}
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

	@Override
	public long saveAccount(String accountNumber, User user, long amount, String pinCodeEncoderString) {
		Session session = entityManager.unwrap(Session.class);
		LocalDate createdAt = LocalDate.now();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 5);
		Date expiredDate = cal.getTime();
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		account.setUser(user);
		account.setAmount(amount);
		account.setPinCode(pinCodeEncoderString);
		account.setCreatedAt(java.sql.Date.valueOf(createdAt));
		account.setUpdatedAt(java.sql.Date.valueOf(createdAt));
		account.setExpiredAt(expiredDate);
		account.setStatus(true);
		account.setOtpTransferEnabled(true);
		session.saveOrUpdate(account);
		return account.getId();
	}

	@Override
	public void lockAccount(long accountId) {
		Session session = entityManager.unwrap(Session.class);
		Account account = session.get(Account.class, accountId);
		account.setStatus(false);
		session.saveOrUpdate(account);
	}
}
