package redblacktree;

import java.util.LinkedList;
import java.util.Queue;

class Node {

	public static int RED = 0;
	public static int BLACK = 1;

	int key;
	Node left;
	Node right;
	Node parent;
	int color;

	public Node() {
		super();
	}

	public Node(int x) {
		this.key = x;
		color = RED;
	}

	public Node(int key, Node left, Node right, Node parent, int color) {
		super();
		this.key = key;
		this.left = left;
		this.right = right;
		this.parent = parent;
		this.color = color;
	}

	public static int getRED() {
		return RED;
	}

	public static void setRED(int rED) {
		RED = rED;
	}

	public static int getBLACK() {
		return BLACK;
	}

	public static void setBLACK(int bLACK) {
		BLACK = bLACK;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return key + " " + (color == RED ? "Red" : "Black");
	}

}

public class RedBlackTree {
	Node root;

	public String breathFirst() {
		Queue<Node> queue = new LinkedList<>();
		Queue<Integer> levels = new LinkedList<>();
		queue.add(root);
		Node node;
		levels.add(1);
		int nodeLevel;
		int currentLevel = 1;
		StringBuffer sb = new StringBuffer();
		while (!queue.isEmpty()) {
			node = queue.poll();
			nodeLevel = levels.poll();
			if (nodeLevel != currentLevel) {
				sb.append("\n");
				currentLevel++;
			}
			sb.append(node).append(" ");
			if (node.left != null) {
				queue.add(node.left);
				levels.add(nodeLevel + 1);
			}
			if (node.right != null) {
				queue.add(node.right);
				levels.add(nodeLevel + 1);
			}
		}
		return sb.toString();
	}

	public Node findNode(Node node, int val) {
		if (node.key == val)
			return node;
		else if (val < node.key)
			return findNode(node.left, val);
		else
			return findNode(node.right, val);
	}

	public Node findNode(int val) {
		return findNode(root, val);
	}

	public void rolateLeft(Node node) {
		Node parent = node.getParent();
		if (parent != null) {
			Node ancestor = parent.parent;
			if (ancestor != null) {
				if (parent.key < ancestor.key) {
					ancestor.left = node;
					node.parent = ancestor;
				} else {
					ancestor.right = node;
					node.parent = ancestor;
				}
			} else {
				root = node;
				node.parent = null;
			}
			parent.right = node.left;
			if (node.left != null) {
				node.left.parent = parent;
			}
			node.left = parent;
			parent.parent = node;
		}
	}

	public void rolateRight(Node node) {
		Node parent = node.getParent();
		if (parent != null) {
			Node ancestor = parent.parent;
			if (ancestor != null) {
				if (parent.key < ancestor.key) {
					ancestor.left = node;
					node.parent = ancestor;
				} else {
					ancestor.right = node;
					node.parent = ancestor;
				}
			} else {
				root = node;
				node.parent = null;
			}
			parent.left = node.right;
			if (node.right != null) {
				node.right.parent = parent;
			}
			node.right = parent;
			parent.parent = node;
		}
	}

	public void fixUp(Node node) {
		while (node.color == Node.RED && 
				(node.parent != null && node.parent.color == Node.RED)) {
			changeParentAndUncleIsRed(node);
			changeNewNodeAndParentOnOneSlideHasRedColor(node);
			changeNewNodeAndParentOnTwoSlideHasRedColor(node);
		}
	}

	public void changeParentAndUncleIsRed(Node node) {
		Node parent = node.getParent();
		Node ancestor = null;
		if (parent != null) {
			ancestor = parent.getParent();
			Node uncle = null;
			if (ancestor != null) {
				uncle = parent.key < ancestor.key ? ancestor.right : ancestor.left;
				if (uncle != null) {
					if (uncle.color == Node.RED && parent.color == Node.RED) {
						uncle.color = parent.color = Node.BLACK;
					}
				}
			}
		}
	}

	public void changeNewNodeAndParentOnOneSlideHasRedColor(Node node) {
		Node parent = node.getParent();
		Node ancestor = null;
		if (parent != null) {
			if (node.color == Node.RED && parent.color == Node.RED) {
				ancestor = parent.getParent();
				if (ancestor != null) {
					if (((ancestor.key - parent.key) * (parent.key - node.key) > 0)) {
						ancestor.color = node.color = Node.RED;
						parent.color = Node.BLACK;
						if (parent.key < ancestor.key) {
							rolateRight(parent);
						} else {
							rolateLeft(parent);
						}
					}
				}
			}
		}
	}

	public void changeNewNodeAndParentOnTwoSlideHasRedColor(Node node) {
		Node parent = node.getParent();
		Node ancestor = null;
		if (parent != null) {
			if (node.color == Node.RED && parent.color == Node.RED) {
				ancestor = parent.getParent();
				if (ancestor != null) {
					if (((ancestor.key - parent.key) * (parent.key - node.key) < 0)) {
						if (node.key < parent.key) {
							rolateRight(node);
						} else {
							rolateLeft(node);
						}
						changeNewNodeAndParentOnOneSlideHasRedColor(parent);
					}
				}
			}
		}
	}

	public void insertNode(int val) {
		if (root == null) {
			root = new Node(val);
			root.color = Node.BLACK;
		} else {
			Node i = root, p = null;
			while (i != null) {
				p = i;
				if (val < i.key) {
					i = i.left;
				} else {
					i = i.right;
				}
			}
			Node newNode = new Node(val);
			if (p != null) {
				if (val < p.key) {
					p.left = newNode;
					newNode.parent = p;
				} else {
					p.right = newNode;
					newNode.parent = p;
				}
				fixUp(newNode);
			}
		}
	}
}
