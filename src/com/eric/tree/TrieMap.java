package com.eric.tree;

import java.util.HashMap;
import java.util.Map;
/**
 * �ֵ���
 * @author Eric
 *
 */
public class TrieMap<V> {
	/**
	 * �ڵ��ڲ���
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
	 * ���ڵ�
	 */
	private Node root;
	/**
	 * �ڵ����
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
	 * ��ӵ��ʵ��ֵ�����
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
	 * �Ƿ����
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
		//�����ǰ��key���Ǹ�����
		return cur.v != null;
	}
	
	/**
	 * �Ƿ���ǰ׺
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
		//�ݹ鵽�׵����
		if(node.next == null){
			nodeValueMap.put(prefix, node.v);
			return nodeValueMap;
		}
		//�м��Ǹ������ĵ���
		if(node.v != null){
			nodeValueMap.put(prefix, node.v);
		}
		for(Character c : node.next.keySet()){
			nodeValueMap.putAll(getWordValue(node.next.get(c), prefix.concat(c.toString()), nodeValueMap));
		}
		
		return nodeValueMap;
	}
	
	/**
	 * ƥ�䵥�ʣ�֧�֡�.����ʾ����һ���ַ�
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
