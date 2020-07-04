package fpt.banking.system.dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
			// TODO Auto-generated catch block
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
	public void saveTransaction(long accountId, Long amount, Long amountAfterTransaction, int transactionTypeId,
			String description) {
		Session session = entityManager.unwrap(Session.class);
		Account account = session.get(Account.class, Long.valueOf(accountId));
		LocalDate createdAt = LocalDate.now();
		TransactionType transactionType = session.get(TransactionType.class, Long.valueOf(transactionTypeId));
		Transaction transaction = new Transaction();
		transaction.setAccount(account);
		transaction.setTransactionType(transactionType);
		transaction.setAmount(amount);
		transaction.setAmountAfterTransaction(amountAfterTransaction);
		transaction.setCreatedAt(java.sql.Date.valueOf(createdAt));
		transaction.setDescription(description);
		session.saveOrUpdate(transaction);
	}

}
