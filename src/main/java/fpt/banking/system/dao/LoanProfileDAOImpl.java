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
import fpt.banking.system.model.LoanInterestRate;
import fpt.banking.system.model.LoanProfile;
import fpt.banking.system.model.LoanProfileQueue;
import fpt.banking.system.model.TransactionOffice;
import fpt.banking.system.model.User;

@Repository
public class LoanProfileDAOImpl implements LoanProfileDAO {

	// define field for entitymanager	
	private EntityManager entityManager;
	
	// set up constructor injection
	@Autowired
	public LoanProfileDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public LoanProfile findById(long id) {
		Session session = entityManager.unwrap(Session.class);
		LoanProfile loanProfile = session.get(LoanProfile.class, id);
		return loanProfile;
	}

	@Override
	public long saveLoanProfile(long amount, String description, Account account, LoanInterestRate loanInterestRate, User user,
			TransactionOffice transactionOffice) {
		Session session = entityManager.unwrap(Session.class);
		LocalDate createdAt = LocalDate.now();
		LoanProfile loanProfile = new LoanProfile();
		loanProfile.setAmount(amount);
		loanProfile.setDescription(description);
		loanProfile.setConfirmed(false);
		loanProfile.setApproved(false);
		loanProfile.setRejected(false);
		loanProfile.setStatus("1");
		loanProfile.setCreatedAt(java.sql.Date.valueOf(createdAt));
		
		loanProfile.setLoanInterestRate(loanInterestRate);
		loanProfile.setAccount(account);
		loanProfile.setUser(user);
		loanProfile.setTransactionOffice(transactionOffice);
		
		session.saveOrUpdate(loanProfile);
		return loanProfile.getId();
	}

	@Override
	public List<LoanProfile> findLoanProfilesByUser(User user) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT lp FROM LoanProfile lp " +
					 "WHERE user_id = :user_id";
		Query<LoanProfile> q = session.createQuery(sql, LoanProfile.class);
		q.setParameter("user_id", user.getId());
		try {
			return q.getResultList(); 
		} catch (NullPointerException e) {
			return null;
		}
	}

	@Override
	public void confirmLoanProfile(long id, String employeeConfirmedName, long employeeConfirmedId) {
		Session session = entityManager.unwrap(Session.class);
		LoanProfile loanProfile = session.get(LoanProfile.class, id);
		loanProfile.setConfirmed(true);
		loanProfile.setStatus("2");
		loanProfile.setEmployeeConfirmedName(employeeConfirmedName);
		loanProfile.setEmployeeConfirmedId(employeeConfirmedId);
		session.save(loanProfile);
	}

	@Override
	public long approvedLoanProfileByTransactionManager(long loanProfileId) {
		Session session = entityManager.unwrap(Session.class);
		LoanProfile loanProfile = session.get(LoanProfile.class, loanProfileId);
		loanProfile.setStatus("3");
		session.saveOrUpdate(loanProfile);
		return loanProfile.getId();
	}

	@Override
	public long approvedLoanProfileByBranchManager(long loanProfileId) {
		Session session = entityManager.unwrap(Session.class);
		LoanProfile loanProfile = session.get(LoanProfile.class, loanProfileId);
		loanProfile.setStatus("4");
		loanProfile.setApproved(true);
		session.saveOrUpdate(loanProfile);
		return loanProfile.getId();
	}

	@Override
	public void rejectLoanProffile(long loanProfileId, String rejectedReason) {
		Session session = entityManager.unwrap(Session.class);
		LoanProfile loanProfile = session.get(LoanProfile.class, loanProfileId);
		loanProfile.setStatus("0");
		loanProfile.setRejected(true);
		loanProfile.setRejectedReason(rejectedReason);
	}

	@Override
	public List<LoanProfile> getLoanProfilesByTransactionOffice(long transactionOfficeId, int page, String status) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "";
		if (status == "ALL") {
			sql = "SELECT l FROM LoanProfile l " +
				  "WHERE transaction_office_id = :transactionOfficeId " +
				  "ORDER BY l.id DESC";
		} else {
			sql = "SELECT l FROM LoanProfile l " +
				  "WHERE transaction_office_id = :transactionOfficeId " +
				  "AND status = :status " +
				  "ORDER BY l.id DESC";
		}
		Query<LoanProfile> q = session.createQuery(sql, LoanProfile.class).setFirstResult((page - 1) * 5).setMaxResults(5);
		q.setParameter("transactionOfficeId", transactionOfficeId);
		if (status != "ALL") {
			q.setParameter("status", status);
		}
		List<LoanProfile> results;
		try {
			results = q.getResultList();
		} catch (NoResultException e) {
			return null;
		} 
		return results;
	}

	@Override
	public long getTotalLoanProfilesByTransactionOffice(long transactionOfficeId, String status) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "";
		if (status == "ALL") {
			sql = "SELECT COUNT(*) FROM LoanProfile l " +
				  "WHERE transaction_office_id = :transactionOfficeId";
		} else {
			sql = "SELECT COUNT(*) FROM LoanProfile l " +
				  "WHERE transaction_office_id = :transactionOfficeId " +
				  "AND status = :status";
		}
		Query q = session.createQuery(sql);
		q.setParameter("transactionOfficeId", transactionOfficeId);
		if (status != "ALL") {
			q.setParameter("status", status);
		}
		long total = (Long) q.uniqueResult();
		return total;
	}
}
