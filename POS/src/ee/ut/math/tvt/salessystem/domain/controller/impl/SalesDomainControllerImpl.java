package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.DisplayableItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {
	private final Session session = HibernateUtil.currentSession();

	private void saveEntities(List<? extends DisplayableItem> items) throws VerificationFailedException {
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			for (DisplayableItem item : items) {
				session.persist(item);
			}
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new VerificationFailedException(e);
		}
	}

	public void submitCurrentPurchase(List<SoldItem> goods)
			throws VerificationFailedException {
		saveEntities(goods);

	
	}

	public void cancelCurrentPurchase() throws VerificationFailedException {
		// XXX - Cancel current purchase
	}

	public void startNewPurchase() throws VerificationFailedException {
		// XXX - start new purchase
	}

	public void submitPurchase() throws VerificationFailedException {

	}

	@SuppressWarnings("unchecked")
	public List<StockItem> loadWarehouseState() {
		return session.createQuery("from StockItem").list();
	}

	public void endSession() {
		HibernateUtil.closeSession();

	}

}
