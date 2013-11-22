package ee.ut.math.tvt.unum_quadrigis;

import java.awt.Font;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

public class IntroUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame;
	JPanel frame_upper;
	JPanel frame_logo;
	private final Logger log = Logger.getLogger(IntroUI.class);

	int x, y;

	public IntroUI() {
		super();
		Properties prop = new Properties();
		try {
			// load the application.properties file
			prop.load(new FileInputStream("prop//application.properties"));

			x = 400;
			y = 600;
			// t = new JFrame(); //create main frame
			this.setSize(x, y);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setLayout(new GridLayout(2, 1));
			this.setLocationRelativeTo(null);
			this.setTitle("Unum Quagridis");

			frame_upper = new JPanel(); // create the upper frame for team info
			frame_upper.setLayout(new GridLayout(5, 1));
			this.add(frame_upper);

			frame_logo = new JPanel(); // create frame for logo
			this.add(frame_logo);

			// create two fonts
			Font f = new Font(Font.SERIF, 1, 20), g = new Font(Font.SANS_SERIF,
					0, 16);

			// create all the labels used in frame
			JLabel tn = new JLabel(prop.getProperty("team.name"), JLabel.CENTER), ln = new JLabel(
					prop.getProperty("team.leader"), JLabel.CENTER), le = new JLabel(
					prop.getProperty("team.leader.email"), JLabel.CENTER), m = new JLabel(
					prop.getProperty("team.members"), JLabel.CENTER), lg = new JLabel(
					new ImageIcon(prop.getProperty("team.logo")), JLabel.CENTER);

			// load the version.properties file
			prop.load(new FileInputStream("prop//version.properties"));
			JLabel v = new JLabel(prop.getProperty("build.number"),
					JLabel.CENTER);

			// set fonts to labels
			tn.setFont(f);
			ln.setFont(g);
			le.setFont(g);
			m.setFont(g);
			lg.setFont(g);
			v.setFont(g);

			// add labels to frames
			frame_upper.add(tn);
			frame_upper.add(ln);
			frame_upper.add(le);
			frame_upper.add(m);
			frame_logo.add(lg);
			frame_upper.add(v);

			// log the work
			log.info("Frame created");

		} catch (FileNotFoundException e) {
			log.error("Loading application.properties failed - "
					+ e.getMessage());
		} catch (IOException e) {
			log.error("Loading application.properties failed - "
					+ e.getMessage());
		}

	}
}
