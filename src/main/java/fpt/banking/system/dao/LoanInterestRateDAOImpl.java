package fpt.banking.system.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fpt.banking.system.model.LoanInterestRate;

@Repository
public class LoanInterestRateDAOImpl implements LoanInterestRateDAO {
	
	// define field for entitymanager	
	private EntityManager entityManager;
		
	// set up constructor injection
	@Autowired
	public LoanInterestRateDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public LoanInterestRate findById(long id) {
		Session session = entityManager.unwrap(Session.class);
		LoanInterestRate loanInterestRate = session.get(LoanInterestRate.class, id);
		return loanInterestRate;
	}

}
