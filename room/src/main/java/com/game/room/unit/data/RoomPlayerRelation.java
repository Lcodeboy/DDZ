package com.game.room.unit.data;

import com.game.message.proto.ProtoContext_Common.PlayerBlackDataView;
import com.game.message.proto.ProtoContext_Common.PlayerRelationData;
import com.game.message.proto.ProtoContext_Common.PlayerRelationDataIni;
import com.game.message.proto.ProtoContext_Common.PlayerState;
import com.game.message.proto.ProtoContext_Common.PlayerType;
import com.game.message.proto.ProtoContext_Common.RelationType;

/**
 * 
 */
public class RoomPlayerRelation {

	//	用户ID
	private int playerid;
	//	头部ID
	private String head;
	//	名称ID
	private String name;
	//	关系类型
	private RelationType relationType;
	//	生日
	private String birthday;
	//	性别
	private boolean sex;
	//	
	private long time;
	//	
	private PlayerState playerState = null;
	//	
	private PlayerType playerType = null;
	
	public RoomPlayerRelation copyFrom( PlayerRelationDataIni ini ) {
		playerid = ini.getPlayerID();
		head = ini.getHead();
		name = ini.getName();
		birthday = ini.getBirthday();
		sex = ini.getSex();
		time = ini.getCreateTime();
		relationType = RelationType.valueOf(ini.getRelationType());
		playerType = ini.getPlayerType();
		playerState = ini.getPlayerState();
		return this;
	}
	
	public PlayerRelationData.Builder copyTo(PlayerRelationData.Builder builder ) {
		builder.setName(name);
		builder.setHead(head);
		builder.setSex(sex);
		builder.setBirthday(birthday);
		builder.setRelationType(relationType);
		builder.setPlayerID(playerid);
		builder.setCreateTime(time);
		builder.setPlayerType(playerType);
//		builder.setPlayerState(playerState);
		//	TODO @SuChen 关系数据中的城市
		builder.setCity("北京市");
		return builder;
	}
	
	public PlayerBlackDataView.Builder copyTo(PlayerBlackDataView.Builder builder ) {
		builder.setName(name);
		builder.setHead(head);
		builder.setPlayerid(playerid);
		return builder;
	}
	 
	public int getPlayerid() { 
 		return playerid;
	}

	public void setPlayerid(int playerid) {
		this.playerid = playerid;
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

	public RelationType getRelationType() {
		return relationType;
	}

	public void setRelationType(RelationType relationType) {
		this.relationType = relationType;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public PlayerState getPlayerState() {
		return playerState;
	}

	public void setPlayerState(PlayerState playerState) {
		this.playerState = playerState;
	}

	public PlayerType getPlayerType() {
		return playerType;
	}

	public void setPlayerType(PlayerType playerType) {
		this.playerType = playerType;
	}

	public long getTime() {
		return time;
	}
	
	
}
