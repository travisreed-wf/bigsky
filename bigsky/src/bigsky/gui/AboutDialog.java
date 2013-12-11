package bigsky.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class AboutDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JFrame frmAbtDialog = new JFrame();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AboutDialog dialog = new AboutDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AboutDialog() {
		frmAbtDialog.setTitle("About BlueText");
		frmAbtDialog.setSize(255, 255);
		frmAbtDialog.getContentPane().setLayout(null);
		frmAbtDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmAbtDialog.setLocationRelativeTo(null);
		
		frmAbtDialog.getContentPane().setLayout(new BorderLayout());
		contentPanel.setSize(300, 279);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		frmAbtDialog.getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblBluetextBroughtTo = new JLabel("BlueText brought to you by:");
		lblBluetextBroughtTo.setBounds(30, 48, 175, 16);
		contentPanel.add(lblBluetextBroughtTo);

		JLabel lblAndrewGuibert = new JLabel("Andrew Guibert");
		lblAndrewGuibert.setBounds(30, 86, 141, 16);
		contentPanel.add(lblAndrewGuibert);

		JLabel lblAndrewHartman = new JLabel("Andrew Hartman");
		lblAndrewHartman.setBounds(30, 114, 113, 16);
		contentPanel.add(lblAndrewHartman);

		JLabel lblJonathanMielke = new JLabel("Jonathan Mielke");
		lblJonathanMielke.setBounds(30, 143, 199, 16);
		contentPanel.add(lblJonathanMielke);

		JLabel lblTravisReed = new JLabel("Travis Reed");
		lblTravisReed.setBounds(30, 171, 141, 16);
		contentPanel.add(lblTravisReed);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}
	
	public JFrame getFrame(){
		return frmAbtDialog;
	}
}
