package com.game.common.cache.redis.bean;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 */
public class CBeanJoinFriendState {

	// 另一个用户的ID
	private int id;
	// true 我加另一个人
	// flase 另一个人加我
	//	dir 是方向的意思
	private boolean dir;
	// true 已添加
	// flase 等待同意
	private boolean state;
	//	头像
	private String head;
	//	名称
	private String name;
	//	时间
	private long time;
	
	public CBeanJoinFriendState copyFrom( JSONObject json ) {
		id = json.getInteger("id");
		dir = json.getBoolean("dir");
		state = json.getBoolean("state");
		name = json.getString("name");
		head = json.getString("head");
		time = json.getLongValue("time");
		return this;
	}
	
	public JSONObject copyTo( JSONObject json ) {
		json.put("id", id);
		json.put("dir", dir);
		json.put("state", state);
		json.put("name", name);
		json.put("head", head);
		json.put("time", time);
		return json;
	}
	

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public boolean isDir() {
		return dir;
	}

	public void setDir(boolean dir) {
		this.dir = dir;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
}
