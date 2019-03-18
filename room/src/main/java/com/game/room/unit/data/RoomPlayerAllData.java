package com.game.room.unit.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.game.common.cache.redis.bean.CBeanMatchLog;
import com.game.message.proto.ProtoContext_Common.PlayerBackpackAllData;
import com.game.message.proto.ProtoContext_Common.PlayerCardData;
import com.game.message.proto.ProtoContext_Common.PlayerFriendListItem;
import com.game.message.proto.ProtoContext_Common.PlayerPanelData;
import com.game.message.proto.ProtoContext_Common.PlayerRelationData;
import com.game.message.proto.ProtoContext_Common.PlayerRelationDataIni;
import com.game.message.proto.ProtoContext_Common.PlayerType;
import com.game.message.proto.ProtoContext_Common.RelationType;

public class RoomPlayerAllData {

	//	ID
	private int playerid = 0;
	//	名称
	private String playername = null;
	//	
	private boolean sex = false;
	//
	private String head = null;
	//
	private String birthday = null;
	//
	private String city = null;
	//
	private int coin1 = 0;
	//
	private int coin2 = 0;
	//
	private int coin3 = 0;
	//	魅力值
	private int glamour = 0;
	//	礼物值
	private float giftvalue = 0;
	//	消费值
	private float consume = 0;
	//	好友列表
	private HashMap<Integer, RoomPlayerRelation> friendMap = null;
	//	临时好友列表
	private HashMap<Integer, RoomPlayerRelation> tempFriendMap = null;
	//	陌生人列表
	private HashMap<Integer, RoomPlayerRelation> strangerMap = null;
	//	黑名单列表
	private HashMap<Integer, RoomPlayerRelation> blackMap = null;
	//	
	private ArrayList<CBeanMatchLog> matchLog = null;
	//	背包
	private PlayerBackpackData backpack = null;
	
	private boolean flag = true;
	
	private void clearCache() {
		if( friendMap == null ) {
			friendMap = new HashMap<Integer, RoomPlayerRelation>();
		}
		
		friendMap.clear();
		
		//	---------------
		
		if( tempFriendMap == null ) {
			tempFriendMap = new HashMap<Integer, RoomPlayerRelation>();
		}
		
		tempFriendMap.clear();
		
		//	---------------
		
		if( strangerMap == null ) {
			strangerMap = new HashMap<Integer, RoomPlayerRelation>();
		}
		
		strangerMap.clear();
		
		//	---------------
		
		if( blackMap == null ) {
			blackMap = new HashMap<Integer, RoomPlayerRelation>();
		}
		
		blackMap.clear();
	}
	
	public void copyToAll( PlayerPanelData.Builder builder, PlayerBackpackAllData.Builder backpackBuilder) {
		
		builder.setCoin1(coin1);
		builder.setCoin2(coin2);
		builder.setCoin3(coin3);
		
		builder.setCharm(glamour);
		builder.setGift(giftvalue);
		builder.setConsume((int)(consume * 100));
		
		builder.setName(playername);
		builder.setBirthday(birthday);
		builder.setSex(sex);
		builder.setHead(head);
		builder.setCity(city);
		builder.setType( PlayerType.NORMAL );
		
		ArrayList<Integer> count = backpack.getCount();
		ArrayList<Integer> itemid = backpack.getItemid();
				
		for (int i = 0; i < count.size(); i++) {
			
			backpackBuilder.addCount( count.get(i) );
			backpackBuilder.addItemid( itemid.get(i) );
		}
	}
	public PlayerFriendListItem.Builder copyTo( PlayerFriendListItem.Builder builder ) {
		builder.setName(playername);
		builder.setHead(head);
		builder.setPlayerid(playerid);
		
		return builder;
	}
	
	public PlayerRelationData.Builder copyTo( PlayerRelationData.Builder builder ) {
		builder.setBirthday(birthday);
		builder.setName(playername);
		builder.setHead(head);
		builder.setPlayerID(playerid);
		builder.setSex(sex);
		
		return builder;
	}
	
	public PlayerCardData.Builder copyTo( PlayerCardData.Builder builder ) {
		builder.setBirthday(birthday);
		builder.setName(playername);
		builder.setHead(head);
		builder.setPlayerID(playerid);
		builder.setSex(sex);
		
		return builder;
	}
	
	public PlayerPanelData.Builder copyTo( PlayerPanelData.Builder builder ) {
		builder.setBirthday(birthday);
		builder.setName(playername);
		builder.setHead(head);
		builder.setSex(sex);
		
		return builder;
	}
	
	public void copyFrom( List<PlayerRelationDataIni> iniList ) {
		
		clearCache();
		RoomPlayerRelation rpp = null;
		
		for( PlayerRelationDataIni ini : iniList ) {
			rpp = new RoomPlayerRelation();
			rpp.copyFrom(ini);
			
			if( ini.getRelationType() == RelationType.FRIEND_FORMAL_VALUE ) {
				//	正式好友
				friendMap.put(rpp.getPlayerid(), rpp);
			} else if( ini.getRelationType() == RelationType.FRIEND_TEMP_VALUE ) {
				//	临时好友
				tempFriendMap.put(rpp.getPlayerid(), rpp);
			} else if( ini.getRelationType() == RelationType.FRIEND_BLACK_VALUE ) {
				//	黑名单
				blackMap.put(rpp.getPlayerid(), rpp);
			} else if( ini.getRelationType() == RelationType.FRIEND_NO_VALUE ) {
				//	陌生人
				strangerMap.put(rpp.getPlayerid(), rpp);
			}
			
		}
	}
	
	public void copyFrom( PlayerPanelData playerPanelData ) {
		
		birthday = playerPanelData.getBirthday();
		playername = playerPanelData.getName();
		head = playerPanelData.getHead();
		city = playerPanelData.getCity();
		sex = playerPanelData.getSex();
		coin1 = playerPanelData.getCoin1();
		coin2 = playerPanelData.getCoin2();
		coin3 = playerPanelData.getCoin3();
		
		glamour = playerPanelData.getCharm();
		giftvalue = playerPanelData.getGift();
		consume = playerPanelData.getConsume();
		
		//	
		List<Integer> countList = playerPanelData.getBackpack().getCountList();
		int length = countList.size();
		//
		List<Integer> itemidList = playerPanelData.getBackpack().getItemidList();
		
		backpack.setCount( new ArrayList<Integer>() );
		backpack.setItemid( new ArrayList<Integer>() );
		
		for(int i = 0; i< length; i++) {
			
			backpack.getCount().add(countList.get(i));
			
			backpack.getItemid().add(itemidList.get(i));
		}
	}
	
	public PlayerBackpackData getBackpack() {
		return backpack;
	}

	public void setBackpack(PlayerBackpackData backpack) {
		this.backpack = backpack;
	}

	public float getConsume() {
		return consume;
	}
	
	public void setConsume(float consume) {
		this.consume = consume;
	}
	
	public int getGlamour() {
		return glamour;
	}

	public void setGlamour(int glamour) {
		this.glamour = glamour;
	}

	public float getGiftvalue() {
		return giftvalue;
	}


	public void setGiftvalue(float giftvalue) {
		this.giftvalue = giftvalue;
	}


	public int getCoin1() {
		return coin1;
	}

	public void setCoin1(int coin1) {
		this.coin1 = coin1;
	}

	public int getCoin2() {
		return coin2;
	}

	public void setCoin2(int coin2) {
		this.coin2 = coin2;
	}

	public int getCoin3() {
		return coin3;
	}

	public void setCoin3(int coin3) {
		this.coin3 = coin3;
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
		if(flag) {
			this.sex = sex;
			flag = false;
		}		
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
	
	public HashMap<Integer, RoomPlayerRelation> getFriendMap() {
		return friendMap;
	}

	public void setFriendMap(HashMap<Integer, RoomPlayerRelation> friendMap) {
		this.friendMap = friendMap;
	}

	public HashMap<Integer, RoomPlayerRelation> getTempFriendMap() {
		return tempFriendMap;
	}

	public void setTempFriendMap(HashMap<Integer, RoomPlayerRelation> tempFriendMap) {
		this.tempFriendMap = tempFriendMap;
	}

	public HashMap<Integer, RoomPlayerRelation> getStrangerMap() {
		return strangerMap;
	}

	public void setStrangerMap(HashMap<Integer, RoomPlayerRelation> strangerMap) {
		this.strangerMap = strangerMap;
	}

	public HashMap<Integer, RoomPlayerRelation> getBlackMap() {
		return blackMap;
	}

	public void setBlackMap(HashMap<Integer, RoomPlayerRelation> blackMap) {
		this.blackMap = blackMap;
	}
	
	public ArrayList<CBeanMatchLog> getMatchLog() {
		return matchLog;
	}

	public void setMatchLog(ArrayList<CBeanMatchLog> matchLog) {
		this.matchLog = matchLog;
	}
	
}
