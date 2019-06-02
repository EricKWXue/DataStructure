package com.eric.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Graph {
	//�������
	private int vertex;
	//�ߵĸ���
	private int edge;
	//�ڽӱ�
	private LinkedList<Integer>[] adj;
	
	/**
	 * ��ʼ��ͼ
	 * @param vertex
	 */
	public Graph(int vertex){
		this.vertex = vertex;
		this.edge = 0;
		this.adj = new LinkedList[vertex];
		
		for (int i = 0; i < vertex; i++) {
			this.adj[0] = new LinkedList<>();
		}
	}
	/**
	 * �ߵĽṹ v-w�����£�
	 * 0-5
	 * 4-3
	 * 0-1
	 * 9-12
	 * 6-4
	 * 5-4
	 * 0-2
	 * 11-12
	 * 9-10
	 * 0-6
	 * 7-8
	 * 9-11
	 * 5-3
	 * ע�⣺
	 * 1.������ͬʱ����5-3��3-5����ıߣ�����ͼ�����
	 * 2.�ڵ�ı�ű���������������ͼ�����
	 * @param graphlist
	 */
	public Graph(Set<String> graphSet){
		Set<Integer> vertexSet = new HashSet<>();
		for (String str : graphSet) {
			String[] strArr = str.split("-");
			vertexSet.add(Integer.parseInt(strArr[0]));
			vertexSet.add(Integer.parseInt(strArr[1]));
		}
		
		this.vertex = vertexSet.size();
		this.adj = new LinkedList[vertex];
		for (int i = 0; i < vertex; i++) {
			this.adj[i] = new LinkedList<>();
		}
		for (String str : graphSet) {
			String[] strArr = str.split("-");
			addEdge(Integer.parseInt(strArr[0]), Integer.parseInt(strArr[1]));
		}
	}
	/**
	 * ���һ����
	 * @param v
	 * @param w
	 */
	public void addEdge(Integer v, Integer w){
		adj[v].add(w);
		adj[w].add(v);
		this.edge ++;
	}
	/**
	 * ��ȡĳ������ڽӱ�
	 * @param vertex
	 * @return
	 */
	public LinkedList<Integer> getAdj(int vertex){
		return this.adj[vertex];
	}
	public int getVertex() {
		return vertex;
	}
	public int getEdge() {
		return edge;
	}
	
	
}
