package data_structures.trees;

import java.util.Iterator;

public abstract class AbstractBinaryTree<T> extends AbstractTree<T> implements
		BinaryTree<T> {

	public AbstractBinaryTree(boolean safety) {
		super(safety);
	}

	@Override
	public Node<T> addChild(Node<T> parent, T info) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Node<T> addSubtree(Node<T> parent, Tree<T> st) {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof BinaryTree)) {
			return false;
		}
		if (o == this) {
			return true;
		}
		BinaryTree<T> btr = (BinaryTree<T>) o;
		if (size() != btr.size()) {
			return false;
		}
		Iterator<Node<T>> it1 = iterator();
		Iterator<Node<T>> it2 = iterator();
		while (it1.hasNext()) {
			Node<T> n1 = it1.next();
			Node<T> n2 = it2.next();
			if (!n1.equals(n2)) {
				return false;
			}
		}
		return true;
	}

}
