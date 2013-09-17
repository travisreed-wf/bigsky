package bigsky;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class SmallChat {
	
	public static void main(String[] args){
		
		JFrame jfrm = new JFrame("small chat");
		jfrm.setSize(210,400);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel jlab = new JLabel("TEST MESSAGE");
		jfrm.add(jlab);
		jfrm.setVisible(true);
		jfrm.setLayout(new FlowLayout());
		
	}
	
	public SmallChat(){
		
		
		
	}
	
	
	
}
