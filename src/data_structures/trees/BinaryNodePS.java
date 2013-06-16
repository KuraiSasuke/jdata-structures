package data_structures.trees;

/**
 * A BinaryNodePS is a binary tree node that contains a pointer to its sons.
 * 
 * @author Davide
 * 
 * @param <T>
 */
public class BinaryNodePS<T> extends Node<T> {

	private BinaryNodePS<T> parent = null;
	private BinaryNodePS<T> left = null;
	private BinaryNodePS<T> right = null;
	private BinaryTreePS<T> container = null;
	
	public BinaryNodePS(T info) {
		super(info);
	}
	
	public BinaryNodePS<T> getParent() {
		return parent;
	}

	public void setParent(BinaryNodePS<T> parent) {
		this.parent = parent;
	}

	public BinaryNodePS<T> getLeft() {
		return left;
	}

	public void setLeft(BinaryNodePS<T> left) {
		this.left = left;
	}

	public BinaryNodePS<T> getRight() {
		return right;
	}

	public void setRight(BinaryNodePS<T> right) {
		this.right = right;
	}

	@Override
	public BinaryTreePS<T> getContainer() {
		BinaryNodePS<T> n = this;
		while (n.parent != null) {
			n = n.parent;
		}
		return n.container;
	}
	
	@Override
	public void setContainer(Tree<T> container) {
		this.container = (BinaryTreePS<T>) container;
	}
	
	@Override
	public void clearContainerPointer() {
		container = null;
	}

}
