package fpt.banking.system.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fpt.banking.system.model.TransactionOffice;

@Repository
public class TransactionOfficeDAOImpl implements TransactionOfficeDAO {
	
	private EntityManager entityManager;
	
	@Autowired
	public TransactionOfficeDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public TransactionOffice findById(long id) {
		Session session = entityManager.unwrap(Session.class);
		return session.get(TransactionOffice.class, id);
	}

}
