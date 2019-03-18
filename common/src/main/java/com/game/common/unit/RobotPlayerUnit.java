package com.game.common.unit;

import com.game.common.staticdata.bean.RobatStaticData;
import com.game.message.proto.ProtoContext_Common.PlayerCardData;
import com.game.message.proto.ProtoContext_Common.PlayerPanelData;
import com.game.message.proto.ProtoContext_Common.PlayerState;
import com.game.message.proto.ProtoContext_Common.PlayerType;

/**
 * 
 * @author suchen
 * @date 2018年8月14日下午12:31:18
 */
public class RobotPlayerUnit {
	//
	private int playerId = 0;
	
	// 一级货币 元宝 其他用户无法看到此数据
	private int coin1 = 1;
	// 二级货币 金币 暂时不用 其他用户无法看到此数据
	private int coin2 = 2;
	// 三级货币 银币 暂时不用 其他用户无法看到此数据
	private int coin3 = 3;
	// 魅力值
	private int charm = 4;
	// 礼物值
	private float gift = 5;
	// 消费值
	private float consume = 6;
	//
	private PlayerState playerState = null;
	//
	private RobatStaticData robatStaticData = null;


	public void copyTo( PlayerPanelData.Builder panelData ) {
		panelData.setSex(robatStaticData.isSex());
		panelData.setBirthday(robatStaticData.getBirthday());
		panelData.setCoin1(0);
		panelData.setCoin2(0);
		panelData.setCoin3(0);
		panelData.setGift(22F);
		panelData.setCharm(33);
		//	机器人的消费值
		panelData.setConsume(0);
		panelData.setName(robatStaticData.getName());
		panelData.setHead(robatStaticData.getHead());
		panelData.setType(PlayerType.ROBAT);
		panelData.setCity(robatStaticData.getCity());
	}
	
	public void copyTo( PlayerCardData.Builder cardData ) {
		
	}
	
	public static RobotPlayerUnit valueOf(RobatStaticData robatStaticData) {
		RobotPlayerUnit robatPlayerUnit = new RobotPlayerUnit();
		
		robatPlayerUnit.setPlayerId( robatStaticData.getId() );
		robatPlayerUnit.robatStaticData = robatStaticData;
		robatPlayerUnit.playerState = PlayerState.ONLINE;
				
		return robatPlayerUnit;
	}
	
	public RobatStaticData getRobatStaticData() {
		return robatStaticData;
	}
	
	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
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

	public int getCharm() {
		return charm;
	}

	public void setCharm(int charm) {
		this.charm = charm;
	}

	public float getGift() {
		return gift;
	}

	public void setGift(float gift) {
		this.gift = gift;
	}

	public float getConsume() {
		return consume;
	}

	public void setConsume(float consume) {
		this.consume = consume;
	}

	public PlayerState getPlayerState() {
		return playerState;
	}

	public void setPlayerState(PlayerState playerState) {
		this.playerState = playerState;
	}

	
}
