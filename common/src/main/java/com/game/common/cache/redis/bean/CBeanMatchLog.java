package com.game.common.cache.redis.bean;

import com.alibaba.fastjson.JSONObject;
import com.game.message.proto.ProtoContext_Common.PlayerMatchBattleData;

/**
 * 
 */
public class CBeanMatchLog {

	private int playerId;
	
	private String head;
	
	private String name;
	
	private boolean sex;
	
	public CBeanMatchLog() {
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CBeanMatchLog other = (CBeanMatchLog) obj;
		if (playerId != other.playerId)
			return false;
		return true;
	}


	public CBeanMatchLog copyFrom(JSONObject json) {
		playerId = json.getInteger("id");
		name = json.getString("name");
		head = json.getString("head");
		sex = json.getBoolean("sex");
		return this;
	}
	
	public PlayerMatchBattleData.Builder copyTo( PlayerMatchBattleData.Builder data ) {

		data.setPlayerid(playerId);
		data.setHead(head);
		data.setSex(sex);
		data.setName(name);
		return data;
	}
	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
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

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

}
