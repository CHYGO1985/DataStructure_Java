/**
 * This is the implementation for AVLTree, it contains methods include:
 * 1. boolean insertNode();
 * 
 * 2. void rebalance();
 * 
 * 3. removeNode();
 *
 * 4. void inOrderTraverse();
 *
 * 5. int getHeight(): 
 * 
 * 
 * Note: the conceot of right and left rotate
 * @author jingjiejiang Nov 20, 2016
 * @history
 */
public class AVLTree {
	
	public static class AVLTreeNode {
		
		int val;
		// biFactor = height of left - height of right
		int biFactor;
		AVLTreeNode leftNode;
		AVLTreeNode rightNode;
		// parent is to assist rebalance
		
		AVLTreeNode parent;
		
		public AVLTreeNode(int val, int biFactor, AVLTreeNode leftNode
				, AVLTreeNode rightNode, AVLTreeNode parent) {
			this.val = val;
			this.biFactor = biFactor;
			this.leftNode = leftNode;
			this.rightNode = rightNode;
			this.parent = null;
		}
	}
	
	public static void inOrderTraverse(AVLTreeNode root) {
		
		if (null == root) {
			return;
		}
		
		inOrderTraverse(root.leftNode);
		System.out.print(root.val + " ");
		inOrderTraverse(root.rightNode);
	}
	
	/**
	 * Insert a key into the AVL tree, if it causes the tree becomes unbalanced, 
	 * rebalance the tree.
	 * 
	 * @param root
	 * @param parent
	 * @param key
	 * @return if a key is inserted into the AVL tree successfully, return true;
	 * if it fails, return false.
	 *
	 */
	public static boolean insertNode(AVLTreeNode root, AVLTreeNode parent, int key) {
	
		if (null == root) {
			
			root = new AVLTreeNode(key, 0, null, null, parent);
			// unless insert a root, after insert a non-root node, identify the
			// node that is unbalanced (if it exists)
			AVLTreeNode unBalNode = null;
			if (null != parent) {
				
				// Set balance factor of all the ancestor of the inserted node
				// and return the unbalanced one as soon as identifying it.
			    unBalNode = findUnbalancedNode(parent);
			}
			
			// there exist unbalanced node, rebalance the AVL tree
			if (null != unBalNode) {
			    rebalance(unBalNode);
			}
			return true;
		}
		else if (root.val == key) {
			return false;
		}
		// key is smaller than root, then search the left sub-tree of root
		else if (key < root.val) {
			return insertNode(root.leftNode, root, key);
		}
		// key is bigger than root, search the right sub-tree of root
		else {
			return insertNode(root.rightNode, root, key);
		}
	}
	
	public static boolean removeNode(AVLTreeNode root, int key) {
		
		if (null == root) {
			return false;
		}
		
		if (key == root.val) {
			removeAVLTreeNode (root);
			return true;
		}
		else if (key < root.val){
			return removeNode(root.leftNode, key);
		}
		else {
			return removeNode(root.rightNode, key);
		}
	}
	
	private static void removeAVLTreeNode(AVLTreeNode nodeToRemove) {
		
		// there are two case when remove a node: 
		// 1. nodeToRemove is leaf or only has one child
		// 1.1 nodeToRemoves is a root 1.2 nodeToRemoves is not root
		// 2. nodeToRemove has two children.
		// whether nodeToRemove is a root does not matter in this case.
		
		if (null == nodeToRemove.leftNode || null == nodeToRemove.rightNode) {
			
			// the remove is done by replace the node with one of its empty child
			replaceNodeWithChild(nodeToRemove);
		}
		// if case 2, rebuild the AVLTree
		else {
			rebuildAVLTree(nodeToRemove);
		}
	}
	
	/**
	 * Remove a node from AVL tree that has non-empty left child and non-empty right child.
	 * 
	 * @param nodeToRemove
	 */
	private static void rebuildAVLTree(AVLTreeNode nodeToRemove) {
		
		// Rebuild a AVLTree
		// 1) same as BST, use the rightmost child of nodeToRemove's left child A
		// to replace nodeToRemove
		// 2) set the balance factor start from A to root
		// 3) if findUnbalancedNode() return a null node
		// 4) rebalance() the node
		
		AVLTreeNode original = nodeToRemove;
		AVLTreeNode parent = nodeToRemove.parent;
		// the node from reset balance factor
		AVLTreeNode checkPoint = null;
		nodeToRemove = nodeToRemove.leftNode;
		
		if (null == nodeToRemove.rightNode) {
			
			nodeToRemove.leftNode.parent = nodeToRemove.parent;
			// exclude the case that nodeToRemove is a root node
			if (null != parent) {
				
				if (parent.leftNode == original) {
					parent.leftNode = nodeToRemove;
				}
				else {
					parent.rightNode = nodeToRemove;
				}
			}
			checkPoint = nodeToRemove;
		}
		else {
			
			while (null != nodeToRemove.rightNode) {
				
				nodeToRemove = nodeToRemove.rightNode;
			}
			
			original.val = nodeToRemove.val;
			nodeToRemove.parent.rightNode = null;
			checkPoint = nodeToRemove.parent;
			nodeToRemove.parent = null;
		}
		
		// check whether there exists unbalanced node.
		AVLTreeNode unBalNode = findUnbalancedNode(checkPoint);
		
		if (null != unBalNode) {
			rebalance(unBalNode);
		}
	}
	
	private static void replaceNodeWithChild(AVLTreeNode nodeToReplace) {
		
		// the condition also include the case of having two empty children.
		if (null == nodeToReplace.leftNode) {
			
			if (nodeToReplace != null) {
				nodeToReplace.rightNode.parent = nodeToReplace.parent;
			}
			nodeToReplace = nodeToReplace.rightNode;

		}
		else {
			nodeToReplace.leftNode.parent = nodeToReplace.parent;
			nodeToReplace = nodeToReplace.leftNode;
		}
		
	}
	
	/**
	 * Set the balance factor of a node.
	 * 
	 * @param startNode
	 */
	private static void setBalanceFactor(AVLTreeNode startNode) {
		
		if (null == startNode)
			return ;
		
		// calculate the height of the right sub-tree and left sub-tree
		// of startNode
		int lHeight = calTreeHeight(startNode.leftNode);
		int rHeight = calTreeHeight(startNode.rightNode);
		
		startNode.biFactor = lHeight - rHeight;
	}
	
	/**
	 * Calculate the height of a tree.
	 *
	 * @param root
	 * @return
	 */
	public static int calTreeHeight(AVLTreeNode root) {
		
		if (null == root)
		    return 0;
		/*
		if (null == root.rightNode) {
		    return 1 + calTreeHeight(root.leftNode);	
		}
		else if (null == root.leftNode) {
			return 1 + calTreeHeight(root.rightNode);
		}
		else {
			
			return 1 + Math.max(calTreeHeight(root.leftNode)
					, calTreeHeight(root.rightNode));
		}
		*/
		
		// I think the algorithm can be optiimsed to one line
		return 1 + Math.max(calTreeHeight(root.leftNode)
				, calTreeHeight(root.rightNode));
	}
	
	/**
	 * Find the first node that is unbalanced. If there does not exist one
	 * return null.
	 * 
	 * @param nodeToCheck
	 * @return The unbalanced node, if there is none, return null.
	 */
	private static AVLTreeNode findUnbalancedNode(AVLTreeNode nodeToCheck) {
		
		
		if (null == nodeToCheck) {
			return null;
		}
		
		// Although there is no chance that the parent of the inserted node
		// become unbalanced, it still need to be set the biFacotr, cause
		// when rebalance the node, the four cases require checking parent
		// and grandparent to decide whether to rotate or double rotate
		setBalanceFactor(nodeToCheck);
		
		// if find an unbalanced node, rebalance it and then reset the rest of
		// the nodes' balance factor.
		if (2 == nodeToCheck.biFactor
				|| -2 == nodeToCheck.biFactor) {
			return nodeToCheck;
		}
		else {
			return findUnbalancedNode(nodeToCheck.parent);
		}
	}
	
	/**
	 * Rebalance the unbalanced node unBalNode and recalculate its balance factor
	 * and all its parents' balance factor.
	 * 
	 * @param unBalNode
	 *
	 */
	private static void rebalance(AVLTreeNode unBalNode) {
		
		// check 4 cases of unbalance
		// 1. left - right = 2
		if (2 == unBalNode.biFactor) {
		
			// 1.1 left - right >=v0, all left children of unbalanced node
			// are not null
			if (unBalNode.leftNode.biFactor >= 1) {
				
				rotateRight(unBalNode);
			}
			// 1.2 left - right = -1, need to do right and left rotate
			else {
				rotateLeftAndRight(unBalNode);
			}
		}
		// 2. left - right = -2
		else if (-2 == unBalNode.biFactor) {
		
			if (-1 == unBalNode.rightNode.biFactor) {
				rotateLeft(unBalNode);
			}
			else {
				rotateRightAndLeft(unBalNode);
			}
		}
		
		// Reset the balance factor of the unBalNode and all its parents'.
		while (null != unBalNode) {
			
			setBalanceFactor(unBalNode);
			unBalNode = unBalNode.parent;
		}
	}
	
	/**
	 * Rotate unBalNode, make it as the left child of its original left child.
	 * 
	 * @param unBalNode the node that has abs(balance factor) == 2.
	 */
	private static void rotateRight(AVLTreeNode unBalNode) {
		
		AVLTreeNode parent = unBalNode.parent;
		AVLTreeNode leftChild = unBalNode.leftNode; 
		
		leftChild.parent = parent;
		
		// Before assign unBalNode to leftChild, save the original rchild
		// of leftChild 
		unBalNode.leftNode = leftChild.rightNode;
		leftChild.rightNode = unBalNode;
		
		// *** Note: rightNode can be null
		if (leftChild.rightNode != null) {
		    leftChild.rightNode.parent = unBalNode;
		}
		unBalNode.parent = leftChild;
		
		if (null != parent) {
			// *** Note: unBalNode can be right / left child of its parent
			if (unBalNode == parent.leftNode) {
			    parent.leftNode = leftChild;
			}
			else {
				parent.rightNode = leftChild;
			}
		}
	}
	
	/**
	 * Rotate the unbalanced node: unBalNode, make it as the left child of its 
	 * original left child.
	 * 
	 * @param unBalNode
	 */
	private static void rotateLeft(AVLTreeNode unBalNode) {
		
		AVLTreeNode parent = unBalNode.parent;
		AVLTreeNode rightChild = unBalNode.rightNode;
		
	    rightChild.parent = parent;
	    unBalNode.leftNode = rightChild.leftNode;
	    rightChild.leftNode = unBalNode;
	    
	    if (null != rightChild.leftNode) {
	    	rightChild.leftNode.parent = unBalNode;
	    }
	    
	    unBalNode.parent = rightChild;
	    
	    if (null != parent) {
	    	if (unBalNode == parent.leftNode) {
	    		parent.leftNode = rightChild;
	    	}
	    	else {
	    		parent.rightNode = rightChild;
	    	}
	    }
	}
	
	/**
	 * Left rotate the left child of unBalNode and then right rotate the
	 * unbalanced node - unBalNode.
	 *
	 * @param unBalNode
	 */
	private static void rotateLeftAndRight(AVLTreeNode unBalNode) {
		
		// Note first step is the left child of unBalNode which does the actual
		// left rotate.
		rotateLeft(unBalNode.leftNode);
		rotateRight(unBalNode);
	}
	
	/**
	 * Right rotate the right child of unBalNode and then left rotate the
	 * unbalanced node - unBalNode.
	 * 
	 * @param unBalNode
	 */
	private static void rotateRightAndLeft(AVLTreeNode unBalNode) {
		
		rotateRight(unBalNode.rightNode);
		rotateLeft(unBalNode);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
