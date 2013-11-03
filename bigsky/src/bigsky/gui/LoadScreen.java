package bigsky.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

public class LoadScreen extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				 try {
			           	UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
			           } catch (UnsupportedLookAndFeelException ex) {
			               ex.printStackTrace();
			           } catch (IllegalAccessException ex) {
			               ex.printStackTrace();
			           } catch (InstantiationException ex) {
			               ex.printStackTrace();
			           } catch (ClassNotFoundException ex) {
			               ex.printStackTrace();
			           }
				try {
					LoadScreen frame = new LoadScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoadScreen() {
		  setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/bigsky/BlueText.gif")));
		  setResizable(false);
		  setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		  JLabel image = new JLabel();
          contentPane = (JPanel) getContentPane();
          contentPane.setLayout(new BorderLayout());
          setSize(new Dimension(576, 320));
          ImageIcon icon = new ImageIcon(this.getClass().getResource(
                  "Load.gif"));
          image.setIcon(icon);
          contentPane.add(image, java.awt.BorderLayout.CENTER);
          // show it
          this.setLocationRelativeTo(null);
	}

}
