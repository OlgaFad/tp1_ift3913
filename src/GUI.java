import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

public class GUI {

	private JFrame frame;
	private JTextField textField;
	private String selectedElement;
	private JTextArea output;
	
	//Création de tous les JList
	private JList attributs = new JList();
	private JList méthodes;
	private JList sousClasses;
	private JList relAgreg;
	
	//Création de tous les DefaultListModel
	DefaultListModel attributsModel = new DefaultListModel();
	DefaultListModel methodesModel = new DefaultListModel();
	DefaultListModel sousClassesModel = new DefaultListModel();
	DefaultListModel relAgregModel = new DefaultListModel();
	
	//Creation des tableaux pour chaque valeursl
//	String[] classes;
//	String[][] attr;
//	String[][] met;
//	String[][] sc;
//	String[][] relAgr;
	
	int numberClasses = 0;
	MyTree tree;
	DefaultMutableTreeNode root;
	DefaultMutableTreeNode[] dmtnRootChildren;
	
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
		textField.setBounds(146, 5, 268, 29);
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
// 			    		Tree<String> arbre = parseFile(chooser.getSelectedFile());
			    	}
			    }
				
				
			}
		});
		
		btnNewButton.setBounds(17, 6, 117, 29);
		frame.getContentPane().add(btnNewButton);
		
		
		
		
		
		
		//Essai
		tree = new MyTree("Ligue");
		root = tree.getDMTNRoot();
		
		String[] classes =  {"Participants", "Equipes", "Entraineurs"};
		
		String[] attributsP = {"String nom", "Strin age"};
		String[] attributsEq = {"String nom_equipe"};
		String[] attributsEn = {"Integer degre"};
		
		String[] metP = {"bob", "ana"};
		String[] metEq = {"cool"};
		String[] metEn = {"nice"};
		
		
		String[][] attr = {attributsP, attributsEq, attributsEn};
		String[][] met = {metP, metEq, metEn};
		String[][] sc = {};
		String[][] relAgr = {};
		//Creation de l'arbre
		tree.createNodeChildren(root, classes);

		//Creation des DefaultListModel pour chaque noeud
		DefaultListModel classesModel = tree.createDefaultListModel(root);
		
		//Création  des frames gui qui affichent les composantes uml
		
		JList classesL = new JList(classesModel);
		classesL.setBounds(17, 59, 117, 224);
		frame.getContentPane().add(classesL);
		
		fillClasses(attr, met, sc, relAgr); //Rempli les attributs de chaque classe
		
		
		attributs.setBounds(149, 59, 132, 65);
		frame.getContentPane().add(attributs);
		
		ListSelectionModel classesLSelectionModel;
		classesLSelectionModel = classesL.getSelectionModel();
		classesLSelectionModel.addListSelectionListener( new ClassListSelectionHandler());
		
		DefaultListModel methodesModel = new DefaultListModel();
		méthodes = new JList();
		méthodes.setBounds(293, 59, 121, 65);
		frame.getContentPane().add(méthodes);
		
		DefaultListModel sousClassesModel = new DefaultListModel();
		sousClasses = new JList();
		sousClasses.setBounds(149, 145, 132, 50);
		frame.getContentPane().add(sousClasses);
		
		DefaultListModel assocAgregModel = new DefaultListModel();
		relAgreg = new JList();
		relAgreg.setBounds(293, 145, 124, 50);
		frame.getContentPane().add(relAgreg);
		
		DefaultListModel detailsModel = new DefaultListModel();
		JTextArea details = new JTextArea();
		details.setBounds(224, 207, 268, 65);
		frame.getContentPane().add(details);
		
		JLabel lblClasses = new JLabel("Classes");
		lblClasses.setBounds(17, 42, 61, 16);
		frame.getContentPane().add(lblClasses);
		
		JLabel lblAttributs = new JLabel("Attributs");
		lblAttributs.setBounds(145, 42, 61, 16);
		frame.getContentPane().add(lblAttributs);
		
		JLabel lblMthodes = new JLabel("Méthodes");
		lblMthodes.setBounds(293, 42, 61, 16);
		frame.getContentPane().add(lblMthodes);
		
		JLabel lblSousclasses = new JLabel("Sous-classes");
		lblSousclasses.setBounds(146, 128, 97, 16);
		frame.getContentPane().add(lblSousclasses);
		
		JLabel lblAssociationsagrgations = new JLabel("Associations/Agrégations");
		lblAssociationsagrgations.setBounds(293, 128, 97, 16);
		frame.getContentPane().add(lblAssociationsagrgations);
		
		JLabel lblDtails = new JLabel("Détails");
		lblDtails.setBounds(146, 202, 97, 16);
		frame.getContentPane().add(lblDtails);
		
	}
	
	
	public void fillClasses(String[][]attr, String[][] met,String[][] sc,String[][] relAgr){
		dmtnRootChildren = tree.getChildrenTreeNodes(root);
		numberClasses = dmtnRootChildren.length;
		
		for(int i = 0; i <numberClasses-1; i++){
			switch(i){
				case 1: tree.createNodeChild(dmtnRootChildren[i], "attribut");
				case 2: tree.createNodeChild(dmtnRootChildren[i], "methodes");
				case 3: tree.createNodeChild(dmtnRootChildren[i], "sous-classes");
				case 4: tree.createNodeChild(dmtnRootChildren[i], "relations/agregations"); 
			}
			DefaultMutableTreeNode[] node = tree.getChildrenTreeNodes(dmtnRootChildren[i]);
			try{
				switch(i){
					case 1: tree.createNodeChildren(node[i], attr[i]);
					case 2: tree.createNodeChildren(node[i], met[i]);
					case 3: tree.createNodeChildren(node[i], sc[i]);
					case 4: tree.createNodeChildren(node[i], relAgr[i]); 
				}
			}catch(IndexOutOfBoundsException e){
				continue;
			}
		}
	}
	
	
	
	//ListSelectionListener pour les classes
	class ClassListSelectionHandler implements ListSelectionListener {
	    public void valueChanged(ListSelectionEvent e) {
	        ListSelectionModel lsm = (ListSelectionModel)e.getSource();
	        
	        for(int i=0; i<attributsModel.size(); i++){
	        	attributsModel.remove(i);
	        }
	        
	        for(int i = 0; i< numberClasses; i++){
	        	if (lsm.isSelectedIndex(i)){
	        		attributsModel = tree.createDefaultListModel(dmtnRootChildren[i]);
	        	}
	        }
	        
	        attributs.setModel(attributsModel);
//	        System.out.println(attributs.getModel());
//			frame.getContentPane().add(attributs);
	        
	    }
	}
	
	//ListSelectionListener pour les relations et aggregations
	class RelAggListSelectionHandler implements ListSelectionListener {
	    public void valueChanged(ListSelectionEvent e) {
	        ListSelectionModel lsm = (ListSelectionModel)e.getSource();
	        
	        for(int i=0; i<relAgregModel.size(); i++){
	        	relAgregModel.remove(i);
	        }
	        
	        for(int i = 0; i< numberClasses; i++){
	        	if (lsm.isSelectedIndex(i)){
	        		relAgregModel = tree.createDefaultListModel(dmtnRootChildren[i]);
	        	}
	        }
	        
	        relAgreg.setModel(relAgregModel);
	        System.out.println(relAgreg.getModel());
			frame.getContentPane().add(relAgreg);
	        
	    }
	}
	
}
