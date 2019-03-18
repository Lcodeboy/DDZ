package com.game.framework.util.filterword;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * DFA算法构建敏感词树
 *
 */
public class SensitiveWordTree extends HashMap<Character, SensitiveWordTree>{

	private static final long serialVersionUID = -2457516986009433146L;

	/**
	 * 存放每个敏感词的末尾字符
	 */
	private Set<Character> endCharacterSet = new HashSet<>();
	
	public SensitiveWordTree() {}
	
	/**
	 * 添加敏感词
	 */
	public void addWord(String word) {
		SensitiveWordTree parent = null;
		SensitiveWordTree current = this;
		SensitiveWordTree child;
		char currentChar = 0;
		int length = word.length();
		for (int i = 0; i < length; i++) {
			currentChar = word.charAt(i);
			//	只处理合法字符
			if( StopChar.isStopChar(currentChar) == false ) {
				child = current.get(currentChar);
				if(child == null){
					//无子类，新建一个子节点后存放下一个字符
					child = new SensitiveWordTree();
					current.put(currentChar, child);
				}
				parent = current;
				current = child;
			}
		}
		
		if(null != parent){
			parent.setEnd(currentChar);
		}
	}
	
	/**
	 * 增加一组单词
	 * @param words 单词集合
	 */
	public void addWords(Collection<String> words){
		if(false == (words instanceof Set)){
			words = new HashSet<>(words);
		}
		for (String word : words) {
			addWord(word);
		}
	}
	
	/**
	 * 增加一组单词
	 * @param words 单词数组
	 */
	public void addWords(String... words){
		HashSet<String> wordsSet = new HashSet<String>();
		
		for (String string : words) {
			wordsSet.add(string);
		}
		
		for (String word : wordsSet) {
			addWord(word);
		}
	}
	
	/**
	 * 检查当前字符是否是末尾字符
	 * @param c
	 * @return
	 */
	public boolean isEnd(Character c){
		return this.endCharacterSet.contains(c);
	}
	
	/**
	 * 将当前字符添加进末尾字符集合
	 * @param c
	 */
	public void setEnd(Character c){
		if(null != c){
			this.endCharacterSet.add(c);
		}
	}
	
	
}
