package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.Client;
import ee.ut.math.tvt.salessystem.domain.data.Sale;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {

        private static final Logger log = Logger.getLogger(SalesDomainControllerImpl.class);

        private SalesSystemModel model;

        private final Session session = HibernateUtil.currentSession();

        @Override
        @SuppressWarnings("unchecked")
        public List<StockItem> getAllStockItems() {
                List<StockItem> result = session.createQuery("from StockItem").list();
                log.info(result.size() + " items loaded from disk");

                return result;
        }

        @Override
        @SuppressWarnings("unchecked")
        public List<Sale> getAllSales() {
                List<Sale> result = session.createQuery("from Sale").list();
                log.info(result.size() + " Sales loaded from disk");

                for (Sale sale : result) {
                        sale.setSoldItems(new HashSet<SoldItem>(session.createQuery("from SoldItem where sale_id = " + sale.getId()).list()));
                }
                return result;
        }

        @Override
        @SuppressWarnings("unchecked")
        public List<Client> getAllClients() {
                List<Client> clients = session.createQuery("from Client").list();
                log.info(clients.size() + " clients loaded from disk");

                return clients;
        }

        @Override
        public Client getClient(long id) {
                return (Client) session.get(Client.class, id);
        }

        private StockItem getStockItem(long id) {
                return (StockItem) session.get(StockItem.class, id);
        }

        @Override
        public void registerSale(Sale sale) {
                // Begin transaction
                Transaction tx = session.beginTransaction();

                sale.setSellingTime(new Date());

                // Reduce quantities of stockItems in warehouse
                for (SoldItem item : sale.getSoldItems()) {
                        // Associate with current sale
                        item.setSale(sale);

                        StockItem stockItem = getStockItem(item.getStockItem().getId());
                        stockItem.setQuantity(stockItem.getQuantity() - item.getQuantity());
                        session.save(stockItem);
                }
                session.save(sale);

                // end transaction
                tx.commit();

                model.getPurchaseHistoryTableModel().addRow(sale);
        }

        @Override
        public void createStockItem(StockItem stockItem) {
                // Begin transaction
                Transaction tx = session.beginTransaction();
                session.save(stockItem);
                tx.commit();
                model.getWarehouseTableModel().addRow(stockItem);
                log.info("Added new stockItem : " + stockItem);
        }

        @Override
        public void cancelCurrentPurchase() {
                // Cancel current purchase
                log.info("Current purchase canceled");
        }

        @Override
        public void startNewPurchase() {
                // Start new purchase
                log.info("New purchase started");
        }

        @Override
        public void setModel(SalesSystemModel model) {
                this.model = model;
        }

        @Override
        public void endSession() {
                HibernateUtil.closeSession();
        }

		@Override
		public void submitCurrentPurchase(List<SoldItem> goods, Client client)
				throws VerificationFailedException {
			// TODO Auto-generated method stub
			
		}
}
