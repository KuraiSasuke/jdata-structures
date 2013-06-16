package data_structures.trees;

public interface BinaryTree<T> extends Tree<T> {
	
	Node<T> getLeftChild(Node<T> parent);
	
	Node<T> getRightChild(Node<T> parent);
	
	Node<T> addLeftChild(Node<T> parent, T info);
	
	Node<T> addRightChild(Node<T> parent, T info);
	
	Node<T> addLeftSubtree(Node<T> parent, BinaryTree<T> st);
	
	Node<T> addRightSubtree(Node<T> parent, BinaryTree<T> st);
	
}
