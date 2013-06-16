package data_structures.test;

import data_structures.trees.*;

public class TestTreeFSNB {

	final static int ADD_NUM = 17;
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		TreeFSNB<Integer> tree = new TreeFSNB<>(true);
		NodeFSNB<Integer>[] nodes = (NodeFSNB<Integer>[]) new NodeFSNB[ADD_NUM];
		
		nodes[0] = tree.addRoot(0);
		nodes[1] = tree.addChild(nodes[0], 1);
		nodes[2] = tree.addChild(nodes[0], 2);
		nodes[3] = tree.addChild(nodes[0], 3);
		nodes[4] = tree.addChild(nodes[0], 4);
		nodes[5] = tree.addChild(nodes[1], 5);
		nodes[6] = tree.addChild(nodes[2], 6);
		nodes[7] = tree.addChild(nodes[2], 7);
		nodes[8] = tree.addChild(nodes[4], 8);
		nodes[9] = tree.addChild(nodes[6], 9);
		nodes[10] = tree.addChild(nodes[6], 10);
		nodes[11] = tree.addChild(nodes[8], 11);
		nodes[12] = tree.addChild(nodes[8], 12);
		nodes[13] = tree.addChild(nodes[8], 13);
		nodes[14] = tree.addChild(nodes[10], 14);
		nodes[15] = tree.addChild(nodes[12], 15);
		nodes[16] = tree.addChild(nodes[12], 16);
		
		System.out.println("tree: " + tree);
		System.out.println("tree size: " + tree.size());
		
		TreeFSNB<Integer> tr1 = tree.removeSubtree(nodes[12]);
		
		System.out.println("tree: " + tree);
		System.out.println("tree size: " + tree.size());
		
		System.out.println("tr1: " + tr1);
		System.out.println("tr1 size: " + tr1.size());
		
		
		tree.addSubtree(nodes[2], tr1);
		
		System.out.println();
		System.out.println("tree: " + tree);
		System.out.println("tree size: " + tree.size());
		
		System.out.println("tr1: " + tr1);
		System.out.println("tr1 size: " + tr1.size());
		
	}

}
