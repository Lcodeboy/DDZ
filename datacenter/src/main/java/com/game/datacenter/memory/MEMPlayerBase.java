package com.game.datacenter.memory;

import com.game.common.staticdata.bean.RobatStaticData;
import com.game.dao.bean.DBPlayerBase;
import com.game.framework.util.DateUtil;
import com.game.message.proto.ProtoContext_Common.PlayerCardData;
import com.game.message.proto.ProtoContext_Common.PlayerFriendListItem;
import com.game.message.proto.ProtoContext_Common.PlayerPanelData;
import com.game.message.proto.ProtoContext_Common.PlayerRelationDataIni;

public class MEMPlayerBase {
	private int playerid = 0;

	private String playername = null;

	private boolean sex = false;

	private String head = null;

	private String birthday = null;

	private String city = null;
	
	private long createtime;

	private int age;
	
	public MEMPlayerBase() {
		createtime = System.currentTimeMillis();
	}
	
	public void copyFrom( RobatStaticData robatStaticData ) {
		playerid = robatStaticData.getId();
		playername = robatStaticData.getName();
		sex = robatStaticData.isSex();
		head = robatStaticData.getHead();
		birthday = robatStaticData.getBirthday();
		city = robatStaticData.getCity();
		age = DateUtil.calculateAge(birthday);
	}
	
	public PlayerRelationDataIni.Builder copyTo(PlayerRelationDataIni.Builder builder ) {
		builder.setPlayerID(playerid);
		builder.setName(playername);
		builder.setSex(sex);
		builder.setHead(head);
		builder.setBirthday(birthday);
		builder.setCreateTime(System.currentTimeMillis());
		
		return builder;
	}
	public PlayerFriendListItem.Builder copyTo( PlayerFriendListItem.Builder builder ) {
		builder.setName(playername);
		builder.setHead(head);
		builder.setPlayerid(playerid);
		
		return builder;
	}
	public PlayerPanelData.Builder copyTo(PlayerPanelData.Builder builder) {
		builder.setName(playername);
		builder.setSex(sex);
		builder.setCity(city);
		builder.setHead(head);
		builder.setBirthday(birthday);
		return builder;
	}
	
	public PlayerCardData.Builder copyTo(PlayerCardData.Builder builder ) {
		builder.setBirthday(birthday);
		builder.setHead(head);
		builder.setName(playername);
		builder.setPlayerID(playerid);
		builder.setSex(sex);
		builder.setCity(city);
		
		return builder;
	}
	
	public DBPlayerBase copyTo( DBPlayerBase playerBase ) {
		playerBase.setPlayerid(playerid);
		playerBase.setPlayername(playername);
		playerBase.setSex(sex);
		playerBase.setHead(head);
		playerBase.setBirthday(birthday);
		playerBase.setCity(city);
		playerBase.setCreatetime(createtime);
		return playerBase;
	}
	
	public DBPlayerBase copyFrom( DBPlayerBase playerBase ) {
		playerid = playerBase.getPlayerid();
		playername = playerBase.getPlayername();
		sex = playerBase.isSex();
		head = playerBase.getHead();
		birthday = playerBase.getBirthday();
		city = playerBase.getCity();
		createtime = playerBase.getCreatetime();
		age = Math.min(DateUtil.calculateAge(birthday), 100);
		return playerBase;
	}
	
	public int getPlayerid() {
		return playerid;
	}

	public void setPlayerid(int playerid) {
		this.playerid = playerid;
	}

	public String getPlayername() {
		return playername;
	}

	public void setPlayername(String playername) {
		this.playername = playername;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public long getCreatetime() {
		return createtime;
	}

	public void setCreatetime(long createtime) {
		this.createtime = createtime;
	}

	public int getAge() {
		return age;
	}

	public void resetAge() {
		age = Math.min(DateUtil.calculateAge(birthday), 100);
	}
	
}
