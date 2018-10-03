import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;

public class GUI {

	private JFrame frame;
	private JTextField textField;
	private JList list;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 436, 311);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(149, 17, 268, 29);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Ouvrir fichier");
		btnNewButton.setBackground(new Color(250, 250, 210));
		btnNewButton.setForeground(new Color(0, 100, 0));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				}
				catch(Exception exc) { }
				
				//Permet d'ouvrir une fenetre pour choisir le fichier a ouvrir
				JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "UCD", "ucd");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(frame);
			    
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	textField.setText(chooser.getSelectedFile().getName());
//			        System.out.println("Vous avez choisit d'ouvrir ce fichier: " +
//			            chooser.getSelectedFile().getName());
			    	
			    	if (chooser.getSelectedFile().canRead() == true) {
//			    		Parser(chooser.getSelectedFile());
			    	}
			    }
				
				
			}
		});
		
		btnNewButton.setBounds(17, 18, 117, 29);
		frame.getContentPane().add(btnNewButton);
		
	
		
		list = new JList();
		list.setBounds(17, 85, 117, 176);
		frame.getContentPane().add(list);
	}
}
