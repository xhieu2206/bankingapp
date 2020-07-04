package fpt.banking.system.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fpt.banking.system.model.Asset;
import fpt.banking.system.model.ImagesAsset;

@Repository
public class ImagesAssetDAOImpl implements ImagesAssetDAO {
	
	// define field for entitymanager	
	private EntityManager entityManager;
	
	// set up constructor injection
	@Autowired
	public ImagesAssetDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public void saveImagesAsset(String url, Asset asset) {
		Session session = entityManager.unwrap(Session.class);
		ImagesAsset imagesAsset = new ImagesAsset();
		imagesAsset.setUrl(url);
		imagesAsset.setAsset(asset);
		session.saveOrUpdate(imagesAsset);
	}

}
