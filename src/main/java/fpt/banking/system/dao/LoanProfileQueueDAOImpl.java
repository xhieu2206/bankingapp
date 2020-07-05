package fpt.banking.system.dao;

import java.util.Date;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fpt.banking.system.model.LoanProfileQueue;

@Repository
public class LoanProfileQueueDAOImpl implements LoanProfileQueueDAO {
	
	// define field for entitymanager
	private EntityManager entityManager;

	// set up constructor injection
	@Autowired
	public LoanProfileQueueDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public LoanProfileQueue findById(long id) {
		Session session = entityManager.unwrap(Session.class);
		return session.get(LoanProfileQueue.class, id);
	}

	@Override
	public long saveLoanProfileQueue(String otpCode, long loanProfileId, Date expriedAt) {
		Session session = entityManager.unwrap(Session.class);
		LoanProfileQueue loanProfileQueue = new LoanProfileQueue(otpCode, loanProfileId, expriedAt);
		session.saveOrUpdate(loanProfileQueue);
		return loanProfileQueue.getId();
	}

	@Override
	public void deleteLoanProfileQueue(long id) {
		Session session = entityManager.unwrap(Session.class);
		LoanProfileQueue loanProfileQueue = session.get(LoanProfileQueue.class, id);
		session.remove(loanProfileQueue);
	}

}
