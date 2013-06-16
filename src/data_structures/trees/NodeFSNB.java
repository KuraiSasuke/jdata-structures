package data_structures.trees;

/**
 * A NodeFSNB is a tree node that contains a pointer to the first son and
 * another to the next brother.
 * 
 * @author Davide
 * 
 * @param <T>
 */
public class NodeFSNB<T> extends Node<T> {

	private NodeFSNB<T> parent = null;
	private NodeFSNB<T> first = null;
	private NodeFSNB<T> next = null;
	private TreeFSNB<T> container = null;

	public NodeFSNB(T info) {
		super(info);
	}

	public NodeFSNB<T> getParent() {
		return parent;
	}

	public void setParent(NodeFSNB<T> parent) {
		this.parent = parent;
	}

	public NodeFSNB<T> getFirst() {
		return first;
	}

	public void setFirst(NodeFSNB<T> first) {
		this.first = first;
	}

	public NodeFSNB<T> getNext() {
		return next;
	}

	public void setNext(NodeFSNB<T> next) {
		this.next = next;
	}

	@Override
	public void setContainer(Tree<T> container) {
		this.container = (TreeFSNB<T>) container;
	}

	@Override
	public TreeFSNB<T> getContainer() {
		NodeFSNB<T> n = this;
		while (n.parent != null) {
			n = n.parent;
		}
		return n.container;
	}

	@Override
	public void clearContainerPointer() {
		container = null;
	}

}
