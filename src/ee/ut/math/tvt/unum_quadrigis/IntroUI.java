package ee.ut.math.tvt.unum_quadrigis;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IntroUI {
	JFrame raam;
	JPanel raam_�lemine;
	JPanel raam_logo;
	// Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	// //ekraani suuruse ja laiuse jaoks
	int x, y;

	public IntroUI() {
		super();

		x = 400;
		y = 600;
		raam = new JFrame();
		raam.setSize(x, y);
		raam.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		raam.setLayout(new GridLayout(2, 1));
		raam.setLocationRelativeTo(null);
		raam.setTitle("Unum Quagridis");
		raam_�lemine = new JPanel();
		raam_�lemine.setLayout(new GridLayout(5, 1));

		raam.add(raam_�lemine);
		raam_logo = new JPanel();
		raam.add(raam_logo);
//		JPanel teamName = new JPanel(), leaderName = new JPanel(), leaderEmail = new JPanel(), members = new JPanel(), logo = new JPanel(), version = new JPanel();
		Font f = new Font(Font.SERIF, 1, 20), g = new Font(Font.SANS_SERIF, 0,
				16);

		JLabel tn = new JLabel("Unum Quadrigis", JLabel.CENTER), ln = new JLabel("Marek Pagel", JLabel.CENTER), le = new JLabel(
				"pagel.marek@gmail.com", JLabel.CENTER), m = new JLabel(
				"Marek Pagel, Oskar Hint, Eerik Muuli, Janar Ojalaid", JLabel.CENTER), lg = new JLabel(
				new ImageIcon("lib/temppic.jpg"), JLabel.CENTER), v = new JLabel("versioon", JLabel.CENTER);
		tn.setFont(f);
		ln.setFont(g);
		le.setFont(g);
		m.setFont(g);
		lg.setFont(g);
		v.setFont(g);
		// teamName.add(tn);
		// leaderName.add(ln);
		// leaderEmail.add(le);
		// members.add(m);
		// logo.add(lg);
		// version.add(v);
		raam_�lemine.add(tn);
		raam_�lemine.add(ln);
		raam_�lemine.add(le);
		raam_�lemine.add(m);
		raam_logo.add(lg);
		raam_�lemine.add(v);
		// raam_�lemine.add(teamName);
		// raam_�lemine.add(leaderName);
		// raam_�lemine.add(leaderEmail);
		// raam_�lemine.add(members);
		// raam_logo.add(logo);
		// raam_�lemine.add(version);
		raam.setVisible(true);

	}

}
