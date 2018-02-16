package redblacktree;

public class TestRedBlackTree {
	public static void main(String[] args) {
		RedBlackTree tree = new RedBlackTree();
		tree.insertNode(50);
		tree.insertNode(5);
		tree.insertNode(10);
		tree.insertNode(15);
		tree.insertNode(3);
		tree.insertNode(6);
		tree.insertNode(1);
//		tree.rolateRight(tree.findNode(5));
		System.out.println(tree.breathFirst());

	}
}
