package com.eric.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GraphSearchResult {
	/**
	 * ��ʼ�Ķ���
	 */
	private int startVertex;
	
	/**
	 * ĳ���ڵ������������ڵ��Ƿ���ͨ
	 */
	private boolean[] connected;
	
	/**
	 * ����startVertex��ĳ��һ���������֪·���ϵ����һ������
	 * ��ʵ�ʾ��ǣ����鼯
	 */
	private int[] edgeTo;
	
	public GraphSearchResult(int vertex, int startVertex){
		this.startVertex = startVertex;
		this.connected = new boolean[vertex];
		this.edgeTo = new int[vertex];
	}
	/**
	 * ����startVertex��w���Ƿ���ͨ
	 * @param w
	 * @return
	 */
	public boolean isConnect(int w){
		return this.connected[w];
	}
	
	/**
	 * �붥��startVertex�����Ķ������
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
	 * ��ȡ�붥��startVertex���������еĵ�
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
	 * ���ö���startVertex��w�ڵ��Ƿ�����
	 * @param w
	 * @param isConnect
	 */
	public void setConnected(int w, boolean isConnect) {
		this.connected[w] = isConnect;
	}
	
	/**
	 * ���ÿ�ʼ�Ķ���
	 * @param startVertex
	 */
	public void setStartVertex(int startVertex) {
		this.startVertex = startVertex;
	}
	/**
	 * ����·�� v -> w
	 * @param w
	 * @param v
	 */
	public void setEdgePath(int w, int v) {
		this.edgeTo[w] = v;
	}
	
	/**
	 * ����startVertex���ڵ�v��·��
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
