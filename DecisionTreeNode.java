/**
 * Defines a decision tree and defines all available basic operations 
 */

/**
 *
 * @author Lester Hernandez Alfonso
 */

package data_mining_project;
import java.util.ArrayList;


// Class that represents a Decision Tree Node
public class DecisionTreeNode<T> {

    private T data;
    private DecisionTreeNode<T> parent;
    private ArrayList<DecisionTreeNode<T>> children;

    public DecisionTreeNode(T data) {
        this.data = data;
        this.children = new ArrayList<DecisionTreeNode<T>>();
    }
    
    //Create and add a new node as a child.
    public void addChild(T child) {
        DecisionTreeNode<T> childNode = new DecisionTreeNode<T>(child);
        childNode.parent = this;
        this.children.add(childNode);
    }
    
    //Attach an existing node as a child.
    public void attachChild(DecisionTreeNode<T> childNode) {
        childNode.parent = this;
        this.children.add(childNode);
    }
    
    public T getData() {
        return data;
    }

    public DecisionTreeNode<T> getParent() {
        return parent;
    }

    public ArrayList<DecisionTreeNode<T>> getChildren() {
        return children;
    }
    
    public void displayTree() {
        
        System.out.println(this.getData().toString());
        
        ArrayList<DecisionTreeNode<T>> allchildren = this.getChildren();
        for(DecisionTreeNode<T> child: allchildren) {
            child.displayTree();
        }
    }

}
