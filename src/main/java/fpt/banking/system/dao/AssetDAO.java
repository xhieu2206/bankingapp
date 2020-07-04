package fpt.banking.system.dao;

import fpt.banking.system.model.LoanProfile;

public interface AssetDAO {

	public void saveAsset(String name, String description, long price, LoanProfile loanProfile);
}
