package com.eric.tree;

import java.util.ArrayList;
import java.util.List;
/**
 * 最大堆
 * @author Eric
 *
 * @param <E>
 */
public class MaxHeap<E extends Comparable<E>> {

	/**
	 * 数据
	 */
	private List<E> data;
	
	public MaxHeap(int capacity){
		this.data = new ArrayList<>(capacity);
	}
	
	public MaxHeap(){
		this.data = new ArrayList<>();
	}
	
	public MaxHeap(List<E> initData){
		this.data.addAll(initData);
		heapify(this.data);
	}
	
	public int size(){
		return this.data.size();
	}
	
	public boolean isEmpty(){
		return this.data.isEmpty();
	}
	
	/**
	 * 取父节点
	 * @param i
	 * @return
	 */
	private int parentIndex(int i){
		if(i == 0){
			throw new RuntimeException("index=0 dos not have parent node");
		}
		return (i - 1)/2;
	}
	
	/**
	 * 取左孩子
	 * @param i
	 * @return
	 */
	private int leftChild(int i){
		return 2*i + 1;
	}
	
	/**
	 * 取右孩子
	 * @param i
	 * @return
	 */
	private int rightChild(int i){
		return 2*i + 2;
	}
	
	/**
	 * 往堆中添加元素
	 * @param e
	 */
	public void add(E e){
		this.data.add(e);
		siftUp(this.data.size() - 1);
	}
	
	/**
	 * 上浮元素
	 * @param i
	 */
	private void siftUp(int i) {
		while(i > 0 && this.data.get(i).compareTo(this.data.get(parentIndex(i))) > 0){
			//交换
			swapData(i, parentIndex(i));
			
			//将i替换为父亲节点的索引
			i = parentIndex(i);
		}
	}
	
	/**
	 * 交换两个元素
	 * @param i
	 * @param k
	 */
	private boolean swapData(int i, int k){
		if(i<0 || i>=this.data.size() || k<0 || k>=this.data.size()){
			return false;
		}
		E e = this.data.get(i);
		this.data.set(i, this.data.get(k));
		this.data.set(k, e);
		return true;
	}
	
	/**
	 * 查看最大元素
	 * @return
	 */
	public E findMax(){
		if(this.data.size() == 0){
			return null;
		}
		return this.data.get(0);
	}
	/**
	 * 取出最大元素
	 * @return
	 */
	public E extraMax(){
		E e = findMax();
		//将最后一位放在根节点上
		swapData(0, this.data.size() - 1);
		this.data.remove(this.data.size() - 1);
		//向下调整最大堆
		siftDown(0);
		
		return e;
	}
	
	/**
	 * 向下调整最大堆
	 * @param i
	 */
	private void siftDown(int i) {
		//必须存在孩子节点
		while(leftChild(i) < this.data.size()){
			int needSwapIndex = leftChild(i);
			//如果存在右孩子，比较左右孩子谁大
			if(rightChild(i) < this.data.size() && this.data.get(needSwapIndex).compareTo(this.data.get(rightChild(i))) < 0){
				needSwapIndex = rightChild(i);
			}
			//与孩子节点比较,若本身就比较大，则不需要再交换了，直接跳出循环
			if(this.data.get(i).compareTo(this.data.get(needSwapIndex)) >= 0){
				break;
			}
			
			//否则，交换，继续循环
			swapData(i, needSwapIndex);
			i = needSwapIndex;
		}
		
	}
	/**
	 * 将数据调整为最大堆
	 * @param data
	 */
	public void heapify(List<E> data){
		for (int i = parentIndex(data.size() - 1); i >= 0; i--) {
			siftDown(i);
		}
	}
	/**
	 * 最大堆头节点替换
	 * @param e
	 * @return
	 */
	public E replace(E e){
		E max = findMax();
		this.data.set(0, e);
		siftDown(0);
		return max;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
