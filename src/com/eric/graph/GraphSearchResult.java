package com.eric.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GraphSearchResult {
	/**
	 * 开始的顶点
	 */
	private int startVertex;
	
	/**
	 * 某个节点与其他几个节点是否连通
	 */
	private boolean[] connected;
	
	/**
	 * 顶点startVertex到某个一个顶点的已知路径上的最后一个顶点
	 * 其实质就是：并查集
	 */
	private int[] edgeTo;
	
	public GraphSearchResult(int vertex, int startVertex){
		this.startVertex = startVertex;
		this.connected = new boolean[vertex];
		this.edgeTo = new int[vertex];
	}
	/**
	 * 顶点startVertex与w点是否相通
	 * @param w
	 * @return
	 */
	public boolean isConnect(int w){
		return this.connected[w];
	}
	
	/**
	 * 与顶点startVertex相连的顶点个数
	 * @return
	 */
	public int count(){
		int count = 0;
		for(boolean isConnect: connected){
			if(isConnect){
				count ++;
			}
		}
		return count;
	}

	/**
	 * 获取与顶点startVertex相连的所有的点
	 * @return
	 */
	public List<Integer> getConnected() {
		List<Integer> connectList = new ArrayList<>();
		for(int i=0; i<connected.length; i++){
			if(connected[i]){
				connectList.add(i);
			}
		}
		return connectList;
	}

	/**
	 * 设置顶点startVertex与w节点是否相连
	 * @param w
	 * @param isConnect
	 */
	public void setConnected(int w, boolean isConnect) {
		this.connected[w] = isConnect;
	}
	
	/**
	 * 设置开始的顶点
	 * @param startVertex
	 */
	public void setStartVertex(int startVertex) {
		this.startVertex = startVertex;
	}
	/**
	 * 设置路径 v -> w
	 * @param w
	 * @param v
	 */
	public void setEdgePath(int w, int v) {
		this.edgeTo[w] = v;
	}
	
	/**
	 * 顶点startVertex到节点v的路径
	 * @param v
	 * @return
	 */
	public String pathTo(int v){
		if(!isConnect(v)){
			return "";
		}
		Stack<Integer> stack = new Stack<>();
		stack.push(v);
		
		int parentIndex = this.edgeTo[v];
		while(startVertex != parentIndex){
			stack.push(parentIndex);
			parentIndex = this.edgeTo[parentIndex];
		}
		stack.push(startVertex);
		
		StringBuilder sb = new StringBuilder();
		while(!stack.isEmpty()){
			sb.append(stack.pop());
			if(stack.size() != 0){
				sb.append("->");
			}
		}
		return sb.toString();
	}
	
}
