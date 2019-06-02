package com.eric.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * ƽ�������
 * @author Eric
 *
 */
public class AVLTree<E extends Comparable<E>> {
	/**
	 * �ڵ�
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
	 * ��ȡ�ڵ�ĸ߶�
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
	 * ��ȡƽ������
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
	 * ���ӽڵ�
	 * @param e
	 */
	public void add(E e){
		root = add(root, e);
	}
	
	private Node add(Node node, E e){
		//�ݹ鵽�׵����
		if(node == null){
			size ++;
			return new Node(e);
		}
		if(e.compareTo(node.e) > 0){
			//��������
			node.right = add(node.right, e);
		}else if(e.compareTo(node.e) < 0){
			//��������
			node.left = add(node.left, e);
		}else{
			node.e = e;
		}
		//�˴��ĸ߶ȴ�������е�����
		node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
		
		//��ƽ�⴦��
		if(getBalenceFactor(node) > 1 && getBalenceFactor(node.left) >= 0){
			//LL,����������б��������ת
			return rightRotate(node);
		}
		if(getBalenceFactor(node) < -1 && getBalenceFactor(node.right) <= 0){
			//RR,����������б��������ת
			return leftRotate(node);
		}
		if(getBalenceFactor(node) > 1 && getBalenceFactor(node.left) < 0){
			//LR,��תΪLL��������ת
			node.left = leftRotate(node.left);
			return rightRotate(node);
		}
		if(getBalenceFactor(node) < -1 && getBalenceFactor(node.right) > 0){
			//RL,��תΪRR��������ת
			node.right = rightRotate(node.right);
			return leftRotate(node);
		}
		
		return node;
	}
	/**
	 * LL
	 * �Խڵ�y��������ת����ת��xΪ�µĸ��ڵ�,����
	 *       y
	 *      / \
	 *     x   T4                 ������ת                                       x
	 *    / \     ------------->    /   \
	 *   z   T3                    z     y
	 *  / \                       / \   / \
	 * T1  T2                    T1 T2 T3 T4
	 */
	private Node rightRotate(Node y){
		Node x = y.left;
		Node T3 = x.right;
		
		//������ת
		x.right = y;
		y.left = T3;
		
		//����x��y�ĸ߶�
		x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
		y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
	
		return x;
	}
	/**
	 * RR
	 * �Խڵ�y��������ת����ת��xΪ�µĸ��ڵ�,����
	 *       y
	 *      / \
	 *     T1   x        ������ת                                            x
	 *         / \     ------------->    /   \
	 *        T2  z                     y     z
	 *  		 / \                   / \   / \
	 *          T3  T4                T1 T2 T3 T4
	 */
	private Node leftRotate(Node y){
		Node x = y.right;
		Node T2 = x.left;
		
		//������ת
		x.left = y;
		y.right = T2;
		
		//����x��y�ĸ߶�
		x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
		y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
	
		return x;
	}
	
	/**
	 * ȡ��Сֵ
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
	 * ɾ��������С�Ľڵ㣨ȱ��ά��ƽ��Ĺ��̣�
	 * @param node
	 * @return
	 */
	@Deprecated
	public Node removeMin(Node node){
		//�ݹ鵽�׵����
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
	 * ɾ��Ԫ��e
	 * @param e
	 */
	public void remove(E e){
		remove(root, e);
	}
	/**
	 * �ݹ����ɾ������
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
			//ɾ������
			node.right = remove(node.right, e);
			returnNode = node;
		} else if(e.compareTo(node.e) < 0){
			//ɾ������
			node.left = remove(node.left, e);
			returnNode = node;
		} else {
			//ɾ�Լ�
			if(node.left == null){
				//ֱ�ӷ����Լ�������������
				Node rightNode = node.right;
				node.right = null;
				size --;
				returnNode = rightNode;
			}else if(node.right == null){
				//ֱ�ӷ����Լ�������������
				Node leftNode = node.left;
				node.left = null;
				size --;
				returnNode = leftNode;
			} else {
				//������������Ϊ�յ������
				//�ҵ����Լ������С�Ľڵ㣬������������С�ڵ㣬��Ϊ�µĸ��ڵ㷵��
				Node newNode = minimum(node.right);
				newNode.right = remove(node.right, newNode.e);
				newNode.left = node.left;
				node.left = node.right = null;
				returnNode = newNode;
			}
		}
		
		//��ɾ���ڵ�󣬷��ص������ǿգ�����Ҫά����ƽ��
		if(returnNode != null){
			//�˴��ĸ߶ȴ�������е�����
			returnNode.height = Math.max(getHeight(returnNode.left), getHeight(returnNode.right)) + 1;
			
			//��ƽ�⴦��
			if(getBalenceFactor(returnNode) > 1 && getBalenceFactor(returnNode.left) >= 0){
				//LL,����������б��������ת
				return rightRotate(returnNode);
			}
			if(getBalenceFactor(returnNode) < -1 && getBalenceFactor(returnNode.right) <= 0){
				//RR,����������б��������ת
				return leftRotate(returnNode);
			}
			if(getBalenceFactor(returnNode) > 1 && getBalenceFactor(returnNode.left) < 0){
				//LR,��תΪLL��������ת
				returnNode.left = leftRotate(returnNode.left);
				return rightRotate(returnNode);
			}
			if(getBalenceFactor(returnNode) < -1 && getBalenceFactor(returnNode.right) > 0){
				//RL,��תΪRR��������ת
				returnNode.right = rightRotate(returnNode.right);
				return leftRotate(returnNode);
			}
		}
		return returnNode;
	}
	
	/**
	 * �ж��Ƿ��Ǹ�����������
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
	 * �������������������ÿ���ڵ��ֵ����list��
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
	 * �ж��Ƿ���ƽ�������
	 * @return
	 */
	public boolean isBalance(){
		return isBalance(root);
	}
	/**
	 * ��ĳ���ڵ�Ϊ���ڵ�����Ƿ�����ƽ�������
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
	 * �Ƿ����e
	 * @param e
	 * @return
	 */
	public boolean contains(E e){
		return contains(root, e);
	}
	/**
	 * �Ƿ����e�ĵݹ�д��
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
