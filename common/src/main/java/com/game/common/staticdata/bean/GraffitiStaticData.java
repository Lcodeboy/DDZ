package com.game.common.staticdata.bean;

import com.game.framework.dataprovier.StaticData;

public class GraffitiStaticData implements StaticData{

	// 序号
	private int id;
	// 词语分类
	private String classification;
	// 词语
	private String terms;
	// 字数
	private int wordnumber;
	
	private int type;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getTerms() {
		return terms;
	}
	public void setTerms(String terms) {
		this.terms = terms;
	}
	public int getWordnumber() {
		return wordnumber;
	}
	public void setWordnumber(int wordnumber) {
		this.wordnumber = wordnumber;
	}
	
	
}
