package fpt.banking.system.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fpt.banking.system.constants.TimerConstants;
import fpt.banking.system.model.Notification;
import fpt.banking.system.model.User;

@Repository
public class NotificationDAOImpl implements NotificationDAO {
	// define field for entitymanager	
	private EntityManager entityManager;

	// set up constructor injection
	@Autowired
	public NotificationDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public void saveNotification(String message, User user) {
		Session session = entityManager.unwrap(Session.class);
		Notification notification = new Notification();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis() + TimerConstants.VIETNAM_TIMEZONE_TIMESTAMP);
		notification.setMessage(message);
		notification.setUser(user);
		notification.setCreatedAt(new Date(timestamp.getTime()));
		notification.setRead(false);
		session.saveOrUpdate(notification);
	}

	@Override
	public List<Notification> getNotifications(long userId, int page) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT n FROM Notification n " +
					 "WHERE user_id = :userId " +
				     "ORDER BY n.id DESC";
		Query<Notification> q = session.createQuery(sql, Notification.class).setFirstResult((page - 1) * 5).setMaxResults(5);
		q.setParameter("userId", userId);
		List<Notification> results;
		try {
			results = q.getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return results;
	}

	@Override
	public int getTotalUnreadMessage(long userId) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT COUNT(*) FROM Notification n " +
					 "WHERE user_id = :userId " +
					 "AND read = 0";
		Query q = session.createQuery(sql);
		q.setParameter("userId", userId);
		Long total = (Long) q.uniqueResult();
		return total.intValue();
	}

	@Override
	public void markAsRead(long userId) {
		Session session = entityManager.unwrap(Session.class);
		String sql =
				"UPDATE Notification set read = 1 " +
			    "WHERE user_id = :userId";
		Query q = session.createQuery(sql);
		q.setParameter("userId", userId);
		int result = q.executeUpdate();
	}

	@Override
	public int getTotalNotifications(long userId) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT COUNT(*) FROM Notification n " +
					 "WHERE user_id = :userId ";
		Query q = session.createQuery(sql);
		q.setParameter("userId", userId);
		Long total = (Long) q.uniqueResult();
		return total.intValue();
	}

}
