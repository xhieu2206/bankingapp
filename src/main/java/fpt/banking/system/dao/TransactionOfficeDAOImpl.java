package fpt.banking.system.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fpt.banking.system.model.TransactionOffice;
import fpt.banking.system.model.User;

@Repository
public class TransactionOfficeDAOImpl implements TransactionOfficeDAO {
	
	private EntityManager entityManager;
	
	@Autowired
	public TransactionOfficeDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	@Transactional
	public TransactionOffice findById(long id) {
		Session session = entityManager.unwrap(Session.class);
		return session.get(TransactionOffice.class, id);
	}

	@Override
	@Transactional
	public List<TransactionOffice> findTransactionOfficesOfABranchOffice(long branchOfficeId) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT t FROM TransactionOffice t " +
					 "WHERE branch_office_id = :branchOfficeId";
		Query<TransactionOffice> query = session.createQuery(sql, TransactionOffice.class);
		query.setParameter("branchOfficeId", branchOfficeId);
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<TransactionOffice> listAllTransactionOffices() {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT * FROM transaction_office";
		NativeQuery<TransactionOffice> query = session.createNativeQuery(sql, TransactionOffice.class);
		return query.getResultList();
	}

}
