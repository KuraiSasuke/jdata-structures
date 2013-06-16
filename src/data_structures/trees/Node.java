package data_structures.trees;

public abstract class Node<T> {
	
	protected T info = null;
	
	public Node(T info) {
		if (info == null) {
			throw new IllegalArgumentException();
		}
		this.info = info;
	}
	
	public T getInfo() {
		return info;
	}
	
	public void setInfo(T info) {
		if (info == null) {
			throw new IllegalArgumentException();
		}
		this.info = info;
	}
	
	public abstract Tree<T> getContainer();
	
	public abstract void setContainer(Tree<T> container);
	
	public abstract void clearContainerPointer();
	
	@SuppressWarnings("unchecked")
	public boolean equals(Object o) {
		if (!(o instanceof Node)) {
			return false;
		}
		if (o == this) {
			return true;
		}
		Node<T> n = (Node<T>) o;
		return info.equals(n);
	}
	
	public int hashCode() {
		return info.hashCode();
	}
	
	public String toString() {
		return info.toString();
	}
	
}