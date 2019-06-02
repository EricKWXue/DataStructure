package com.eric.tree;

import java.util.HashMap;
import java.util.Map;
/**
 * 字典树
 * @author Eric
 *
 */
public class Trie {
	/**
	 * 节点内部类
	 * @author Eric
	 *
	 */
	private class Node{
		public boolean word;
		public Map<Character, Node> next;
		
		public Node(boolean isWord){
			this.word = isWord;
			this.next = new HashMap<>();
		}
		
		public Node(){
			this(false);
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
	
	public Trie(){
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
	public void add(String word){
		Node cur = root;
		for (int i = 0; i < word.length(); i++) {
			Character c = word.charAt(i);
			if(!cur.next.containsKey(c)){
				cur.next.put(c, new Node());
			}
			cur = cur.next.get(c);
		}
		//如果当前的key不是个单词，说明没添加过
		if(!cur.word){
			cur.word = true;
			this.size ++;
		}
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
		return cur.word;
	}
	
	/**
	 * 是否是前缀
	 * @param prefix
	 * @return
	 */
	public boolean isPrefix(String prefix){
		Node cur = root;
		for (int i = 0; i < prefix.length(); i++) {
			Character c = prefix.charAt(i);
			if(!cur.next.containsKey(c)){
				return false;
			}
			cur = cur.next.get(c);
		}
		return true;
	}
	
	/**
	 * 匹配单词，支持“.”表示任意一个字符
	 * @param matchWord
	 * @return
	 */
	public boolean search(String matchWord){
		return match(root, matchWord, 0);
	}
	
	private boolean match(Node node, String matchWord, int index){
		if(index == matchWord.length()){
			return node.word;
		}
		Character c = matchWord.charAt(index);
		if(c != '.'){
			if(!node.next.containsKey(c)){
				return false;
			}
			return match(node.next.get(c), matchWord, index + 1);
		}else{
			for (Character character : node.next.keySet()){
				if(match(node.next.get(character), matchWord, index + 1)){
					return true;
				}
			}
			return false;
		}
	}
}
