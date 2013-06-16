package data_structures.trees;

import java.util.Iterator;
import java.util.List;

public interface Tree<T> extends Iterable<Node<T>> {
	
	public enum VisitPattern {
		PRE_ORDER, IN_ORDER, POST_ORDER
	}
	
	int size();
	
	void clear();
	
	boolean isEmpty();
	
	Node<T> getRoot();
	
	Node<T> getParent(Node<T> n);
	
	int getGrade(Node<T> n);
	
	Iterator<Node<T>> children(Node<T> n);
	
	Node<T> addRoot(T info);
	
	Node<T> addChild(Node<T> parent, T info);
	
	Node<T> addSubtree(Node<T> parent, Tree<T> st);
	
	Tree<T> removeSubtree(Node<T> sr);
	
	boolean contains(Node<T> n);
	
	boolean contains(T info);
	
	List<Node<T>> get(T info);
	
	Iterator<Node<T>> depthFirstSearchIt();
	
	Iterator<Node<T>> breadthFirstSearchIt();
	
	Iterator<Node<T>> depthFirstSearchIt(VisitPattern vp);
	
}