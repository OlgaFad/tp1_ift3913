import java.util.ArrayList;
import java.util.List;

public class Tree<String> {
    String value = null;
    Tree<String> parent = null;
    List<Tree> children = new ArrayList<>();

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

    public void addChildren(List<Tree> children) {
        for(Tree t : children) {
            t.setParent(this);
        }
        this.children.addAll(children);
    }

    public List<Tree> getChildren() {
        return children;
    }

    public String getValue(){
        return this.value;
    }

    public void setValue(String value){
        this.value = value;
    }
}
