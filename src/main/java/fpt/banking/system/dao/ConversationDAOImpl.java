package fpt.banking.system.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fpt.banking.system.constants.TimerConstants;
import fpt.banking.system.model.Conversation;
import fpt.banking.system.model.User;

@Repository
public class ConversationDAOImpl implements ConversationDAO {
	// define field for entitymanager	
	private EntityManager entityManager;

	// set up constructor injection
	@Autowired
	public ConversationDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<Conversation> getConversationsForUser(long userId) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT * FROM conversation " +
					 "WHERE questioner_id = :userId " +
					 "ORDER BY id DESC";
		NativeQuery<Conversation> query = session.createNativeQuery(sql, Conversation.class);
		query.setParameter("userId", userId);
		List<Conversation> results;
		try {
			results = query.getResultList();
		} catch (NoResultException e) {
			return null;
		} 
		return results;
	}

	@Override
	public Conversation findConversationById(long conversationId) {
		Session session = entityManager.unwrap(Session.class);
		Conversation conversation = session.get(Conversation.class, conversationId);
		return conversation;
	}

	@Override
	public void setReadConversationFromUser(long conversationId) {
		Session session = entityManager.unwrap(Session.class);
		Conversation conversation = session.get(Conversation.class, conversationId);
		conversation.setReadFromQuestioner(true);
		session.saveOrUpdate(conversation);
	}

	@Override
	public void setUnreadConversationFromUser(long conversationId) {
		Session session = entityManager.unwrap(Session.class);
		Conversation conversation = session.get(Conversation.class, conversationId);
		conversation.setReadFromQuestioner(false);
		session.saveOrUpdate(conversation);
	}

	@Override
	public void setReadConversationFromEmployee(long conversationId) {
		Session session = entityManager.unwrap(Session.class);
		Conversation conversation = session.get(Conversation.class, conversationId);
		conversation.setReadFromRespondent(true);
		session.saveOrUpdate(conversation);
	}

	@Override
	public void setUnreadConversationFromEmployee(long conversationId) {
		Session session = entityManager.unwrap(Session.class);
		Conversation conversation = session.get(Conversation.class, conversationId);
		conversation.setReadFromRespondent(false);
		session.saveOrUpdate(conversation);
	}

	@Override
	public long saveConversation(String title, User questioner) {
		Session session = entityManager.unwrap(Session.class);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis() + TimerConstants.VIETNAM_TIMEZONE_TIMESTAMP);
		Conversation conversation = new Conversation();
		conversation.setQuestioner(questioner);
		conversation.setTitle(title);
		conversation.setCreatedAt(new Date(timestamp.getTime()));
		conversation.setReadFromQuestioner(true);
		conversation.setReadFromRespondent(false);
		session.save(conversation);
		return conversation.getId();
	}
}
