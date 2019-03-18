package com.game.framework.util.filterword;

import java.util.ArrayList;
import java.util.List;

/**
 * 敏感词过滤实现
 *
 */
public class FilterwordImpl implements Filterword{

//	SensitiveWordTree tree = new SensitiveWordTree();
	
	private SensitiveWordTree tree = null;
	
	public FilterwordImpl() {}
	
	public FilterwordImpl( SensitiveWordTree _tree) {
		tree = _tree;
	}

	@Override
	public boolean filter(String text) {
		if(null == text){
			return false;
		}
		return null != match(text);
	}

	@Override
	public String match(String text) {
	
		if(null == text){
			return null;
		}
		//	limit参数为1表示，一匹配到就返回
		List<String> matchAll = matchAll(text, 1);
		if( ! matchAll.isEmpty() ){
			return matchAll.get(0);
		}
		return null;
	}

	@Override
	public List<String> matchAll(String text) {
		return matchAll(text, -1);
	}

	@Override
	public List<String> matchAll(String text, int limit) {
		return matchAll(text, limit, false, false);
	}

	@Override
	public List<String> matchAll(String text, int limit, boolean isDensityMatch, boolean isGreedMatch) {
	
		if(null == text){
			return null;
		}
		
		// 保存找到的敏感词列表
		List<String> findedWords = new ArrayList<String>();
		SensitiveWordTree current = tree;
		int length = text.length();
		StringBuilder wordBuffer;//存放查找到的字符缓存。完整出现一个词时加到findedWords中，否则清空
		char currentChar;
		for (int i = 0; i < length; i++) {
			wordBuffer = new StringBuilder();
			for (int j = i; j < length; j++) {
				currentChar = text.charAt(j);
				//	Console.log("i: {}, j: {}, currentChar: {}", i, j, currentChar);
				if(StopChar.isStopChar(currentChar)){
					if(wordBuffer.length() > 0){
						//做为关键词中间的停顿词被当作关键词的一部分被返回
						wordBuffer.append(currentChar);
					}else{
						//停顿词做为关键词的第一个字符时需要跳过
						i++;
					}
					continue;
				}else if(false == current.containsKey(currentChar)){
					//非关键字符被整体略过，重新以下个字符开始检查
					break;
				}
				wordBuffer.append(currentChar);
				if(current.isEnd(currentChar)){
					//到达单词末尾，关键词成立，从此词的下一个位置开始查找
					findedWords.add(wordBuffer.toString());
					if(limit > 0 && findedWords.size() >= limit){
						//超过匹配限制个数，直接返回
						return findedWords;
					}
					if(false == isDensityMatch){
						//如果非密度匹配，跳过匹配到的词
						i = j;
					}
					if(false == isGreedMatch){
						//如果懒惰匹配（非贪婪匹配）。当遇到第一个结尾标记就结束本轮匹配
						break;
					}
				}
				current = current.get(currentChar);
				if(null == current){
					break;
				}
			}
			current = tree;
		}
		return findedWords;
	}
	public SensitiveWordTree getTree() {
		return tree;
	}

	public void setTree(SensitiveWordTree tree) {
		this.tree = tree;
	}
}
