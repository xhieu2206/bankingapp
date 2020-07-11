package fpt.banking.system.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fpt.banking.system.constants.TimerConstants;
import fpt.banking.system.model.Conversation;
import fpt.banking.system.model.Message;
import fpt.banking.system.model.Role;
import fpt.banking.system.model.User;

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

	@Override
	public Message getMessageById(long messageId) {
		Session session = entityManager.unwrap(Session.class);
		Message message = session.get(Message.class, messageId);
		return message;
	}

	@Override
	public List<Message> getMessagesByConversation(long conversationId) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT * FROM message " +
					 "WHERE conversation_id = :conversationId " +
					 "ORDER BY id ASC";
		NativeQuery<Message> query = session.createNativeQuery(sql, Message.class);
		query.setParameter("conversationId", conversationId);
		try {
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void saveMessage(Conversation conversation, User user, String messageDetail) {
		Session session = entityManager.unwrap(Session.class);
		boolean messageType = false;
		String role = "";
		for (Role r: user.getRoles()) {
			role = r.getName();
		}
		if (!role.equals("ROLE_USER")) {
			messageType = true;
		}
		Timestamp timestamp = new Timestamp(System.currentTimeMillis() + TimerConstants.VIETNAM_TIMEZONE_TIMESTAMP);
		Message message = new Message();
		message.setConversation(conversation);
		message.setUser(user);
		message.setCreatedAt(new Date(timestamp.getTime()));
		message.setMessageDetail(messageDetail);
		message.setMessageType(messageType);
		session.save(message);
	}

}
