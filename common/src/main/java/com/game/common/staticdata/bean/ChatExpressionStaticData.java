package com.game.common.staticdata.bean;

import com.game.framework.dataprovier.StaticData;

/**
 * 聊天表情
 * 
 *
 */
public class ChatExpressionStaticData implements StaticData{

	//序号
	private int id;
	//名字
	private String name;
	//资源图片
	private String picture;
	//键位
	private String jian;
	//图集
	private String atlasname;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getJian() {
		return jian;
	}
	public void setJian(String jian) {
		this.jian = jian;
	}
	public String getAtlasname() {
		return atlasname;
	}
	public void setAtlasname(String atlasname) {
		this.atlasname = atlasname;
	}
	
	
	
}
