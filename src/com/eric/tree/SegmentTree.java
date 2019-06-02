package com.eric.tree;

import com.eric.tree.inter.ISegmentTree;
/**
 * ���������߶�����
 * @author Eric
 *
 * @param <E>
 */
public class SegmentTree<E> {
	/**
	 * ԭʼ����
	 */
	private E[] data;
	/**
	 * �߶���
	 */
	private E[] tree;
	
	/**
	 * �����ӿ�
	 */
	private ISegmentTree<E> iSegmentTree;
	
	private int parentIndex(int index){
		return (index - 1)/2;
	}
	
	private int leftChildIndex(int index){
		return 2*index + 1;
	}
	
	private int rightChildIndex(int index){
		return 2*index + 2;
	}
	
	
	@SuppressWarnings("unchecked")
	public SegmentTree(E[] arr, ISegmentTree<E> iSegmentTree){
		this.iSegmentTree = iSegmentTree;
		data = (E[])new Object[arr.length];
		for (int i = 0; i < arr.length; i++) {
			data[i] = arr[i];
		}
		tree = (E[])new Object[4 * arr.length];
		initSegmentTree(0, 0, data.length - 1);
	}
	
	/**
	 * ��ѯ����
	 * @param left
	 * @param right
	 * @return
	 */
	public E query(int left, int right){
		if(left < 0 || left > this.tree.length - 1 || right < 0 || right > this.tree.length - 1 || left > right){
			return null;
		}
		return query(0, 0, this.tree.length - 1, left, right);
	}

	/**
	 * �ݹ����
	 * @param index
	 * @param from
	 * @param to
	 * @param left
	 * @param right
	 * @return
	 */
	private E query(int index, int from, int to, int left, int right) {
		if(from == left && to == right){
			return tree[index];
		}
		int mid = from + (to - from)/2;
		if(left > mid){
			//�������Һ���
			return query(rightChildIndex(index), mid+1, to, left, right);
		}else if(right <= mid){
			//����������
			return query(leftChildIndex(index), from, mid, left, right);
		}else{
			//�������м�
			int middle = left + (right - left)/2;
			E leftE = query(leftChildIndex(index), from, mid, left, middle);
			E rightE = query(rightChildIndex(index), mid+1, to, middle+1, right);
			return iSegmentTree.merge(leftE, rightE);
		}
	}

	/**
	 * ��ʼ��������
	 * @param index
	 * @param from
	 * @param to
	 */
	private void initSegmentTree(int index, int from, int to) {
		if(from == to){
			tree[index] = data[from];
			return;
		}
		int mid = from + (to - from)/2;
		int leftChildIndex = leftChildIndex(index);
		initSegmentTree(leftChildIndex, from, mid);
		
		int rightChildIndex = rightChildIndex(index);
		initSegmentTree(rightChildIndex, mid + 1, to);
		
		tree[index] = iSegmentTree.merge(tree[leftChildIndex], tree[rightChildIndex]);
		
	}
	/**
	 * ����ֵ
	 * @param index
	 * @param e
	 */
	public void set(int index, E e){
		if(index < 0 || index > this.tree.length - 1){
			throw new ArrayIndexOutOfBoundsException();
		}
		set(0, 0, this.data.length, index, e);
	}
	
	/**
	 * �����߶���
	 * @param treeIndex
	 * @param from
	 * @param to
	 * @param index
	 * @param e
	 */
	private void set(int treeIndex, int from, int to, int index, E e){
		if(from == to){
			this.tree[treeIndex] = e;
			return;
		}
		
		int mid = from + (to - from)/2;
		int leftChildIndex = leftChildIndex(treeIndex);
		int rightChildIndex = rightChildIndex(treeIndex);
		if(index >= mid + 1){
			//���Ҳ࣬�����Һ���
			set(rightChildIndex, mid + 1, to, index, e);
		}else{
			//����࣬��������
			set(leftChildIndex, from, mid, index, e);
		}
		
		//���±��ڵ�
		this.tree[treeIndex] = iSegmentTree.merge(tree[leftChildIndex], tree[rightChildIndex]);
	}
}
