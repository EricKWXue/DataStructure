package com.eric.tree;

public class RedBlackTree<E extends Comparable<E>> {
	
	public static final boolean RED = true;
	public static final boolean BLACK = false;
	/**
	 * �ڵ�
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
	 * ��ȡ���ڵ�Ĵ�С
	 * @return
	 */
	public int getSize(){
		return this.size;
	}
	/**
	 * �ж϶������Ƿ�Ϊ��
	 * @return
	 */
	public boolean isEmpty(){
		return this.size == 0;
	}
	
	/**
	 * ���ӽڵ�
	 * @param e
	 */
	public void add(E e){
		root = add(root, e);
		root.color = BLACK;
	}
	/**
	 * �ݹ����
	 * @param node
	 * @param e
	 * @return
	 */
	private Node add(Node node, E e){
		//�ݹ鵽�׵����
		if(node == null){
			size ++;
			return new Node(e);
		}
		if(e.compareTo(node.value) > 0){
			//��������
			node.right = add(node.right, e);
		}else if(e.compareTo(node.value) < 0){
			//��������
			node.left = add(node.left, e);
		}else{
			node.value = e;
		}
		/**
		 *    a(��)  add(c)    a(��)                       a(��)
		 *   /     -------->  /      leftRotate(b)       /       rightRotate(a)            flipColor(c)
		 *  b(��)             b(��) ------------------>  c(��)  ------------------>  c(��)------------------->  c(��) 
		 *                    \                         /                         /   \                      /   \
		 *                     c(��)                   b(��)                      b(��) a(��)                 b(��) a(��)
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
	 * �սڵ�Ĭ���Ǻ�ɫ
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
	 * ����ת
	 *      node                            x
	 *      / \                           /   \
	 *     T1   x        ������ת                                     node  T3
	 *         / \     ------------->   /   \
	 *        T2  T3                   T1   T2
	 */
	private Node leftRotate(Node node){
		Node x = node.right;
		Node T2 = x.left;
		
		//������ת
		x.left = node;
		node.right = T2;
	
		//��ɫ
		x.color = node.color;
		node.color = RED;
		
		return x;
	}
	
	/**
	 * ����ת
	 *      node                      
	 *      / \
	 *     x   T2                 ������ת                                       x
	 *    / \     ------------->    /   \
	 *   y   T1                    z   node
	 *                                 /  \
	 *                                T1  T2  
	 */
	private Node rightRotate(Node node){
		Node x = node.left;
		Node T1 = x.right;
		
		//������ת
		x.right = node;
		node.left = T1;
		
		//ά������ɫ
		x.color = node.color;
		node.color = RED;
		
		return x;
	}
	
	/**
	 * ��ɫ��ת
	 * @param node
	 */
	private void flipColor(Node node){
		node.color = RED;
		node.left.color = BLACK;
		node.right.color = BLACK;
	}
	
	/**
	 * �Ƿ����e
	 * @param e
	 * @return
	 */
	public boolean contains(E value){
		return contains(root, value);
	}
	/**
	 * �Ƿ����e�ĵݹ�д��
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
