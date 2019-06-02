package com.eric.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Graph {
	//定点个数
	private int vertex;
	//边的个数
	private int edge;
	//邻接表
	private LinkedList<Integer>[] adj;
	
	/**
	 * 初始化图
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
	 * 边的结构 v-w，如下：
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
	 * 注意：
	 * 1.不允许同时出现5-3和3-5这类的边，否则构图会错误
	 * 2.节点的编号必须能连续，否则构图会错误
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
	 * 添加一条边
	 * @param v
	 * @param w
	 */
	public void addEdge(Integer v, Integer w){
		adj[v].add(w);
		adj[w].add(v);
		this.edge ++;
	}
	/**
	 * 获取某个点的邻接表
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
