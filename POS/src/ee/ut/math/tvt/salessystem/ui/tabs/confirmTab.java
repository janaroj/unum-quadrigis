package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.panels.ConfirmPanel;


public class confirmTab {
	private static final Logger log = Logger.getLogger(confirmTab.class);
	
	private final SalesDomainController domainController;


	private ConfirmPanel confirmPane;
	

	private SalesSystemModel model;

	public confirmTab(SalesDomainController controller, SalesSystemModel model) {
		this.domainController = controller;
		this.model = model;
	}
	public Component draw() {
		JPanel panel = new JPanel();
		
		// Layout
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.setLayout(new GridBagLayout());

		confirmPane = new ConfirmPanel(model);
		panel.add(confirmPane);

		return panel;
	}

}
