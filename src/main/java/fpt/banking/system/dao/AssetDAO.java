package fpt.banking.system.dao;

import fpt.banking.system.model.Asset;
import fpt.banking.system.model.LoanProfile;

public interface AssetDAO {
	
	public Asset findById(long id);

	public long saveAsset(String name, String description, long price, LoanProfile loanProfile);
}
