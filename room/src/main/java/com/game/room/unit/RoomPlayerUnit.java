package com.game.room.unit;

import com.game.common.cache.redis.DefaultRedisContextFacade;
import com.game.framework.ProcessGlobalData;
import com.game.framework.network.AbstractGamePlayer;
import com.game.message.proto.ProtoContext_CR.NetRCDuadMSG;
import com.game.message.proto.ProtoContext_Common.PlayerState;
import com.game.room.unit.data.RoomPlayerAllData;

/**
 * 
 * @author suchen
 * @date 2018年4月10日 下午1:13:41 email xiaochen_su@126.com
 */
public class RoomPlayerUnit extends AbstractGamePlayer {

	//
	private RoomPlayerAllData roomPlayerAllData = new RoomPlayerAllData();
	//
	private volatile PlayerState playerState = null;
	
	private Long position = null;

	private double latitude = 0;

	private double longitude = 0;
	
	private volatile int chatTargetPlayerId = 0;
	//
	protected NetRCDuadMSG netRCDuadMSG = null;
	
	private volatile long pushGameSceneTime = 0;
	
	private volatile long pushDoubleTime = 0;
	
	public RoomPlayerUnit() {

	}
	
	public int getChatTargetPlayerId() {
		return chatTargetPlayerId;
	}
	
	public void setChatTargetPlayerId( int chatTargetPlayerId ) {
		this.chatTargetPlayerId = chatTargetPlayerId;
	}
	
	public long getPushGameSceneTime() {
		return pushGameSceneTime;
	}

	public void setPushGameSceneTime(long pushGameSceneTime) {
		this.pushGameSceneTime = pushGameSceneTime;
	}

	public long getPushDoubleTime() {
		return pushDoubleTime;
	}

	public void setPushDoubleTime(long pushDoubleTime) {
		this.pushDoubleTime = pushDoubleTime;
	}

	public RoomPlayerAllData getRoomPlayerAllData() {
		return roomPlayerAllData;
	}

	public void setRoomPlayerAllData(RoomPlayerAllData roomPlayerAllData) {
		this.roomPlayerAllData = roomPlayerAllData;
	}
	
	public PlayerState getPlayerState() {
		return playerState;
	}

	public void setPlayerState(PlayerState playerState) {
		this.playerState = playerState;
	}
	
	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void online() {
		ProcessGlobalData.gameLog.basic("Player Online " + roomPlayerAllData.getPlayerid());
		setPlayerState(PlayerState.ONLINE);
		joinProcessTime = System.currentTimeMillis();
		quitProcessTime = 0;
	}
	
	public void offline() {
		ProcessGlobalData.gameLog.basic("Player Offline " + roomPlayerAllData.getPlayerid());
		setPlayerState(PlayerState.OFFLINE);
		joinProcessTime = 0;
		quitProcessTime = System.currentTimeMillis();
		int playerId = roomPlayerAllData.getPlayerid();
		DefaultRedisContextFacade redisContext = (DefaultRedisContextFacade) ProcessGlobalData.redisContextFacade;
		setToKen(null);
		boolean result = redisContext.delNearbyPeople( playerId, latitude, longitude );
		ProcessGlobalData.gameLog.basic("Romove NearbyPeople " + result + " " + "latitude " + latitude + " " + "longitude " + longitude);
		if(result == false && latitude != 0 && longitude != 0) {
			ProcessGlobalData.gameLog.waring("Remove NearbyPeople Fail" + " " +"playerId = " + playerId);
		}
	}
}
