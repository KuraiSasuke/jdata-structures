package data_structures.trees;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractTree<T> implements Tree<T> {

	private boolean safety;

	public AbstractTree(boolean safety) {
		this.safety = safety;
	}

	public boolean isSafe(){
		return safety;
	}
	
	@SuppressWarnings("unused")
	@Override
	public int size() {
		int c = 0;
		for (Node<T> n : this) {
			c++;
		}
		return c;
	}

	@Override
	public void clear() {
		Iterator<Node<T>> it = iterator();
		while (it.hasNext()) {
			it.next();
			it.remove();
		}
	}

	@Override
	public boolean isEmpty() {
		return iterator().hasNext();
	}

	@Override
	public Node<T> getRoot() {
		Iterator<Node<T>> it = iterator();
		return (it.hasNext()) ? it.next() : null;
	}

	@Override
	public Node<T> getParent(Node<T> n) {
		if (safety && !contains(n)) {
			throw new NodeNotFoundException();
		} else if (n == null) {
			return null;
		}
		for (Node<T> tn : this) {
			Iterator<Node<T>> children = children(tn);
			while (children.hasNext()) {
				Node<T> child = children.next();
				if (child.equals(n)) {
					return tn;
				}
			}
		}
		return null;
	}

	@Override
	public int getGrade(Node<T> n) {
		if (safety && !contains(n)) {
			throw new NodeNotFoundException();
		} else if (n == null) {
			return 0;
		}
		int c = 0;
		Iterator<Node<T>> children = children(n);
		while (children.hasNext()) {
			children.next();
			c++;
		}
		return c;
	}

	@Override
	public boolean contains(Node<T> n) {
		if (n == null) {
			return false;
		}
		return n.getContainer() == this;
	}

	@Override
	public boolean contains(T info) {
		if (info == null) {
			return false;
		}
		for (Node<T> n : this) {
			if (info.equals(n.info)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<Node<T>> get(T info) {
		List<Node<T>> nodes = new LinkedList<Node<T>>();
		if (info == null) {
			return nodes;
		}
		for (Node<T> tn : this) {
			if (info.equals(tn.getInfo())) {
				nodes.add(tn);
			}
		}
		return nodes;
	}
	
	@Override
	public Iterator<Node<T>> iterator() {
		// TODO
		//return depthFirstSearchIt(VisitPattern.PRE_ORDER);
		//return depthFirstSearchIt(VisitPattern.IN_ORDER);
		//return depthFirstSearchIt(VisitPattern.POST_ORDER);
		return breadthFirstSearchIt();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Tree)) {
			return false;
		}
		if (o == this) {
			return true;
		}
		Tree<T> tr = (Tree<T>) o;
		if (size() != tr.size()) {
			return false;
		}
		Iterator<Node<T>> it1 = iterator();
		Iterator<Node<T>> it2 = tr.iterator();
		while (it1.hasNext()) {
			Node<T> n1 = it1.next();
			Node<T> n2 = it2.next();
			if (!n1.equals(n2)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		int h = 0;
		final int MULT = 29;
		Iterator<Node<T>> it = iterator();
		while (it.hasNext()) {
			Node<T> n = it.next();
			h = h * MULT + n.hashCode();
		}
		return h;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(200);
		sb.append('[');
		Iterator<Node<T>> it = iterator();
		while (it.hasNext()) {
			sb.append(it.next().toString());
			if (it.hasNext()) {
				sb.append(',');
				sb.append(' ');
			}
		}
		sb.append(']');
		return sb.toString();
	}
	
}
