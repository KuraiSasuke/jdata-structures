package data_structures.test;

import data_structures.trees.*;
import java.util.*;

public class TestBinaryTreePS {

	final static int ADD_NUM = 14;

	static BinaryTreePS<Integer> safeBin = new BinaryTreePS<>(true);
	static BinaryTreePS<Integer> unsafeBin = new BinaryTreePS<>(false);

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		int addedElements1 = 0;
		int addedElements2 = 0;
		boolean isEmpty = true;

		// testing size when trees are empty
		checkSize(addedElements1);

		// testing isEmpty when trees are empty
		checkIsEmpty(isEmpty);

		// testing add, remove and contains
		LinkedList<BinaryNodePS<Integer>> ll1 = new LinkedList<>();
		BinaryNodePS<Integer> cursor1 = safeBin.addRoot(0);
		ll1.addFirst(cursor1);
		int i = 1;
		while (cursor1 != null && i <= ADD_NUM) {
			cursor1 = ll1.removeLast();
			ll1.addFirst(safeBin.addLeftChild(cursor1, i));
			i++;
			addedElements1++;
			ll1.addFirst(safeBin.addRightChild(cursor1, i));
			i++;
			addedElements1++;
		}

		LinkedList<BinaryNodePS<Integer>> ll2 = new LinkedList<>();
		BinaryNodePS<Integer> cursor2 = unsafeBin.addRoot(0);
		ll2.addFirst(cursor2);
		i = 1;
		while (cursor2 != null && i <= ADD_NUM) {
			cursor2 = ll2.removeLast();
			ll2.addFirst(unsafeBin.addLeftChild(cursor2, i));
			i++;
			addedElements2++;
			ll2.addFirst(unsafeBin.addRightChild(cursor2, i));
			i++;
			addedElements2++;
		}
		isEmpty = false;
		
//		if (addedElements1 != addedElements2) {
//			throw new RuntimeException("Error in addedElements");
//		}
		
		checkSize(addedElements1 + 1);
		
		checkToString();
		
		checkClear();
		
		BinaryNodePS<Integer> n0 = safeBin.addRoot(0);
		BinaryNodePS<Integer> n1 = safeBin.addLeftChild(n0, 1);
		BinaryNodePS<Integer> n2 = safeBin.addRightChild(n0, 2);
		BinaryNodePS<Integer> n3 = safeBin.addRightChild(n1, 3);
		BinaryNodePS<Integer> n4 = safeBin.addLeftChild(n3, 4);
		BinaryNodePS<Integer> n5 = safeBin.addLeftChild(n4, 5);
		BinaryNodePS<Integer> n6 = safeBin.addRightChild(n5, 6);
		BinaryNodePS<Integer> n7 = safeBin.addRightChild(n6, 7);
		BinaryNodePS<Integer> n8 = safeBin.addLeftChild(n7, 8);
		BinaryNodePS<Integer> n9 = safeBin.addRightChild(n7, 9);
		BinaryNodePS<Integer> n10 = safeBin.addLeftChild(n2, 10);
		BinaryNodePS<Integer> n11 = safeBin.addRightChild(n2, 11);
		BinaryNodePS<Integer> n12 = safeBin.addRightChild(n10, 12);
		BinaryNodePS<Integer> n13 = safeBin.addLeftChild(n11, 13);
		BinaryNodePS<Integer> n14 = safeBin.addRightChild(n13, 14);
		BinaryNodePS<Integer> n15 = safeBin.addLeftChild(n14, 15);
		BinaryNodePS<Integer> n16 = safeBin.addRightChild(n4, 16);
		BinaryNodePS<Integer> n17 = safeBin.addRightChild(n11, 17);
		BinaryNodePS<Integer> n18 = safeBin.addLeftChild(n1, 18);
	
		unsafeBin.addRoot(101);
		
		System.out.println();
		System.out.println();
		
		checkToString();
		
		checkSize(-1);
		
		BinaryTreePS<Integer> tr = safeBin.removeSubtree(n5);
		
		checkSize(-1);

		System.out.println("tr size: " + tr.size());
		
		System.out.println("tr: " + tr.toString());
		
		checkToString();
		
		System.out.println("tr root container: " + tr.getRoot().getContainer());
		
		Iterator<Node<Integer>> children = safeBin.children(n2);
		safeBin.addLeftChild(children.next(), 999);
		
		checkToString();
		
		checkSize(-1);
		
		safeBin.addRightSubtree(n3, tr);
		
		checkToString();
		
		checkSize(-1);
		
	}

	public static void checkSize(int expectedResult) {
		System.out.println("safeBin size: " + safeBin.size());
		System.out.println("unsafeBin size: " + unsafeBin.size());
		System.out.println("Expected: " + expectedResult);
	}

	private static void checkIsEmpty(boolean expectedResult) {
		System.out.println("safeBin isEmpty: " + safeBin.isEmpty());
		System.out.println("unsafeBin isEmpty: " + unsafeBin.isEmpty());
		System.out.println("Expected: " + expectedResult);
	}

	private static void checkToString() {
		System.out.println("safeBin: " + safeBin);
		System.out.println("unsafeBin: " + unsafeBin);
	}
	
	private static void checkClear() {
		safeBin.clear();
		unsafeBin.clear();
	}
	
}
