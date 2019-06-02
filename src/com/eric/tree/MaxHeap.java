package com.eric.tree;

import java.util.ArrayList;
import java.util.List;
/**
 * ����
 * @author Eric
 *
 * @param <E>
 */
public class MaxHeap<E extends Comparable<E>> {

	/**
	 * ����
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
	 * ȡ���ڵ�
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
	 * ȡ����
	 * @param i
	 * @return
	 */
	private int leftChild(int i){
		return 2*i + 1;
	}
	
	/**
	 * ȡ�Һ���
	 * @param i
	 * @return
	 */
	private int rightChild(int i){
		return 2*i + 2;
	}
	
	/**
	 * ���������Ԫ��
	 * @param e
	 */
	public void add(E e){
		this.data.add(e);
		siftUp(this.data.size() - 1);
	}
	
	/**
	 * �ϸ�Ԫ��
	 * @param i
	 */
	private void siftUp(int i) {
		while(i > 0 && this.data.get(i).compareTo(this.data.get(parentIndex(i))) > 0){
			//����
			swapData(i, parentIndex(i));
			
			//��i�滻Ϊ���׽ڵ������
			i = parentIndex(i);
		}
	}
	
	/**
	 * ��������Ԫ��
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
	 * �鿴���Ԫ��
	 * @return
	 */
	public E findMax(){
		if(this.data.size() == 0){
			return null;
		}
		return this.data.get(0);
	}
	/**
	 * ȡ�����Ԫ��
	 * @return
	 */
	public E extraMax(){
		E e = findMax();
		//�����һλ���ڸ��ڵ���
		swapData(0, this.data.size() - 1);
		this.data.remove(this.data.size() - 1);
		//���µ�������
		siftDown(0);
		
		return e;
	}
	
	/**
	 * ���µ�������
	 * @param i
	 */
	private void siftDown(int i) {
		//������ں��ӽڵ�
		while(leftChild(i) < this.data.size()){
			int needSwapIndex = leftChild(i);
			//��������Һ��ӣ��Ƚ����Һ���˭��
			if(rightChild(i) < this.data.size() && this.data.get(needSwapIndex).compareTo(this.data.get(rightChild(i))) < 0){
				needSwapIndex = rightChild(i);
			}
			//�뺢�ӽڵ�Ƚ�,������ͱȽϴ�����Ҫ�ٽ����ˣ�ֱ������ѭ��
			if(this.data.get(i).compareTo(this.data.get(needSwapIndex)) >= 0){
				break;
			}
			
			//���򣬽���������ѭ��
			swapData(i, needSwapIndex);
			i = needSwapIndex;
		}
		
	}
	/**
	 * �����ݵ���Ϊ����
	 * @param data
	 */
	public void heapify(List<E> data){
		for (int i = parentIndex(data.size() - 1); i >= 0; i--) {
			siftDown(i);
		}
	}
	/**
	 * ����ͷ�ڵ��滻
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
