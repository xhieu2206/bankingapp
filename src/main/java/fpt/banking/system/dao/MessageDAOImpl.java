package fpt.banking.system.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fpt.banking.system.model.Message;

@Repository
public class MessageDAOImpl implements MessageDAO {

	// define field for entitymanager	
	private EntityManager entityManager;

	// set up constructor injection
	@Autowired
	public MessageDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public Message getLatestMessageOfAConversation(long conversationId) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT * FROM message " +
					 "WHERE conversation_id = :conversationId " +
					 "ORDER BY id DESC";
		NativeQuery<Message> query = (NativeQuery<Message>) session.createNativeQuery(sql, Message.class);
		query.setParameter("conversationId", conversationId);
		try {
			return query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}

}
