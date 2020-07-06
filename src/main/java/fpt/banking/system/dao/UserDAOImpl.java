package fpt.banking.system.dao;

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
public class UserDAOImpl implements UserDAO {
	
	// define field for entitymanager	
	private EntityManager entityManager;
		
	// set up constructor injection
	@Autowired
	public UserDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public User getUser(int id) {
		Session session = entityManager.unwrap(Session.class);
		User user = session.get(User.class, Long.valueOf(id));
		return user;
	}

	@Override
	public User getUser(long id) {
		Session session = entityManager.unwrap(Session.class);
		User user = session.get(User.class, id);
		return user;
	}

	@Override
	public User findById(long id) {
		Session session = entityManager.unwrap(Session.class);
		User user = session.get(User.class, id); 
		return user;
	}

	@Override
	public User findByEmail(String email) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT u FROM User u "
				+ "WHERE email = :email";
		Query<User> query = session.createQuery(sql, User.class);
		query.setParameter("email", email);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public User findByUsername(String username) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT u FROM User u "
				+ "WHERE username = :username";
		Query<User> query = session.createQuery(sql, User.class);
		query.setParameter("username", username);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public User findByIdCardNumber(String idCardNumber) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT u FROM User u "
				+ "WHERE id_card_number = :idCardNumber";
		Query<User> query = session.createQuery(sql, User.class);
		query.setParameter("idCardNumber", idCardNumber);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public User findByAccountNumber(String accountNumber) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT a FROM Account a "
				+ "WHERE account_number = :accountNumber";
		Query<Account> query = session.createQuery(sql, Account.class);
		query.setParameter("accountNumber", accountNumber);
		Account account;
		try {
			account = query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return account.getUser();
	}

	@Override
	public User findByCardNumber(String cardNumber) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT c FROM Card c "
				+ "WHERE card_number = :cardNumber";
		Query<Card> q = session.createQuery(sql, Card.class);
		q.setParameter("cardNumber", cardNumber);
		try {
			return q.getSingleResult().getAccount().getUser();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public User findByPhoneNumber(String phone) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT u FROM User u "
				+ "WHERE phone = :phone";
		Query<User> query = session.createQuery(sql, User.class);
		query.setParameter("phone", phone);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public int increaseAttemptedLoginFail(long userId) {
		Session session = entityManager.unwrap(Session.class);
		User user = session.get(User.class, userId);
		user.setAttempedLoginFailed(user.getAttempedLoginFailed() + 1);
		session.saveOrUpdate(user);
		return user.getAttempedLoginFailed();
	}

	@Override
	public void lockAnUser(long userId) {
		Session session = entityManager.unwrap(Session.class);
		User user = session.get(User.class, userId);
		user.setLocked(true);
		user.setAttempedLoginFailed(0);
		session.saveOrUpdate(user);
	}

	@Override
	public void unlockAnUser(long userId) {
		Session session = entityManager.unwrap(Session.class);
		User user = session.get(User.class, userId);
		user.setLocked(false);
		user.setAttempedLoginFailed(0);
		session.saveOrUpdate(user);
	}

	@Override
	public void resetAttemptedLoginFail(long userId) {
		Session session = entityManager.unwrap(Session.class);
		User user = session.get(User.class, userId);
		user.setAttempedLoginFailed(0);
		session.saveOrUpdate(user);
	}

	@Override
	public void changePassword(long userId, String passwordEncoder) {
		Session session = entityManager.unwrap(Session.class);
		User user = session.get(User.class, userId);
		user.setPassword(passwordEncoder);
		session.saveOrUpdate(user);
	}
}
