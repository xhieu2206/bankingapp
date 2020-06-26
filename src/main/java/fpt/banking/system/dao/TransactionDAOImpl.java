package fpt.banking.system.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fpt.banking.system.model.Transaction;
import fpt.banking.system.payload.TransactionsResponse;

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
	public TransactionsResponse getTransactions(int accountId, int page) {
		Session session = entityManager.unwrap(Session.class);
		TransactionsResponse transactionResponse = new TransactionsResponse();
		String sql = "SELECT count(*) FROM Transaction transaction "
				+ "WHERE account_id = :accountId";
		Query query = session.createQuery(sql);
		query.setParameter("accountId", accountId);
		Long total = (Long) query.uniqueResult();
		transactionResponse.setTotalTransaction(total.intValue());
		int totalPage = (int) Math.ceil(total / 5);
		transactionResponse.setTotalPage(totalPage);
		transactionResponse.setCurrentPage(page);
		sql = "SELECT t FROM Transaction t "
				+ "WHERE account_id = :accountId";
		query = session.createQuery(sql, Transaction.class).setFirstResult((page - 1) * 5).setMaxResults(10);
		query.setParameter("accountId", accountId);
		transactionResponse.setTransactions(query.getResultList());
		return transactionResponse;
	}

}
