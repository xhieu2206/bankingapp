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
import fpt.banking.system.model.User;

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
	public List<Cheque> findChequesWhenDeposit(String recieverFullName, String recieverIdCardNumber) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT * FROM cheque " +
				     "WHERE expired_date >= CURDATE() " +
				     "AND reciever_fullname = :recieverFullName " +
				     "AND reciever_id_card_number = :recieverIdCardNumber " +
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

	@Override
	public void withdrawCheque(long chequeId, User employee) {
		Session session = entityManager.unwrap(Session.class);
		Timestamp withdrawDate = new Timestamp(System.currentTimeMillis() + TimerConstants.VIETNAM_TIMEZONE_TIMESTAMP);
		Cheque cheque = session.get(Cheque.class, chequeId);
		cheque.setStatus(true);
		cheque.setWithdrawDate(new Date(withdrawDate.getTime()));
		cheque.setWithdrawBy(employee);
		session.saveOrUpdate(cheque);
	}

	@Override
	public List<Cheque> getChequesForAdmin(long page, String status) {
		Session session = entityManager.unwrap(Session.class);
		System.out.println(status);
		String sql = "";
		if (status == "ALL") {
			sql = "SELECT c FROM Cheque c " +
				  "ORDER BY c.id DESC";
		} else if (status.equals("CANCELED")) {
			sql = "SELECT c FROM Cheque c " +
				  "WHERE canceled = 1 " +
				  "ORDER BY c.id DESC";
		} else if (status.equals("WITHDRAWED")) {
			sql = "SELECT c FROM Cheque c " +
					  "WHERE status = 1 " +
					  "ORDER BY c.id DESC";
		} else {
			sql = "SELECT c FROM Cheque c " +
					  "WHERE status = 0 " +
					  "AND canceled = 0 " +
					  "ORDER BY c.id DESC";
		}
		Query<Cheque> query = session.createQuery(sql, Cheque.class).setFirstResult((int) ((page - 1) * 5)).setMaxResults(5);
		List<Cheque> results;
		try {
			results = query.getResultList();
		} catch (NoResultException e) {
			return null;
		} 
		return results;
	}
	
	public long getTotalChequesForAdmin(String status) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "";
		if (status == "ALL") {
			sql = "SELECT COUNT(*) FROM Cheque c";
		} else if (status.equals("CANCELED")) {
			sql = "SELECT COUNT(*) FROM Cheque c " +
				  "WHERE canceled = 1";
		} else if (status.equals("WITHDRAWED")) {
			sql = "SELECT COUNT(*) FROM Cheque c " +
			      "WHERE status = 1 ";
		} else {
			sql = "SELECT COUNT(*) FROM Cheque c " +
				  "WHERE status = 0 " +
				  "AND canceled = 0";
		}
		Query q = session.createQuery(sql);
		return (Long) q.uniqueResult();
	}
}
