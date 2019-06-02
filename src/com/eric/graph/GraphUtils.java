package com.eric.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class GraphUtils {
	/**
	 * 对节点s进行深度优先遍历
	 * @param g
	 * @param s
	 * @return
	 */
	public static GraphSearchResult depthFirstSearch(Graph g, int s){
		//初始化结果集
		GraphSearchResult result = new GraphSearchResult(g.getVertex(), s);
		//递归
		dfs(result, g, s);
		//返回结果
		return result;
	}
	/**
	 * 递归实现深度优先遍历
	 * @param result
	 * @param g
	 * @param w
	 */
	private static void dfs(GraphSearchResult result, Graph g, int v){
		result.setConnected(v, true);
		for(int w: g.getAdj(v)){
			if(result.isConnect(w)){
				continue;
			}
			result.setEdgePath(w, v);//v->w
			dfs(result, g , w);
		}
	}
	/**
	 * 广度优先遍历
	 * @param g
	 * @param s
	 * @return
	 */
	public static GraphSearchResult breathFirstSearch(Graph g, int s){
		//初始化结果集
		GraphSearchResult result = new GraphSearchResult(g.getVertex(), s);
		//借助队列
		Queue<Integer> queue = new LinkedList<>();
		result.setConnected(s, true);
		queue.add(s);
		while(!queue.isEmpty()){
			int v = queue.poll();
			for(int w : g.getAdj(v)){
				if(result.isConnect(w)){
					continue;
				}
				result.setConnected(w, true);
				result.setEdgePath(w, v);//v->w
				queue.add(w);
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		Set<String> set = new HashSet<>();
		set.add("0-5");
		set.add("4-3");
		set.add("0-1");
		set.add("9-12");
		set.add("6-4");
		set.add("5-4");
		set.add("0-2");
		set.add("11-12");
		set.add("9-10");
		set.add("0-6");
		set.add("7-8");
		set.add("9-11");
		set.add("5-3");
		Graph g = new Graph(set);
		
		GraphSearchResult depthFirstSearch1 = depthFirstSearch(g, 0);
		System.out.println(depthFirstSearch1.getConnected());
		System.out.println(depthFirstSearch1.pathTo(6));
		
		GraphSearchResult depthFirstSearch2 = breathFirstSearch(g, 0);
		System.out.println(depthFirstSearch2.getConnected());
		System.out.println(depthFirstSearch2.pathTo(6));
	}
}
