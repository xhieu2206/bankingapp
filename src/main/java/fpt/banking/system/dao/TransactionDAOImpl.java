package fpt.banking.system.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fpt.banking.system.model.Transaction;

@Repository
public class TransactionDAOImpl implements TransactionDAO {

	// define field for entitymanager	
	private EntityManager entityManager;
	
	// set up constructor injection
	@Autowired
	public TransactionDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<Transaction> getTransactions(int accountId, int page) {
		// TODO Auto-generated method stub
		return null;
	}

}
