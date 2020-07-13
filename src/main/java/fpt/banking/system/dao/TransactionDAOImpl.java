package fpt.banking.system.dao;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fpt.banking.system.constants.TimerConstants;
import fpt.banking.system.model.Account;
import fpt.banking.system.model.Transaction;
import fpt.banking.system.model.TransactionType;

@Repository
public class TransactionDAOImpl implements TransactionDAO {

	// define field for entitymanager	
	private EntityManager entityManager;
	
	// set up constructor injection
	@Autowired
	public TransactionDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<Transaction> getTransactions(long accountId, int page) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT t FROM Transaction t "
				+ "WHERE account_id = :accountId "
				+ "ORDER BY t.id DESC";
		Query<Transaction> query = session.createQuery(sql, Transaction.class).setFirstResult((page - 1) * 5).setMaxResults(5);
		query.setParameter("accountId", accountId);
		List<Transaction> trans;
		try {
			trans = query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return trans;
	}

	@Override
	public int getTotalTransactions(long accountId) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT count(*) FROM Transaction transaction "
				+ "WHERE account_id = :accountId";
		Query query = session.createQuery(sql);
		query.setParameter("accountId", accountId);
		Long total = (Long) query.uniqueResult();
		return total.intValue();
	}

	@Override
	public void saveTransaction(long accountId, Long amountChange, Long amountAfterTransaction, int transactionTypeId,
			String description) {
		Session session = entityManager.unwrap(Session.class);
		Account account = session.get(Account.class, Long.valueOf(accountId));
		Timestamp timestamp = new Timestamp(System.currentTimeMillis() + TimerConstants.VIETNAM_TIMEZONE_TIMESTAMP);
		TransactionType transactionType = session.get(TransactionType.class, Long.valueOf(transactionTypeId));
		Transaction transaction = new Transaction();
		transaction.setAccount(account);
		transaction.setTransactionType(transactionType);
		transaction.setAmount(amountChange);
		transaction.setAmountAfterTransaction(amountAfterTransaction);
		transaction.setCreatedAt(new Date(timestamp.getTime()));
		transaction.setDescription(description);
		session.saveOrUpdate(transaction);
	}

	@Override
	public void saveTranserTransaction(long accountId, long changeAmount, long amountAfterTransaction, int transactionTypeId,
			String description, String fullname, String accountNumber) {
		Session session = entityManager.unwrap(Session.class);
		Account account = session.get(Account.class, Long.valueOf(accountId));
		Timestamp timestamp = new Timestamp(System.currentTimeMillis() + TimerConstants.VIETNAM_TIMEZONE_TIMESTAMP);
		TransactionType transactionType = session.get(TransactionType.class, Long.valueOf(transactionTypeId));
		Transaction transaction = new Transaction();
		transaction.setAccount(account);
		transaction.setTransactionType(transactionType);
		transaction.setAmount(changeAmount);
		transaction.setAmountAfterTransaction(amountAfterTransaction);
		transaction.setCreatedAt(new Date(timestamp.getTime()));
		transaction.setDescription(description);
		transaction.setFromOrToFullName(fullname);
		transaction.setFromOrToAccountNumber(accountNumber);
		session.saveOrUpdate(transaction);
	}

	@Override
	public List<Transaction> getTransactionsWithSearchTerm(long accountId, int page, String term) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT * " +
				 "FROM transaction " +
				 "WHERE account_id = :accountId " +
				 "AND (description LIKE :term " +
				 "OR from_or_to_fullname LIKE :term) " +
				 "ORDER BY id DESC";
		NativeQuery<Transaction> q = session.createNativeQuery(sql, Transaction.class).setFirstResult((page - 1) * 5).setMaxResults(5);
		q.setParameter("accountId", accountId);
		q.setParameter("term", "%" + term + "%");
		List<Transaction> results;
		try {
			results = q.getResultList();
		} catch (NoResultException e) {
			return null;
		} 
		return results;
	}

	@Override
	public int getTotalTransactionsWithSearchTerm(long accountId, String term) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT COUNT(*) " +
					 "FROM transaction " +
					 "WHERE account_id = :accountId " +
					 "AND (description LIKE :term " +
					 "OR from_or_to_fullname LIKE :term)";
		NativeQuery q = session.createNativeQuery(sql);
		q.setParameter("accountId", accountId);
		q.setParameter("term", "%" + term + "%");
		List<BigInteger> total = q.list();
		return total.get(0).intValue();
	}

	@Override
	public List<Transaction> getTransactionsWithTimeFileter(long accountId, int page, int year, int month) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "";
//		System.out.println(year);
//		System.out.println(month);
		System.out.println(page);
		if (month == 0) {
			sql = "SELECT * FROM transaction " +
	              "WHERE account_id = :accountId " +
	              "AND YEAR(created_at) = :year " +
	              "ORDER BY id DESC";
		} else {
			sql = "SELECT * FROM transaction " +
	              "WHERE account_id = :accountId " +
	              "AND YEAR(created_at) = :year " +
	              "AND MONTH(created_at) = :month " +
	              "ORDER BY id DESC";
		}
		NativeQuery<Transaction> q = session.createNativeQuery(sql, Transaction.class).setFirstResult((page - 1) * 5).setMaxResults(5);
		q.setParameter("accountId", accountId);
		q.setParameter("year", year);
		if (month > 0) {
			q.setParameter("month", month);
		}
		List<Transaction> results;
		try {
			results = q.getResultList();
		} catch (NoResultException e) {
			return null;
		} 
		return results;
	}

	@Override
	public int getTotalTransactionsWithTimeFileter(long accountId, int year, int month) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "";
		if (month == 0) {
			sql = "SELECT COUNT(*) FROM transaction " +
	              "WHERE account_id = :accountId " +
	              "AND YEAR(created_at) = :year";
		} else {
			sql = "SELECT COUNT(*) FROM transaction " +
	              "WHERE account_id = :accountId " +
	              "AND YEAR(created_at) = :year " +
	              "AND MONTH(created_at) = :month";
		}
		NativeQuery q = session.createNativeQuery(sql);
		q.setParameter("accountId", accountId);
		q.setParameter("year", year);
		if (month > 0) {
			q.setParameter("month", month);
		}
		List<BigInteger> total = q.list();
		return total.get(0).intValue();
	}

}
