package fpt.banking.system.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fpt.banking.system.model.Conversation;

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

}
