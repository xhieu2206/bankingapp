package fpt.banking.system.dao;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

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

	@Override
	public long saveCard(String cardNumber, Account account) {
		Session session = entityManager.unwrap(Session.class);
		Card card = new Card();
		LocalDate createdAt = LocalDate.now();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 5);
		Date expiredDate = cal.getTime();
		card.setAccount(account);
		card.setCardNumber(cardNumber);
		card.setCreatedAt(java.sql.Date.valueOf(createdAt));
		card.setExpiredAt(expiredDate);
		session.saveOrUpdate(card);
		return card.getId();
	}

}
