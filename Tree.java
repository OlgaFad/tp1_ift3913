import org.w3c.dom.Node;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.List;

public class Tree<String> {
    private String value = null;
    private Tree<String> parent = null;
    private List<Tree<String>> children = new ArrayList<Tree<String>>();

    public Tree(String value){
        this.value = value;
    }

    public Tree(String value, Tree<String> parent){
        this.value = value;
        this.parent = parent;
    }

    public void setParent(Tree parent){
        parent.addChild(this);
        this.parent = parent;
    }

    public void addChild(String value){
        Tree<String> newChild = new Tree<>(value);
        newChild.setParent(this);
        children.add(newChild);
    }

    public void addChild(Tree<String> child){
        child.setParent(this);
        this.children.add(child);
    }

    public List<Tree<String>> getChildren() {
        return children;
    }

    public String getValue(){
        return this.value;
    }

    public void setValue(String value){
        this.value = value;
    }





//    DefaultTreeModel root = new DefaultTreeModel("gyi");
//    createNodes(root);
//
//
//    public TreeDemo(){
//        DefaultTreeModel top = new DefaultTreeModel("root");
//        createNodes(top);
//    }
}
