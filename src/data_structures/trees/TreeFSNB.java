package data_structures.trees;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TreeFSNB<T> extends AbstractTree<T> {

	private NodeFSNB<T> root = null;
	private int size = 0;
	private boolean modified = false;

	public TreeFSNB(boolean safety) {
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
	public NodeFSNB<T> getRoot() {
		return root;
	}

	@Override
	public NodeFSNB<T> getParent(Node<T> n) {
		if (!(n instanceof NodeFSNB)) {
			throw new DifferentNodeTypesException();
		} else if (isSafe() && !contains(n)) {
			throw new NodeNotFoundException();
		}
		return ((NodeFSNB<T>) n).getParent();
	}

	@Override
	public int getGrade(Node<T> n) {
		if (!(n instanceof NodeFSNB)) {
			throw new DifferentNodeTypesException();
		}
		return super.getGrade(n);
	}

	@Override
	public NodeFSNB<T> addRoot(T info) {
		if (root != null) {
			throw new ExistingNodeException();
		}
		root = new NodeFSNB<T>(info);
		root.setContainer(this);
		size++;
		return root;
	}

	@Override
	public NodeFSNB<T> addChild(Node<T> parent, T info) {
		if (!(parent instanceof NodeFSNB)) {
			throw new DifferentNodeTypesException();
		} else if (isSafe() && !contains(parent)) {
			throw new NodeNotFoundException();
		}
		NodeFSNB<T> n = (NodeFSNB<T>) parent;
		NodeFSNB<T> child = new NodeFSNB<T>(info);
		child.setNext(n.getFirst());
		n.setFirst(child);
		child.setParent(n);
		modified = true;
		size++;
		return child;
	}

	@Override
	public NodeFSNB<T> addSubtree(Node<T> parent, Tree<T> st) {
		// TODO pay attention!!
		if (!(parent instanceof NodeFSNB) || !(st instanceof TreeFSNB)) {
			throw new DifferentNodeTypesException();
		} else if (isSafe() && !contains(parent)) {
			throw new NodeNotFoundException();
		}
		NodeFSNB<T> n = (NodeFSNB<T>) parent;
		NodeFSNB<T> str = (NodeFSNB<T>) st.getRoot();
		str.clearContainerPointer();
		str.setNext(n.getFirst());
		n.setFirst(str);
		modified = true;
		if (((TreeFSNB<T>) st).isSafe()) {
			size += st.size();
		}
		st.clear(); // this avoid undesired aliasing
		return n;
	}

	@Override
	public TreeFSNB<T> removeSubtree(Node<T> sr) {
		return removeSubtree(sr, isSafe());
	}

	public TreeFSNB<T> removeSubtree(Node<T> sr, boolean safety) {
		if (!(sr instanceof NodeFSNB)) {
			throw new DifferentNodeTypesException();
		} else if (isSafe() && !contains(sr)) {
			throw new NodeNotFoundException();
		}
		NodeFSNB<T> n = (NodeFSNB<T>) sr;
		TreeFSNB<T> tr = new TreeFSNB<T>(safety);
		int oldSize = size();
		if (n.getParent() != null && n == n.getParent().getFirst()) {
			n.getParent().setFirst(n.getNext());
		} else if (n.getParent() != null) {
			NodeFSNB<T> cursor = n.getParent().getFirst();
			boolean finished = false;
			while (cursor != null && !finished) {
				if (cursor.getNext() == n) {
					cursor.setNext(n.getNext());
					finished = true;
				} else {
					cursor = cursor.getNext();
				}
			}
		} else {
			root = null;
		}
		n.setParent(null);
		n.setNext(null);
		tr.root = n;
		tr.root.setContainer(tr);
		size = super.size();
		tr.size = oldSize - size;
		return tr;
	}

	@Override
	public Iterator<Node<T>> children(Node<T> n) {
		if (!(n instanceof NodeFSNB)) {
			throw new DifferentNodeTypesException();
		} else if (isSafe() && !contains(n)) {
			throw new NodeNotFoundException();
		}
		return new ChildrenIt((NodeFSNB<T>) n);
	}

	@Override
	public Iterator<Node<T>> depthFirstSearchIt(VisitPattern vp) {
		switch (vp) {
		case PRE_ORDER:
			return new PreOrderIt();
		case IN_ORDER:
			throw new UnsupportedOperationException();
		case POST_ORDER:
			return new PostOrderIt();
		default:
			return new PreOrderIt();
		}
	}

	@Override
	public Iterator<Node<T>> depthFirstSearchIt() {
		return new PreOrderIt();
	}

	@Override
	public Iterator<Node<T>> breadthFirstSearchIt() {
		return new BreadthFirstIt();
	}

	private class ChildrenIt implements Iterator<Node<T>> {

		private NodeFSNB<T> next = null;

		public ChildrenIt(NodeFSNB<T> n) {
			next = n.getFirst();
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public NodeFSNB<T> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			NodeFSNB<T> n = next;
			next = next.getNext();
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
		public NodeFSNB<T> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			NodeFSNB<T> n = (NodeFSNB<T>) stack.pop();
			if (n.getNext() != null) {
				stack.push(n.getNext());
			}
			if (n.getFirst() != null) {
				stack.push(n.getFirst());
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
			NodeFSNB<T> cursor = root;
			while (cursor != null) {
				stack.push(cursor);
				cursor = cursor.getFirst();
			}
		}

		@Override
		public boolean hasNext() {
			return !stack.isEmpty();
		}

		@Override
		public NodeFSNB<T> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			NodeFSNB<T> n = (NodeFSNB<T>) stack.pop();
			NodeFSNB<T> cursor = n.getNext();
			while (cursor != null) {
				stack.push(cursor);
				cursor = cursor.getFirst();
			}
			return n;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	private class BreadthFirstIt implements Iterator<Node<T>> {

		private LinkedList<NodeFSNB<T>> next;

		public BreadthFirstIt() {
			next = new LinkedList<NodeFSNB<T>>();
			if (root != null) {
				next.addFirst(root);
			}
		}

		@Override
		public boolean hasNext() {
			return !next.isEmpty();
		}

		@Override
		public NodeFSNB<T> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			NodeFSNB<T> n = next.removeFirst();
			NodeFSNB<T> cursor = n.getFirst();
			while (cursor != null) {
				next.addLast(cursor);
				cursor = cursor.getNext();
			}
			return n;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

}
