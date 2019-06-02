package com.eric.tree;

import java.util.HashMap;
import java.util.Map;
/**
 * 字典树
 * @author Eric
 *
 */
public class TrieMap<V> {
	/**
	 * 节点内部类
	 * @author Eric
	 *
	 */
	private class Node{
		public V v;
		public Map<Character, Node> next;
		
		public Node(V v){
			this.v = v;
			this.next = new HashMap<>();
		}
		
		public Node(){
			this(null);
		}
	}
	
	/**
	 * 根节点
	 */
	private Node root;
	/**
	 * 节点多少
	 */
	private int size;
	
	public TrieMap(){
		this.root = new Node();
		size = 0;
	}
	
	public int getSize(){
		return size;
	}
	
	/**
	 * 添加单词到字典树种
	 * @param word
	 */
	public void put(String word, V v){
		Node cur = root;
		for (int i = 0; i < word.length(); i++) {
			Character c = word.charAt(i);
			if(!cur.next.containsKey(c)){
				cur.next.put(c, new Node());
			}
			cur = cur.next.get(c);
		}
		if(cur.v != v){
			size ++;
		}
		cur.v = v;
	}
	/**
	 * 是否包含
	 * @param word
	 * @return
	 */
	public boolean constains(String word){
		Node cur = root;
		for (int i = 0; i < word.length(); i++) {
			Character c = word.charAt(i);
			if(!cur.next.containsKey(c)){
				return false;
			}
			cur = cur.next.get(c);
		}
		//如果当前的key不是个单词
		return cur.v != null;
	}
	
	/**
	 * 是否是前缀
	 * @param prefix
	 * @return
	 */
	public Map<String, V> getPrefixValue(String prefix){
		Map<String, V> nodeValueMap = new HashMap<>();
		
		Node cur = root;
		for (int i = 0; i < prefix.length(); i++) {
			Character c = prefix.charAt(i);
			if(!cur.next.containsKey(c)){
				return nodeValueMap;
			}
			cur = cur.next.get(c);
		}
		return getWordValue(cur, prefix, nodeValueMap);
	}
	
	private Map<String, V> getWordValue(Node node, String prefix, Map<String, V> nodeValueMap){
		//递归到底的情况
		if(node.next == null){
			nodeValueMap.put(prefix, node.v);
			return nodeValueMap;
		}
		//中间是个正常的单词
		if(node.v != null){
			nodeValueMap.put(prefix, node.v);
		}
		for(Character c : node.next.keySet()){
			nodeValueMap.putAll(getWordValue(node.next.get(c), prefix.concat(c.toString()), nodeValueMap));
		}
		
		return nodeValueMap;
	}
	
	/**
	 * 匹配单词，支持“.”表示任意一个字符
	 * @param matchWord
	 * @return
	 */
	public V get(String word){
		Node cur = root;
		for (int i = 0; i < word.length(); i++) {
			Character c = word.charAt(i);
			if(!cur.next.containsKey(c)){
				return null;
			}
			cur = cur.next.get(c);
		}
		return cur.v;
	}
}
