package fpt.banking.system.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fpt.banking.system.model.Transaction;

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
	public List<Transaction> getTransactions(int accountId, int page) {
		Session session = entityManager.unwrap(Session.class);		
		String sql = "SELECT t FROM Transaction t "
				+ "WHERE account_id = :accountId";
		Query<Transaction> query = session.createQuery(sql, Transaction.class).setFirstResult((page - 1) * 5).setMaxResults(5);
		query.setParameter("accountId", accountId);
		List<Transaction> trans = query.getResultList();
		return trans;
	}

	@Override
	public int getTotalTransactions(int accountId) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT count(*) FROM Transaction transaction "
				+ "WHERE account_id = :accountId";
		Query query = session.createQuery(sql);
		query.setParameter("accountId", accountId);
		Long total = (Long) query.uniqueResult();
		return total.intValue();
	}

}
