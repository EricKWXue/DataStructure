package com.eric.tree;

import com.eric.tree.inter.ISegmentTree;
/**
 * 区间数（线段树）
 * @author Eric
 *
 * @param <E>
 */
public class SegmentTree<E> {
	/**
	 * 原始数据
	 */
	private E[] data;
	/**
	 * 线段树
	 */
	private E[] tree;
	
	/**
	 * 操作接口
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
	 * 查询区间
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
	 * 递归调用
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
			//区间在右孩子
			return query(rightChildIndex(index), mid+1, to, left, right);
		}else if(right <= mid){
			//区间在左孩子
			return query(leftChildIndex(index), from, mid, left, right);
		}else{
			//区间在中间
			int middle = left + (right - left)/2;
			E leftE = query(leftChildIndex(index), from, mid, left, middle);
			E rightE = query(rightChildIndex(index), mid+1, to, middle+1, right);
			return iSegmentTree.merge(leftE, rightE);
		}
	}

	/**
	 * 初始化区间树
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
	 * 设置值
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
	 * 更新线段树
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
			//在右侧，更新右孩子
			set(rightChildIndex, mid + 1, to, index, e);
		}else{
			//在左侧，更新左孩子
			set(leftChildIndex, from, mid, index, e);
		}
		
		//更新本节点
		this.tree[treeIndex] = iSegmentTree.merge(tree[leftChildIndex], tree[rightChildIndex]);
	}
}
