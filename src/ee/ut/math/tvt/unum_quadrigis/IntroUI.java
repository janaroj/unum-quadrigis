package ee.ut.math.tvt.unum_quadrigis;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IntroUI {
	JFrame raam;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //ekraani suuruse ja laiuse jaoks
	int x,y; 
	
	public IntroUI() {
		super();
		
		x = (int) (screenSize.getWidth()*0.25);
		y = (int) (screenSize.getHeight()*0.5);
		raam = new JFrame();
		raam.setSize(x, y);
		raam.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		raam.setVisible(true);
		raam.setLayout(new GridLayout(6,1));
		raam.setLocationRelativeTo(null);	
		raam.setTitle("Unum Quagridis");
		JPanel teamName=new JPanel(),leaderName=new JPanel(),leaderEmail=new JPanel(),members=new JPanel(),logo=new JPanel(),version=new JPanel();
		Font f = new Font(Font.SERIF,1,20),g=new Font(Font.SANS_SERIF,0,16);
		JLabel tn=new JLabel("Unum Quadrigis"),ln=new JLabel("Marek Pagel"),le=new JLabel("pagel.marek@gmail.com"),m=new JLabel("Marek Pagel, Oskar Hint, Eerik Muuli, Janar Ojalaid"),lg=new JLabel("tiimi logo"),v=new JLabel("versioon");
		tn.setFont(f);ln.setFont(g);le.setFont(g);m.setFont(g);lg.setFont(g);v.setFont(g);
		teamName.add(tn);
		leaderName.add(ln);
		leaderEmail.add(le);
		members.add(m);
		logo.add(lg);
		version.add(v);
		raam.add(teamName);
		raam.add(leaderName);
		raam.add(leaderEmail);
		raam.add(members);
		raam.add(logo);
		raam.add(version);
		
	}
	
	

}
