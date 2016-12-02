
public class BinarySearchTree {
	
	public static class BSTreeNode {
		
		int val;
		BSTreeNode leftNode;
		BSTreeNode rightNode;
		
		public BSTreeNode(int val, BSTreeNode leftNode, BSTreeNode rightNode) {
			this.val = val;
			this.leftNode = leftNode;
			this.rightNode = rightNode;
		}
	}
	
	public static void inOrderTraverse(BSTreeNode root) {
		
		if (null == root)
			return ;
		
		inOrderTraverse(root.leftNode);
		System.out.print(root.val + " ");
		inOrderTraverse(root.rightNode);
	}
	
	/**
	 * 
	 * @param root
	 * @return 0: left is empty; 1: right is empty; 2: both side is empty;
	 *         3: both non-empty.
	 */
	public static int checkRootChildren(BSTreeNode root) {
		
		if (null == root.leftNode && null == root.rightNode) {
			return 2;
		}
		else if (null == root.leftNode) {
			return 0;
		}
		else if (null == root.rightNode) {
			return 1;
		}
		else {
			return 3;
		}
	}
	
	public static BSTreeNode getNode(BSTreeNode root, int key) {
		
		if (null == root)
			return null;
		
		if (root.val == key) {
			return root;
		}
		else if (key < root.val) {
		    return getNode(root.leftNode, key);
		}
		else{
			return getNode(root.rightNode, key);
		}
	}
	
	/**
	 * Delete a node from the BST.
	 * @param parent
	 * @param root
	 * @param key
	 * @param indicator
	 * @return
	 */
	public static boolean deleteNode (BSTreeNode parent, BSTreeNode root
			, int key, boolean indicator) {
		
		// to delete a node, find the node first
		if (null == root) {
			return false;
		}
		
		if (root.val == key) {
			return deleteNodeHelper(parent, root, indicator);
		}
		else if (key < root.val) {
			return deleteNode(root, root.leftNode, key, false);
		}
		else {
			return deleteNode(root, root.rightNode, key, true);
		}
		
	}
	
	/**
	 * Delete the root of BST.
	 * @param delete
	 * @return
	 */
	private static boolean deleteRoot(BSTreeNode delete) {
		
		BSTreeNode root = delete;
		BSTreeNode tempFather = delete;
		
		// *** remember to check whether there is one side is empty 
		int rootChildren = checkRootChildren(delete);
		if (2 == rootChildren) {
			delete = null;
		}
		// if left sub tree is empty
		else if (0 == rootChildren) {
			
			delete = delete.rightNode;
			if (null == delete.leftNode) {
				return true;
			}
			else {
				while (null != delete.leftNode) {
					tempFather = delete;
					delete = delete.leftNode;
				}
				tempFather.leftNode = delete.rightNode;
			}
			
			root.val = delete.val;
				 
		}
		// if left sub tree is not empty
		else{
			
			delete = delete.leftNode;
			
			if (null == delete.rightNode) {
				root.leftNode = delete.leftNode;
			}
			else {
				while (null != delete.rightNode) {
					tempFather = delete;
				    delete = delete.rightNode;
				}
				tempFather.rightNode = delete.leftNode;
			}
			
			root.val = delete.val;
		}
		    
		return true;
	}
	
	/**
	 * 
	 * @param parent
	 * @param delete
	 * @param key
	 * @param childIndicator false: left, true: right
	 * @return
	 */
	private static boolean deleteNodeHelper(BSTreeNode parent, BSTreeNode delete
			, boolean childIndicator) {
		
		
		// if the one to delete is root, find the rightmost in the left tree of root
		// replace it for root.
		if (null == parent) {
			
			return deleteRoot(delete);
		}
			
		// Handling null == delete.left/right, the code an also handle a leaf node, so
		// there is no need to check the code of a leaf node (both sides are empty).
		if (null == delete.leftNode) {
			if (false == childIndicator) {
				parent.leftNode = delete.rightNode;
			}
			else {
				parent.rightNode = delete.rightNode;
			}
			
			return true;
		}
		else if (null == delete.rightNode) {
			if (false == childIndicator) {
				parent.leftNode = delete.leftNode;
			}
			else {
				parent.rightNode = delete.leftNode;
			}
		}
		else {
			// if both child are not empty, find the rightmost node in the left tree of
			// delete (which is the one before delete in inorder traverse.)
			BSTreeNode tempFather = delete;
			BSTreeNode tempRight = delete.rightNode;
			BSTreeNode tempLeft = delete.leftNode;
		    delete = delete.leftNode;
		    
		    while (null != delete.rightNode) {
		    	tempFather = delete;
		    	delete = delete.rightNode;
		    }
		    
		    // this sentence is the actual delete, and if there is a left tree of the
		    // deleted one, add to the parent.
		    tempFather.rightNode = delete.leftNode;
		    
		    // if the original left/right sub tree is not the replacement
		    // add the original left sub tree and right sub tree to the replace node
		    if (delete.val != tempRight.val)
		        delete.rightNode = tempRight;
		    if (delete.val != tempLeft.val)
		        delete.leftNode = tempLeft;
		    
		    if (false == childIndicator) {
		    	parent.leftNode = delete;
		    }
		    else {
		    	parent.rightNode = delete;
		    }
		    
		    return true;
		}
		
		return false;
	}

	public static void main(String[] args) {
		
		/**
		 * Test case 1:
		 *             70
		 *            /  \
		 *          67   105
		 *         /     /  \
		 *       46    100   115
		 *            / \     /  
		 *           99 104  110 
		 *               /   / \
		 *             103 108 112
		 **/
		/*
        BSTreeNode leaf1 = new BSTreeNode(46, null, null);
        BSTreeNode leaf2 = new BSTreeNode(99, null, null);
        BSTreeNode leaf3 = new BSTreeNode(103, null, null);
        BSTreeNode leaf4 = new BSTreeNode(108, null, null);
        BSTreeNode leaf5 = new BSTreeNode(112, null, null);
        
        BSTreeNode node1 = new BSTreeNode(67, leaf1, null);
        BSTreeNode node2 = new BSTreeNode(104, leaf3, null);
        BSTreeNode node3 = new BSTreeNode(110, leaf4, leaf5);
        BSTreeNode node4 = new BSTreeNode(100, leaf2, node2);
        BSTreeNode node5 = new BSTreeNode(115, node3, null);
        BSTreeNode node6 = new BSTreeNode(105, node4, node5);
        
        BSTreeNode root = new BSTreeNode(70, node1, node6);
        */
        /*
		 * Test case 2:
		 *             70
		 *               \
		 *               105
		 *               /  \
		 *             100   115
		 *            / \     /  
		 *           99 104  110 
		 *               /   / \
		 *             103 108 112
		 */
        BSTreeNode leaf1 = new BSTreeNode(46, null, null);
        BSTreeNode leaf2 = new BSTreeNode(99, null, null);
        BSTreeNode leaf3 = new BSTreeNode(103, null, null);
        BSTreeNode leaf4 = new BSTreeNode(108, null, null);
        BSTreeNode leaf5 = new BSTreeNode(112, null, null);
        
        BSTreeNode node1 = new BSTreeNode(67, leaf1, null);
        BSTreeNode node2 = new BSTreeNode(104, leaf3, null);
        BSTreeNode node3 = new BSTreeNode(110, leaf4, leaf5);
        BSTreeNode node4 = new BSTreeNode(100, leaf2, node2);
        BSTreeNode node5 = new BSTreeNode(115, node3, null);
        BSTreeNode node6 = new BSTreeNode(105, node4, node5);
        
        BSTreeNode root = new BSTreeNode(70, null, node6);
        // test inorder traverse -- pass
        // inOrderTraverse(root);
        
        // test find a node in a tree: leaf, root, non exist, in the middle -- pass
//        System.out.println(getNode(root, 70));
//        System.out.println(getNode(root, 46));
//        System.out.println(getNode(root, 115));
//        System.out.println(getNode(root, 98));
        
        // test case 1:
        // test delete a node in a tree: leaf, root, non exist, in the middle() - pass
//        deleteNode(null, root, 70, false);
//        inOrderTraverse(root);
        
//        deleteNode(null, root, 46, false);
//        inOrderTraverse(root);
        
//        deleteNode(null, root, 103, false);
//        inOrderTraverse(root);
        
//        System.out.println(deleteNode(null, root, 43));
        
//        deleteNode(null, root, 100, false);
//        inOrderTraverse(root);
        
//        deleteNode(null, root, 110, false);
//        inOrderTraverse(root);
        
//        deleteNode(null, root, 105, false);
//        inOrderTraverse(root);
        
        // test case 2:
        deleteNode(null, root, 70, false);
        inOrderTraverse(root);
        
        
        /*
		 * Test case 3:
		 *             70
		 *               \
		 *               105
		 *                  \
		 *             100   115
		 *            / \     /  
		 *           99 104  110 
		 *               /   / \
		 *             103 108 112
		 */
//        deleteNode(null, root, 105, false);
//        inOrderTraverse(root);
        
        
        /*
		 * Test case 4:
		 *             70
		 *               \
		 *               105
		 *               /  \
		 *             101   115
		 *            / \     /  
		 *           99 104  110 
		 *           \    /   / \
		 *          100  103 108 112
		 */
		/*
        BSTreeNode leaf1 = new BSTreeNode(100, null, null);
        BSTreeNode leaf2 = new BSTreeNode(99, null, leaf1);
        BSTreeNode leaf3 = new BSTreeNode(103, null, null);
        BSTreeNode leaf4 = new BSTreeNode(108, null, null);
        BSTreeNode leaf5 = new BSTreeNode(112, null, null);
        
        BSTreeNode node1 = new BSTreeNode(67, leaf1, null);
        BSTreeNode node2 = new BSTreeNode(104, leaf3, null);
        BSTreeNode node3 = new BSTreeNode(110, leaf4, leaf5);
        BSTreeNode node4 = new BSTreeNode(101, leaf2, node2);
        BSTreeNode node5 = new BSTreeNode(115, node3, null);
        BSTreeNode node6 = new BSTreeNode(105, node4, node5);
        
        BSTreeNode root = new BSTreeNode(70, null, node6);

        deleteNode(null, root, 70, false);
        inOrderTraverse(root);
        */
	}
}
