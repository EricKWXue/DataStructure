package com.eric.tree;
/**
 * ���鼯
 * һ���ɺ��ӽڵ�ָ�򸸽ڵ����
 * @author Eric
 *
 */
public class UnionFindTree {
	private int[] parent;
	
	private int[] hight;//��iΪ���ڵ�����ĸ߶�
	
	public UnionFindTree(int size){
		parent = new int[size];
		for (int i = 0; i < parent.length; i++) {
			parent[i] = i;
			hight[i] = 1;
		}
	}
	/**
	 * �ҵ�i�ڵ�ĸ��ڵ�
	 * @param i
	 * @return
	 */
	private int findRoot(int i){
		if(i < 0 || i > parent.length){
			throw new ArrayIndexOutOfBoundsException();
		}
		while(parent[i] != i){
			//·��ѹ�����˺�hight[]�ĺ����˻�Ϊ�����������Ǹ߶���
			parent[i] = parent[parent[i]];
			i = parent[i];
		}
		return i;
		
		/*if(i != parent[i]){
			parent[i] = findRoot(parent[i]);
		}
		return parent[i];*/
		
	}
	/**
	 * p/q�Ƿ�������
	 * @param p
	 * @param q
	 * @return
	 */
	public boolean isConnected(int p, int q){
		return findRoot(p) == findRoot(q);
	}
	/**
	 * �ϲ�p/q�����ڵ�
	 * @param p
	 * @param q
	 */
	public void unionElement(int p, int q){
		int pId = findRoot(p);
		int qId = findRoot(q);
		if(pId == qId){
			return;
		}
		//�߶�С���� ָ�� �߶ȴ�������������ĸ߶��ۼ�
		if(hight[pId] < hight[qId]){
			parent[pId] = qId;
		}else if(hight[pId] > hight[qId]){
			parent[qId] = pId;
		}else{
			parent[qId] = pId;
			hight[pId] += 1;
		}
		
	}
}
