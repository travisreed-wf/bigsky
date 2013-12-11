package bigsky.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LoadScreen extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public LoadScreen() {
        if (!System.getProperty("os.name").contains("Mac")){
        	  setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/bigsky/BlueText.gif")));
          }
        
          setResizable(false);
		  setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		  JLabel image = new JLabel();
          contentPane = (JPanel) getContentPane();
          contentPane.setLayout(new BorderLayout());
          setSize(new Dimension(576, 320));
          ImageIcon icon = new ImageIcon(this.getClass().getResource("Load.gif"));
          image.setIcon(icon);
          contentPane.add(image, java.awt.BorderLayout.CENTER);
          this.setLocationRelativeTo(null);
	}
}
