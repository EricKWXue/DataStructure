package com.eric.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 平衡二叉树
 * @author Eric
 *
 */
public class AVLTree<E extends Comparable<E>> {
	/**
	 * 节点
	 */
	private class Node {
		public E e;
		public int height;
		public Node left;
		public Node right;
		
		public Node(E e){
			this.e = e;
			this.height = 1;
			this.left = null;
			this.right = null;
		}
	}
	
	private Node root;
	private int size;
	
	public AVLTree(){
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
	 * 获取节点的高度
	 * @param node
	 * @return
	 */
	public int getHeight(Node node){
		if(node == null){
			return 0;
		}
		return node.height;
	}
	
	/**
	 * 获取平衡因子
	 * @param node
	 * @return
	 */
	public int getBalenceFactor(Node node){
		if(node == null){
			return 0;
		}
		int leftTreeHeight = getHeight(node.left);
		int rightTreeHeight = getHeight(node.right);
		return leftTreeHeight - rightTreeHeight;
	}
	
	/**
	 * 增加节点
	 * @param e
	 */
	public void add(E e){
		root = add(root, e);
	}
	
	private Node add(Node node, E e){
		//递归到底的情况
		if(node == null){
			size ++;
			return new Node(e);
		}
		if(e.compareTo(node.e) > 0){
			//在右子树
			node.right = add(node.right, e);
		}else if(e.compareTo(node.e) < 0){
			//在左子树
			node.left = add(node.left, e);
		}else{
			node.e = e;
		}
		//此处的高度处理好像有点问题
		node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
		
		//自平衡处理
		if(getBalenceFactor(node) > 1 && getBalenceFactor(node.left) >= 0){
			//LL,整体向左倾斜。则右旋转
			return rightRotate(node);
		}
		if(getBalenceFactor(node) < -1 && getBalenceFactor(node.right) <= 0){
			//RR,整体向右倾斜。则左旋转
			return leftRotate(node);
		}
		if(getBalenceFactor(node) > 1 && getBalenceFactor(node.left) < 0){
			//LR,先转为LL，再右旋转
			node.left = leftRotate(node.left);
			return rightRotate(node);
		}
		if(getBalenceFactor(node) < -1 && getBalenceFactor(node.right) > 0){
			//RL,先转为RR。则左旋转
			node.right = rightRotate(node.right);
			return leftRotate(node);
		}
		
		return node;
	}
	/**
	 * LL
	 * 对节点y进行右旋转，旋转后x为新的根节点,返回
	 *       y
	 *      / \
	 *     x   T4                 向右旋转                                       x
	 *    / \     ------------->    /   \
	 *   z   T3                    z     y
	 *  / \                       / \   / \
	 * T1  T2                    T1 T2 T3 T4
	 */
	private Node rightRotate(Node y){
		Node x = y.left;
		Node T3 = x.right;
		
		//向右旋转
		x.right = y;
		y.left = T3;
		
		//更新x和y的高度
		x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
		y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
	
		return x;
	}
	/**
	 * RR
	 * 对节点y进行左旋转，旋转后x为新的根节点,返回
	 *       y
	 *      / \
	 *     T1   x        向左旋转                                            x
	 *         / \     ------------->    /   \
	 *        T2  z                     y     z
	 *  		 / \                   / \   / \
	 *          T3  T4                T1 T2 T3 T4
	 */
	private Node leftRotate(Node y){
		Node x = y.right;
		Node T2 = x.left;
		
		//向左旋转
		x.left = y;
		y.right = T2;
		
		//更新x和y的高度
		x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
		y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
	
		return x;
	}
	
	/**
	 * 取最小值
	 * @param node
	 * @return
	 */
	private Node minimum(Node node){
		if(node.left == null){
			return node;
		}
		return minimum(node.left);
	}
	
	/**
	 * 删除树中最小的节点（缺少维护平衡的过程）
	 * @param node
	 * @return
	 */
	@Deprecated
	public Node removeMin(Node node){
		//递归到底的情况
		if(node.left == null){
			Node rightNode = node.right;
			node.right = null;
			size --;
			return rightNode;
		}
		node.left = removeMin(node.left);
		return node;
	}
	
	/**
	 * 删除元素e
	 * @param e
	 */
	public void remove(E e){
		remove(root, e);
	}
	/**
	 * 递归调用删除方法
	 * @param node
	 * @param e
	 * @return
	 */
	private Node remove(Node node, E e){
		if(node == null){
			return null;
		}
		
		Node returnNode;
		if(e.compareTo(node.e) > 0){
			//删右子树
			node.right = remove(node.right, e);
			returnNode = node;
		} else if(e.compareTo(node.e) < 0){
			//删左子树
			node.left = remove(node.left, e);
			returnNode = node;
		} else {
			//删自己
			if(node.left == null){
				//直接返回自己的右子树即可
				Node rightNode = node.right;
				node.right = null;
				size --;
				returnNode = rightNode;
			}else if(node.right == null){
				//直接返回自己的左子树即可
				Node leftNode = node.left;
				node.left = null;
				size --;
				returnNode = leftNode;
			} else {
				//左右子树都不为空的情况下
				//找到比自己大的最小的节点，即右子树的最小节点，作为新的根节点返回
				Node newNode = minimum(node.right);
				newNode.right = remove(node.right, newNode.e);
				newNode.left = node.left;
				node.left = node.right = null;
				returnNode = newNode;
			}
		}
		
		//若删除节点后，返回的树不是空，则需要维护下平衡
		if(returnNode != null){
			//此处的高度处理好像有点问题
			returnNode.height = Math.max(getHeight(returnNode.left), getHeight(returnNode.right)) + 1;
			
			//自平衡处理
			if(getBalenceFactor(returnNode) > 1 && getBalenceFactor(returnNode.left) >= 0){
				//LL,整体向左倾斜。则右旋转
				return rightRotate(returnNode);
			}
			if(getBalenceFactor(returnNode) < -1 && getBalenceFactor(returnNode.right) <= 0){
				//RR,整体向右倾斜。则左旋转
				return leftRotate(returnNode);
			}
			if(getBalenceFactor(returnNode) > 1 && getBalenceFactor(returnNode.left) < 0){
				//LR,先转为LL，再右旋转
				returnNode.left = leftRotate(returnNode.left);
				return rightRotate(returnNode);
			}
			if(getBalenceFactor(returnNode) < -1 && getBalenceFactor(returnNode.right) > 0){
				//RL,先转为RR。则左旋转
				returnNode.right = rightRotate(returnNode.right);
				return leftRotate(returnNode);
			}
		}
		return returnNode;
	}
	
	/**
	 * 判断是否是个二分搜索树
	 * @return
	 */
	public boolean isBSTree(){
		List<E> list = new ArrayList<>();
		inOrder(root, list);
		for (int i=1; i<list.size(); i++) {
			if(list.get(i-1).compareTo(list.get(i)) > 0){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 中序遍历二分搜索树，每个节点的值存入list中
	 * @param node
	 * @param list
	 */
	private void inOrder(Node node, List<E> list){
		if(node == null){
			return;
		}
		inOrder(node.left, list);
		list.add(node.e);
		inOrder(node.right, list);
	}
	/**
	 * 判断是否是平衡二叉树
	 * @return
	 */
	public boolean isBalance(){
		return isBalance(root);
	}
	/**
	 * 以某个节点为根节点的树是否满足平衡二叉树
	 * @param node
	 * @return
	 */
	private boolean isBalance(Node node){
		if(node == null){
			return true;
		}
		if(Math.abs(getBalenceFactor(node)) > 1){
			return false;
		}
		return isBalance(node.left) && isBalance(node.right);
	}
	
	/**
	 * 是否包含e
	 * @param e
	 * @return
	 */
	public boolean contains(E e){
		return contains(root, e);
	}
	/**
	 * 是否包含e的递归写法
	 * @param node
	 * @param e
	 * @return
	 */
	private boolean contains(Node node, E e) {
		if(node == null){
			return false;
		}
		
		if(e.compareTo(node.e) == 0){
			return true;
		}else if(e.compareTo(node.e) > 0){
			return contains(node.right, e);
		}else{
			return contains(node.left, e);
		}
	}
}
