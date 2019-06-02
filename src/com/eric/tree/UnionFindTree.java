package com.eric.tree;
/**
 * 并查集
 * 一个由孩子节点指向父节点的树
 * @author Eric
 *
 */
public class UnionFindTree {
	private int[] parent;
	
	private int[] hight;//以i为根节点的树的高度
	
	public UnionFindTree(int size){
		parent = new int[size];
		for (int i = 0; i < parent.length; i++) {
			parent[i] = i;
			hight[i] = 1;
		}
	}
	/**
	 * 找到i节点的根节点
	 * @param i
	 * @return
	 */
	private int findRoot(int i){
		if(i < 0 || i > parent.length){
			throw new ArrayIndexOutOfBoundsException();
		}
		while(parent[i] != i){
			//路径压缩，此后hight[]的含义退化为排名，不再是高度了
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
	 * p/q是否连接着
	 * @param p
	 * @param q
	 * @return
	 */
	public boolean isConnected(int p, int q){
		return findRoot(p) == findRoot(q);
	}
	/**
	 * 合并p/q两个节点
	 * @param p
	 * @param q
	 */
	public void unionElement(int p, int q){
		int pId = findRoot(p);
		int qId = findRoot(q);
		if(pId == qId){
			return;
		}
		//高度小的树 指向 高度大的树，减少树的高度累计
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
