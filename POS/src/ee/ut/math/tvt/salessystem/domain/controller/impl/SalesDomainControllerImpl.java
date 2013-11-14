package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.DisplayableItem;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {
	private final Session session = HibernateUtil.currentSession();
	private static final Logger log = Logger.getLogger(StockTableModel.class);

	public void saveEntities(List<? extends DisplayableItem> items) throws VerificationFailedException {
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			for (DisplayableItem item : items) {
				session.persist(item);
			}
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			log.error(e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new VerificationFailedException(e);
		}
	}
	
	  public void updateEntities(List<? extends DisplayableItem> items) throws VerificationFailedException {
          Transaction transaction = null;

          try {
                  transaction = session.beginTransaction();
                  for (DisplayableItem item : items) {
                          session.merge(item);
                  }
                  session.flush();
                  transaction.commit();
          } catch (Exception e) {
                  if (transaction != null) {
                          transaction.rollback();
                  }
                  log.error(e);
                  throw new VerificationFailedException(e);
          }
  }
	  
     public void addNewStockItem(StockItem good) throws VerificationFailedException {
             List<StockItem> goods = new ArrayList<StockItem>();
             goods.add(good);
             saveEntities(goods);
     }

     public void modifyStockItem(StockItem good) throws VerificationFailedException {
             List<StockItem> goods = new ArrayList<StockItem>();
             goods.add(good);
             updateEntities(goods);
     }
     
     public void saveHistory(HistoryItem item) throws VerificationFailedException {
    	 List<HistoryItem> history = new ArrayList<HistoryItem>();
         history.add(item);
         saveEntities(history);
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

	@SuppressWarnings("unchecked")
	public List<HistoryItem> loadHistoryState() {
		return session.createQuery("from HistoryItem").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<SoldItem> loadBoughtItems(int id) {
		return session.createQuery("from SoldItem where sale_id = :nr").setParameter("nr", id).list();
	}
	
	public void endSession() {
		HibernateUtil.closeSession();

	}

}
