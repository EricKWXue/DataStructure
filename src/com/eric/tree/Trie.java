package com.eric.tree;

import java.util.HashMap;
import java.util.Map;
/**
 * �ֵ���
 * @author Eric
 *
 */
public class Trie {
	/**
	 * �ڵ��ڲ���
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
	 * ���ڵ�
	 */
	private Node root;
	/**
	 * �ڵ����
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
	 * ��ӵ��ʵ��ֵ�����
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
		//�����ǰ��key���Ǹ����ʣ�˵��û��ӹ�
		if(!cur.word){
			cur.word = true;
			this.size ++;
		}
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
		return cur.word;
	}
	
	/**
	 * �Ƿ���ǰ׺
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
	 * ƥ�䵥�ʣ�֧�֡�.����ʾ����һ���ַ�
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
