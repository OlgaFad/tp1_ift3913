import javax.swing.DefaultListModel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

public class MyTree{

	private JTree tree;
	private DefaultMutableTreeNode root;
//	private DefaultMutableTreeNode[] children;
	
	MyTree(String myRoot){
		root = new DefaultMutableTreeNode(myRoot);
		JTree tree = new JTree(root);
		
//		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
//		tree.addTreeSelectionListener(new TreeSelectionListener(){
//
//			@Override
//			public void valueChanged(TreeSelectionEvent e) {
//				DefaultMutableTreeNode node = (DefaultMutableTreeNode)
//                        tree.getLastSelectedPathComponent();
//
//			 /* if nothing is selected */ 
//			     if (node == null) return;
//			
//			 /* retrieve the node that was selected */ 
//			     Object nodeInfo = node.getUserObject();
//			     
//			 /* React to the node selection. */
//			     
//			}
//			
//		});
		
	}
	
	public void createNodeChildren(DefaultMutableTreeNode node, String[] children){
		for(int i=0; i<children.length; i++){
			createNodeChild(node, children[i]);
		}
	}
	
	public void createNodeChild(DefaultMutableTreeNode node, String children) {
		
        DefaultMutableTreeNode child;
        if(children !=null){
        	child = new DefaultMutableTreeNode(children);
//        	child.setParent(node);
        	node.add(child);
        }
    }
	
	public DefaultMutableTreeNode getDMTNRoot(){
		return root;
	}
	
	public DefaultMutableTreeNode[] getChildrenTreeNodes(DefaultMutableTreeNode root){
		
		int numberOfChildren = root.getChildCount();
		DefaultMutableTreeNode[] childrenTreeNodeArray = new DefaultMutableTreeNode[numberOfChildren];
		
		for (int i = 0; i < numberOfChildren; i++ ){
			childrenTreeNodeArray[i] = (DefaultMutableTreeNode) root.getChildAt(i);
		}
		return childrenTreeNodeArray;
	}
	
	public DefaultListModel createDefaultListModel(DefaultMutableTreeNode node){
		
		DefaultListModel dlm = new DefaultListModel();
		int numberOfChildren = node.getChildCount();
		for(int i=0; i<numberOfChildren; i++){
			dlm.add(i, node.getChildAt(i));
		}
		return dlm;
		
	}
}
