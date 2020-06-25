package fpt.banking.system.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fpt.banking.system.model.Account;
import fpt.banking.system.model.Card;

@Repository
public class CardDAOImpl implements CardDAO {

	// define field for entitymanager	
	private EntityManager entityManager;
	
	// set up constructor injection
	@Autowired
	public CardDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public Card getCard(int accountId) {
		Session session = entityManager.unwrap(Session.class);
		Account account = session.get(Account.class, Long.valueOf(accountId));
		return account.getCard();
	}

}
