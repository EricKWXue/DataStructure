package com.eric.tree;

public class RedBlackTree<E extends Comparable<E>> {
	
	public static final boolean RED = true;
	public static final boolean BLACK = false;
	/**
	 * 节点
	 */
	private class Node {
		public E value;
		public Node left;
		public Node right;
		public boolean color;
		
		public Node(E e){
			this.value = e;
			this.left = null;
			this.right = null;
			this.color = RED;
		}
	}
	
	private Node root;
	private int size;
	
	public RedBlackTree(){
		this.root = null;
		this.size = 0;
	}
	/**
	 * 获取树节点的大小
	 * @return
	 */
	public int getSize(){
		return this.size;
	}
	/**
	 * 判断二叉树是否为空
	 * @return
	 */
	public boolean isEmpty(){
		return this.size == 0;
	}
	
	/**
	 * 增加节点
	 * @param e
	 */
	public void add(E e){
		root = add(root, e);
		root.color = BLACK;
	}
	/**
	 * 递归添加
	 * @param node
	 * @param e
	 * @return
	 */
	private Node add(Node node, E e){
		//递归到底的情况
		if(node == null){
			size ++;
			return new Node(e);
		}
		if(e.compareTo(node.value) > 0){
			//在右子树
			node.right = add(node.right, e);
		}else if(e.compareTo(node.value) < 0){
			//在左子树
			node.left = add(node.left, e);
		}else{
			node.value = e;
		}
		/**
		 *    a(黑)  add(c)    a(黑)                       a(黑)
		 *   /     -------->  /      leftRotate(b)       /       rightRotate(a)            flipColor(c)
		 *  b(红)             b(红) ------------------>  c(红)  ------------------>  c(黑)------------------->  c(红) 
		 *                    \                         /                         /   \                      /   \
		 *                     c(红)                   b(红)                      b(红) a(红)                 b(黑) a(黑)
		 */
		if(isRed(node.right) && !isRed(node.left)){
			leftRotate(node);
		}
		
		if(isRed(node.left) && isRed(node.left.left)){
			rightRotate(node);
		}
		
		if(isRed(node.right) && isRed(node.left)){
			flipColor(node);
		}
		
		return node;
	}
	
	/**
	 * 空节点默认是黑色
	 * @param node
	 * @return
	 */
	private boolean isRed(Node node){
		if(node == null){
			return BLACK;
		}
		return node.color;
	}
	
	/**
	 * 左旋转
	 *      node                            x
	 *      / \                           /   \
	 *     T1   x        向左旋转                                     node  T3
	 *         / \     ------------->   /   \
	 *        T2  T3                   T1   T2
	 */
	private Node leftRotate(Node node){
		Node x = node.right;
		Node T2 = x.left;
		
		//向左旋转
		x.left = node;
		node.right = T2;
	
		//颜色
		x.color = node.color;
		node.color = RED;
		
		return x;
	}
	
	/**
	 * 右旋转
	 *      node                      
	 *      / \
	 *     x   T2                 向右旋转                                       x
	 *    / \     ------------->    /   \
	 *   y   T1                    z   node
	 *                                 /  \
	 *                                T1  T2  
	 */
	private Node rightRotate(Node node){
		Node x = node.left;
		Node T1 = x.right;
		
		//向右旋转
		x.right = node;
		node.left = T1;
		
		//维护下颜色
		x.color = node.color;
		node.color = RED;
		
		return x;
	}
	
	/**
	 * 颜色翻转
	 * @param node
	 */
	private void flipColor(Node node){
		node.color = RED;
		node.left.color = BLACK;
		node.right.color = BLACK;
	}
	
	/**
	 * 是否包含e
	 * @param e
	 * @return
	 */
	public boolean contains(E value){
		return contains(root, value);
	}
	/**
	 * 是否包含e的递归写法
	 * @param node
	 * @param e
	 * @return
	 */
	private boolean contains(Node node, E value) {
		if(node == null){
			return false;
		}
		
		if(value.compareTo(node.value) == 0){
			return true;
		}else if(value.compareTo(node.value) > 0){
			return contains(node.right, value);
		}else{
			return contains(node.left, value);
		}
	}
	
}
