package fpt.banking.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fpt.banking.system.dao.CardDAO;
import fpt.banking.system.model.Card;

@Service
public class CardServiceImpl implements CardService {

	@Autowired
	private CardDAO cardDao;

	@Override
	@Transactional
	public Card getCard(int accountId) {
		return cardDao.getCard(accountId);
	}

}
