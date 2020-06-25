package fpt.banking.system.dao;

import fpt.banking.system.model.Card;

public interface CardDAO {

	public Card getCard(int accountId);
}
