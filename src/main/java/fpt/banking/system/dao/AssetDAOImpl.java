package fpt.banking.system.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fpt.banking.system.model.Asset;
import fpt.banking.system.model.LoanProfile;

@Repository
public class AssetDAOImpl implements AssetDAO {
	
	// define field for entitymanager	
	private EntityManager entityManager;
	
	@Autowired
	public AssetDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public Asset findById(long id) {
		Session session = entityManager.unwrap(Session.class);
		Asset asset = session.get(Asset.class, id);
		try {
			return asset;
		} catch (Exception e) {
			System.out.println("LOI");
			return null;
		}
	}

	@Override
	public long saveAsset(String name, String description, long price, LoanProfile loanProfile) {
		Session session = entityManager.unwrap(Session.class);
		Asset asset = new Asset();
		asset.setName(name);
		asset.setDescription(description);
		asset.setPrice(price);
		asset.setLoanProfile(loanProfile);
		session.saveOrUpdate(asset);
		return asset.getId();
	}

	@Override
	public void removeAsset(long assetId) {
		Session session = entityManager.unwrap(Session.class);
		Asset asset = session.get(Asset.class, assetId);
		session.remove(asset);
	}

}
