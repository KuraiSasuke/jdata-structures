package data_structures.trees;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class BinaryTreePS<T> extends AbstractBinaryTree<T> {

	private BinaryNodePS<T> root = null;
	private int size = 0;
	private boolean modified = false;

	public BinaryTreePS(boolean safety) {
		super(safety);
	}

	@Override
	public int size() {
		if (isSafe() || !modified) {
			return size;
		}
		size = super.size();
		modified = false;
		return size;
	}

	@Override
	public void clear() {
		root = null;
		size = 0;
		modified = false;
	}

	@Override
	public boolean isEmpty() {
		return root != null;
	}

	@Override
	public BinaryNodePS<T> getRoot() {
		return root;
	}

	@Override
	public BinaryNodePS<T> getParent(Node<T> n) {
		if (!(n instanceof BinaryNodePS)) {
			throw new DifferentNodeTypesException();
		} else if (isSafe() && !contains(n)) {
			throw new NodeNotFoundException();
		}
		return ((BinaryNodePS<T>) n).getParent();
	}

	@Override
	public BinaryNodePS<T> getLeftChild(Node<T> parent) {
		if (!(parent instanceof BinaryNodePS)) {
			throw new DifferentNodeTypesException();
		} else if (isSafe() && !contains(parent)) {
			throw new NodeNotFoundException();
		}
		return ((BinaryNodePS<T>) parent).getLeft();
	}

	@Override
	public BinaryNodePS<T> getRightChild(Node<T> parent) {
		if (!(parent instanceof BinaryNodePS)) {
			throw new DifferentNodeTypesException();
		} else if (isSafe() && !contains(parent)) {
			throw new NodeNotFoundException();
		}
		return ((BinaryNodePS<T>) parent).getRight();
	}

	@Override
	public int getGrade(Node<T> n) {
		if (!(n instanceof BinaryNodePS)) {
			throw new DifferentNodeTypesException();
		} else if (isSafe() && !contains(n)) {
			throw new NodeNotFoundException();
		}
		int grade = 0;
		BinaryNodePS<T> bn = (BinaryNodePS<T>) n;
		if (bn.getLeft() != null) {
			grade++;
		}
		if (bn.getRight() != null) {
			grade++;
		}
		return grade;
	}

	@Override
	public BinaryNodePS<T> addRoot(T info) {
		if (root != null) {
			throw new ExistingNodeException();
		}
		root = new BinaryNodePS<T>(info);
		root.setContainer(this);
		size++;
		return root;
	}

	@Override
	public BinaryNodePS<T> addLeftChild(Node<T> parent, T info) {
		if (!(parent instanceof BinaryNodePS)) {
			throw new DifferentNodeTypesException();
		} else if (isSafe() && !contains(parent)) {
			throw new NodeNotFoundException();
		}
		BinaryNodePS<T> bn = (BinaryNodePS<T>) parent;
		if (bn.getLeft() != null) {
			throw new ExistingNodeException();
		}
		BinaryNodePS<T> child = new BinaryNodePS<T>(info);
		child.setParent(bn);
		bn.setLeft(child);
		modified = true;
		size++;
		return child;
	}

	@Override
	public BinaryNodePS<T> addRightChild(Node<T> parent, T info) {
		if (!(parent instanceof BinaryNodePS)) {
			throw new DifferentNodeTypesException();
		} else if (isSafe() && !contains(parent)) {
			throw new NodeNotFoundException();
		}
		BinaryNodePS<T> bn = (BinaryNodePS<T>) parent;
		if (bn.getRight() != null) {
			throw new ExistingNodeException();
		}
		BinaryNodePS<T> child = new BinaryNodePS<T>(info);
		child.setParent(bn);
		bn.setRight(child);
		modified = true;
		size++;
		return child;
	}

	@Override
	public BinaryNodePS<T> addLeftSubtree(Node<T> parent, BinaryTree<T> st) {
		if (!(parent instanceof BinaryNodePS) || !(st instanceof BinaryTreePS)) {
			throw new DifferentNodeTypesException();
		} else if (isSafe() && !contains(parent)) {
			throw new NodeNotFoundException();
		}
		BinaryNodePS<T> bn = (BinaryNodePS<T>) parent;
		if (bn.getLeft() != null) {
			throw new ExistingNodeException();
		}
		BinaryNodePS<T> str = (BinaryNodePS<T>) st.getRoot();
		str.clearContainerPointer();
		bn.setLeft(str);
		str.setParent(bn);
		modified = true;
		if (((BinaryTreePS<T>) st).isSafe()) {
			size += st.size();
		}
		st.clear(); // this avoid undesired aliasing
		return bn;
	}

	@Override
	public BinaryNodePS<T> addRightSubtree(Node<T> parent, BinaryTree<T> st) {
		if (!(parent instanceof BinaryNodePS) || !(st instanceof BinaryTreePS)) {
			throw new DifferentNodeTypesException();
		} else if (isSafe() && !contains(parent)) {
			throw new NodeNotFoundException();
		}
		BinaryNodePS<T> bn = (BinaryNodePS<T>) parent;
		if (bn.getRight() != null) {
			throw new ExistingNodeException();
		}
		BinaryNodePS<T> str = (BinaryNodePS<T>) st.getRoot();
		str.clearContainerPointer();
		bn.setRight(str);
		str.setParent(bn);
		modified = true;
		if (((BinaryTreePS<T>) st).isSafe()) {
			size += st.size();
		}
		st.clear(); // this avoid undesired aliasing
		return bn;
	}

	@Override
	public BinaryTreePS<T> removeSubtree(Node<T> sr) {
		return removeSubtree(sr, isSafe());
	}

	public BinaryTreePS<T> removeSubtree(Node<T> sr, boolean safety) {
		if (!(sr instanceof BinaryNodePS)) {
			throw new DifferentNodeTypesException();
		} else if (isSafe() && !contains(sr)) {
			throw new NodeNotFoundException();
		}
		BinaryNodePS<T> bn = (BinaryNodePS<T>) sr;
		BinaryTreePS<T> bt = new BinaryTreePS<T>(safety);
		int oldSize = size();
		bt.root = bn;
		bt.root.setContainer(bt);
		if (bn.getParent() != null && bn == bn.getParent().getLeft()) {
			bn.getParent().setLeft(null);
		} else if (bn.getParent() != null && bn == bn.getParent().getRight()) {
			bn.getParent().setRight(null);
		} else {
			root = null;
		}
		bt.root.setParent(null);
		size = super.size();
		bt.size = oldSize - size;
		return bt;
	}

	@Override
	public Iterator<Node<T>> children(Node<T> n) {
		if (!(n instanceof BinaryNodePS)) {
			throw new DifferentNodeTypesException();
		} else if (isSafe() && !contains(n)) {
			throw new NodeNotFoundException();
		}
		return new ChildrenIt((BinaryNodePS<T>) n);
	}

	@Override
	public Iterator<Node<T>> depthFirstSearchIt(VisitPattern vp) {
		switch (vp) {
		case PRE_ORDER:
			return new PreOrderIt();
		case IN_ORDER:
			return new InOrderIt();
		case POST_ORDER:
			return new PostOrderIt();
		default:
			return new PreOrderIt();
		}
	}

	@Override
	public Iterator<Node<T>> depthFirstSearchIt() {
		return new InOrderIt();
	}

	@Override
	public Iterator<Node<T>> breadthFirstSearchIt() {
		return new BreadthFirstIt();
	}

	private class ChildrenIt implements Iterator<Node<T>> {

		private BinaryNodePS<T> next = null;

		public ChildrenIt(BinaryNodePS<T> n) {
			if (n.getLeft() != null) {
				next = n.getLeft();
			} else if (n.getRight() != null) {
				next = n.getRight();
			} else {
				next = null;
			}
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public BinaryNodePS<T> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			BinaryNodePS<T> n = next;
			if (next == next.getParent().getLeft()) {
				next = next.getParent().getRight();
			} else {
				next = null;
			}
			return n;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	private class PreOrderIt implements Iterator<Node<T>> {

		private LinkedList<Node<T>> stack;

		public PreOrderIt() {
			stack = new LinkedList<Node<T>>();
			stack.push(root);
		}

		@Override
		public boolean hasNext() {
			return !stack.isEmpty();
		}

		@Override
		public BinaryNodePS<T> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			BinaryNodePS<T> n = (BinaryNodePS<T>) stack.pop();
			if (n.getRight() != null) {
				stack.push(n.getRight());
			}
			if (n.getLeft() != null) {
				stack.push(n.getLeft());
			}
			return n;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	private class InOrderIt implements Iterator<Node<T>> {

		private LinkedList<Node<T>> stack;

		public InOrderIt() {
			stack = new LinkedList<Node<T>>();
			BinaryNodePS<T> cursor = root;
			while (cursor != null) {
				stack.push(cursor);
				cursor = cursor.getLeft();
			}
		}

		@Override
		public boolean hasNext() {
			return !stack.isEmpty();
		}

		@Override
		public BinaryNodePS<T> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			BinaryNodePS<T> n = (BinaryNodePS<T>) stack.pop();
			BinaryNodePS<T> cursor = n.getRight();
			while (cursor != null) {
				stack.push(cursor);
				cursor = cursor.getLeft();
			}
			return n;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	private class PostOrderIt implements Iterator<Node<T>> {

		private LinkedList<Node<T>> stack;

		public PostOrderIt() {
			stack = new LinkedList<Node<T>>();
			BinaryNodePS<T> cursor = root;
			while (cursor != null) {
				stack.push(cursor);
				if (cursor.getLeft() != null) {
					cursor = cursor.getLeft();
				} else {
					cursor = cursor.getRight();
				}
			}
		}

		@Override
		public boolean hasNext() {
			return !stack.isEmpty();
		}

		@Override
		public BinaryNodePS<T> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			BinaryNodePS<T> n = (BinaryNodePS<T>) stack.pop();
			BinaryNodePS<T> cursor = (BinaryNodePS<T>) stack.peek();
			if (cursor != null) {
				cursor = cursor.getRight();
			}
			if (cursor == n) {
				return n;
			}
			while (cursor != null) {
				stack.push(cursor);
				if (cursor.getLeft() != null) {
					cursor = cursor.getLeft();
				} else {
					cursor = cursor.getRight();
				}
			}
			return n;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	private class BreadthFirstIt implements Iterator<Node<T>> {

		private LinkedList<BinaryNodePS<T>> next;

		public BreadthFirstIt() {
			next = new LinkedList<BinaryNodePS<T>>();
			if (root != null) {
				next.addFirst(root);
			}
		}

		@Override
		public boolean hasNext() {
			return !next.isEmpty();
		}

		@Override
		public BinaryNodePS<T> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			BinaryNodePS<T> bn = next.removeFirst();
			if (bn.getLeft() != null) {
				next.addLast(bn.getLeft());
			}
			if (bn.getRight() != null) {
				next.addLast(bn.getRight());
			}
			return bn;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

}
