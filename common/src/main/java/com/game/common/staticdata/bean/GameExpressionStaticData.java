package com.game.common.staticdata.bean;

import com.game.framework.dataprovier.StaticData;

/**
 * 游戏表情
 * 
 *
 */
public class GameExpressionStaticData implements StaticData{

	private int id;
	private String expressionname;
	private String atlasname;
	private int expressiontype;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getExpressionname() {
		return expressionname;
	}
	public void setExpressionname(String expressionname) {
		this.expressionname = expressionname;
	}
	public String getAtlasname() {
		return atlasname;
	}
	public void setAtlasname(String atlasname) {
		this.atlasname = atlasname;
	}
	public int getExpressiontype() {
		return expressiontype;
	}
	public void setExpressiontype(int expressiontype) {
		this.expressiontype = expressiontype;
	}
	
	
	
}
