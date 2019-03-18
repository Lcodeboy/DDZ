package com.game.framework.util.filterword;

import java.util.List;

public interface Filterword {

	/**
	 * 过滤字 如果有非法字符则返回false.
	 * @param str
	 * @return
	 */
	public boolean filter(String str);
	
	/**
	 * 获得第一个匹配的关键字
	 * @param text 被检查的文本
	 * @return 匹配到的关键字
	 */
	public String match(String text);
	
	/**
	 * 找出所有匹配的关键字
	 * @param text 被检查的文本
	 * @return 匹配的词列表
	 */
	public List<String> matchAll(String text);

	/**
	 * 找出所有匹配的关键字
	 * @param text 被检查的文本
	 * @param limit 限制匹配个数
	 * @return 匹配的词列表
	 */
	public List<String> matchAll(String text, int limit);
	
	/**
	 * 找出所有匹配的关键字<br>
	 * 密集匹配原则：假如关键词有 ab,b，文本是abab，将匹配 [ab,b,ab]<br>
	 * 贪婪匹配（最长匹配）原则：假如关键字a,ab，最长匹配将匹配[a, ab]
	 * 
	 * @param text 被检查的文本
	 * @param limit 限制匹配个数   limit = -1 表示不不限制返回个数
	 * @param isDensityMatch 是否使用密集匹配原则
	 * @param isGreedMatch 是否使用贪婪匹配（最长匹配）原则
	 * @return 匹配的词列表
	 */
	public List<String> matchAll(String text, int limit, boolean isDensityMatch, boolean isGreedMatch) ;
	
}
