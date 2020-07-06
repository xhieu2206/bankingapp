package fpt.banking.system.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fpt.banking.system.model.Membership;

@Repository
public class MembershipDAOImpl implements MembershipDAO {
	// define field for entitymanager	
	private EntityManager entityManager;
	
	// set up constructor injection
	@Autowired
	public MembershipDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public Membership findMembershipById(long id) {
		Session session = entityManager.unwrap(Session.class);
		return session.get(Membership.class, id);
	}

}
