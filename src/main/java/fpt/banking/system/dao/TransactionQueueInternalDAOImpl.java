package fpt.banking.system.dao;

import java.util.Date;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fpt.banking.system.model.TransactionQueueInternal;

@Repository
public class TransactionQueueInternalDAOImpl implements TransactionQueueInternalDAO {

	// define field for entitymanager
	private EntityManager entityManager;

	// set up constructor injection
	@Autowired
	public TransactionQueueInternalDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public TransactionQueueInternal findById(long id) {
		Session session = entityManager.unwrap(Session.class);
		return session.get(TransactionQueueInternal.class, id);
	}

	@Override
	public String saveTransactionQueueInternal(String otpCode, long tranferAccountId, long receiverAccountId, long amount,
			Date expriedAt, String description) {
		Session session = entityManager.unwrap(Session.class);
		TransactionQueueInternal transactionQueueInternal = new TransactionQueueInternal(otpCode, tranferAccountId, receiverAccountId, amount, description, expriedAt);
		System.out.println(transactionQueueInternal.getExpriedAt());
		session.saveOrUpdate(transactionQueueInternal);
		return transactionQueueInternal.getId().toString();
	}

	@Override
	public void deleteTransactionQueueInternal(long transactionQueueInternalId) {
		Session session = entityManager.unwrap(Session.class);
		TransactionQueueInternal transactionQueueInternal = session.get(TransactionQueueInternal.class, transactionQueueInternalId);
		session.remove(transactionQueueInternal);
	}

}
