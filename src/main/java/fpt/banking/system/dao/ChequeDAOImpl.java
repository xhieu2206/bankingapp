package fpt.banking.system.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fpt.banking.system.model.Account;
import fpt.banking.system.model.Cheque;

@Repository
public class ChequeDAOImpl implements ChequeDAO {
	
	// define field for entitymanager	
	private EntityManager entityManager;
	
	// set up constructor injection
	@Autowired
	public ChequeDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<Cheque> getCheques(long accountId) {
		Session session = entityManager.unwrap(Session.class);
		Account account = session.get(Account.class, accountId);
		System.out.println(account.getCheques().size());
		return account.getCheques();
	}
}
