package fpt.banking.system.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fpt.banking.system.constants.TimerConstants;
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

	@Override
	public Cheque getChequeById(long chequeId) {
		Session session = entityManager.unwrap(Session.class);
		try {
			Cheque cheque = session.get(Cheque.class, chequeId);
			return cheque;
		} catch (java.lang.NullPointerException e) {
			return null;
		}
	}

	@Override
	public void saveCheque(Account account, String recieverFullname, String recieverIdCardNumber,
			long transactionAmount) {
		Session session = entityManager.unwrap(Session.class);
		Timestamp createdAt = new Timestamp(System.currentTimeMillis() + TimerConstants.VIETNAM_TIMEZONE_TIMESTAMP);
		Timestamp expiredAt = new Timestamp(System.currentTimeMillis() + TimerConstants.VIETNAM_TIMEZONE_TIMESTAMP + 432000000);
		
		Cheque cheque = new Cheque();
		cheque.setAccount(account);
		cheque.setRecieverFullname(recieverFullname);
		cheque.setRecieverIdCardNumber(recieverIdCardNumber);
		cheque.setTransactionAmount(transactionAmount);
		cheque.setCreatedAt(new Date(createdAt.getTime()));
		cheque.setExpiredDate(new Date(expiredAt.getTime()));
		cheque.setStatus(false);
		cheque.setCanceled(false);
		
		session.save(cheque);
	}

	@Override
	public void cancelCheque(long chequeId) {
		Session session = entityManager.unwrap(Session.class);
		Cheque cheque = session.get(Cheque.class, chequeId);
		cheque.setCanceled(true);
		session.save(cheque);
	}

	@Override
	public List<Cheque> findChequesWhenDeposit(String recieverIdCardNumber, String recieverFullName) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT * FROM cheque " +
				     "WHERE expired_date >= CURDATE()" +
				     "AND reciever_fullname = :recieverIdCardNumber " +
				     "AND reciever_id_card_number = :recieverFullName " +
				     "AND status = 0 " +
				     "AND canceled = 0";
		NativeQuery<Cheque> q = session.createNativeQuery(sql, Cheque.class);
		q.setParameter("recieverIdCardNumber", recieverIdCardNumber);
		q.setParameter("recieverFullName", recieverFullName);
		List<Cheque> results;
		try {
			results = q.getResultList();
		} catch (NoResultException e) {
			return null;
		} 
		return results;
	}

	@Override
	public void updateCheque(long chequeId, String recieverFullname, String recieverIdCardNumber,
			long transactionAmount) {
		Session session = entityManager.unwrap(Session.class);
		Cheque cheque = session.get(Cheque.class, chequeId);
		cheque.setRecieverFullname(recieverFullname);
		cheque.setRecieverIdCardNumber(recieverIdCardNumber);
		cheque.setTransactionAmount(transactionAmount);
		session.saveOrUpdate(cheque);
	}
}
