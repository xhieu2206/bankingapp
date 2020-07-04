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
		loanProfile.setStatus("RECEIVED");
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
		List<LoanProfile> results;
		try {
			results = q.getResultList(); 
		} catch (NoResultException e) {
			return null;
		}
		return results;
	}

}
