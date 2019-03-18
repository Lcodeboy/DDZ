package com.game.datacenter.matchgame;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.game.datacenter.DataCenterServer;
import com.game.datacenter.matchgame.operation.MatchOperation_Entry;
import com.game.datacenter.service.PlayerService;
import com.game.datacenter.unit.DataCenterPlayerUnit;
import com.game.framework.ProcessGlobalData;
import com.game.framework.util.ToKenUtil;
import com.game.message.MessageConstant;
import com.game.message.MessageWriteUtil;
import com.game.message.proto.ProtoContext_Common.GameType;
import com.game.message.proto.ProtoContext_Common.PlayerCardData;
import com.game.message.proto.ProtoContext_Common.PlayerState;
import com.game.message.proto.ProtoContext_RD.NetDRSearchPlayerByGame;

/**
 * 匹配结果的通知
 */
public class MatchResultNotify {

	@Autowired
	private PlayerService playerService = null;
	
	/**
	 * 
	 * @param playerId
	 * @throws Exception
	 */
	public boolean matchRobatResultNotify( MatchOperation_Entry entry, GameType gameType ) throws Exception {

		int playerId = entry.getPlayerId();
		
		ArrayList<MEMPlayerRelationItem> relationList = entry.getRelation().getRelationList();
		
		DataCenterServer dataCenter = (DataCenterServer)ProcessGlobalData.gameProcess;
		
		ArrayList<DataCenterPlayerUnit> robotPlayerUnitList = playerService.getAllRobatPlayerUnit();
		
		String gameUUID = null;
		
		NetDRSearchPlayerByGame.Builder resultBuilder = NetDRSearchPlayerByGame.newBuilder();
		PlayerCardData.Builder playerCardDataBuilder = PlayerCardData.newBuilder();
		
		DataCenterPlayerUnit requestPlayerUnit = playerService.getPlayerUnit( playerId );
		
		boolean contains = false;
		
		for( DataCenterPlayerUnit robotPlayerUnit : robotPlayerUnitList ) {
			
			if( robotPlayerUnit.getPlayerState() == PlayerState.GAMEING ) {
				continue;
			}
			contains = false;
			//	检查机器人与playerid是否有关系
			for (MEMPlayerRelationItem item : relationList) {
				if (item.getPlayerId() == robotPlayerUnit.getPlayer32Id()) {
					contains = true;
					break;
				}
			}
			
			if( contains ) {
				continue;
			}
			//	给用户添加机器人为临时好友
//			relationService.addTempFriend(dataCenter, requestPlayerUnit, robotPlayerUnit);
			
			robotPlayerUnit.setPlayerState(PlayerState.GAMEING);
			requestPlayerUnit.setPlayerState(PlayerState.GAMEING);
			
			robotPlayerUnit.getPlayerBase().copyTo(playerCardDataBuilder);
			playerCardDataBuilder.setPlayerState(PlayerState.ONLINE);
			gameUUID = ToKenUtil.tokentmp(64);
			resultBuilder.setGameUUID(gameUUID);
			resultBuilder.setActive(true);
			resultBuilder.setPlayerId(playerId);
			resultBuilder.setCardData(playerCardDataBuilder.build());
			resultBuilder.setGameType(gameType);
			resultBuilder.setResult(MessageConstant.TFRESULT_TRUE);
				
			MessageWriteUtil.writeAndFlush(
					dataCenter.getServerNode(requestPlayerUnit.getRoomServerId()).getNode().getIoClient(), 
					playerId,
					resultBuilder.build() );
			
			return true;
			
		}
		
		return false;
	}
	
	NetDRSearchPlayerByGame.Builder resultBuilder = NetDRSearchPlayerByGame.newBuilder();
	PlayerCardData.Builder playerCardDataBuilder = PlayerCardData.newBuilder();
	
	/**
	 * 匹配成功(游戏找人)后的回调
	 * 
	 * @param playerArray
	 */
	public void matchResultNotify(ArrayList<int[]>[] matchResult) throws Exception {
		
		if( matchResult.length <= 0 ) {
			return;
		}
		
		resultBuilder.clear();
		playerCardDataBuilder.clear();
		
		int requestPlayerId = 0;
		int responsePlayerId = 0;
		
		ArrayList<int[]> resultList = null;
		int[] result = null;
		
		DataCenterPlayerUnit requestPlayerUnit = null;
		
		DataCenterPlayerUnit responsePlayerUnit = null;
		
		String gameUUID = null;
		
		DataCenterServer dataCenter = (DataCenterServer)ProcessGlobalData.gameProcess;
		
		//	从猜飞机开始通知匹配结果
		for( int i = 0, size = matchResult.length; i < size; i++ ) {
			
			resultList = matchResult[i];
			//	
			resultBuilder.setGameType(GameType.valueOf(i));
			
			for( int j = 0, jsize = resultList.size(); j < jsize; j++ ) {
				
				result = resultList.get(j);
				gameUUID = ToKenUtil.tokentmp(64);
				
				requestPlayerId = result[0];
				responsePlayerId = result[1];
				
				responsePlayerUnit = playerService.getPlayerUnit(responsePlayerId);
				requestPlayerUnit = playerService.getPlayerUnit(requestPlayerId);
				
				resultBuilder.setGameUUID(gameUUID);
				
				responsePlayerUnit.setPlayerState(PlayerState.GAMEING);
				requestPlayerUnit.setPlayerState(PlayerState.GAMEING);
				
				resultBuilder.setActive(true);
				
				responsePlayerUnit.getPlayerBase().copyTo(playerCardDataBuilder);
				
				playerCardDataBuilder.setPlayerState(PlayerState.ONLINE);
				resultBuilder.setCardData(playerCardDataBuilder.build());
				resultBuilder.setPlayerId(requestPlayerId);
				resultBuilder.setResult(MessageConstant.TFRESULT_TRUE);
				
				MessageWriteUtil.writeAndFlush(dataCenter.
						getServerNode(requestPlayerUnit.getRoomServerId()).getNode().
						getIoClient(), requestPlayerId, resultBuilder.build());
				
				playerCardDataBuilder.clear();
				
				resultBuilder.setActive(false);
				
				requestPlayerUnit.getPlayerBase().copyTo(playerCardDataBuilder);
				playerCardDataBuilder.setPlayerState(PlayerState.ONLINE);
				resultBuilder.setCardData(playerCardDataBuilder.build());
				resultBuilder.setPlayerId(responsePlayerId);
				resultBuilder.setResult(MessageConstant.TFRESULT_TRUE);
				
				MessageWriteUtil.writeAndFlush(dataCenter.
						getServerNode(responsePlayerUnit.getRoomServerId()).
						getNode().getIoClient(), responsePlayerId, resultBuilder.build());
				
				playerCardDataBuilder.clear();
			}
		} 
	}
	
	/**
	 * 游戏找人超时匹配通知
	 * @param timeoutEntryList
	 */
	public void matchFailNotify(ArrayList<MatchOperation_Entry> timeOutEntryList) {
		
		NetDRSearchPlayerByGame.Builder builder = NetDRSearchPlayerByGame.newBuilder();
		PlayerCardData.Builder dataBuilder = PlayerCardData.newBuilder();
		DataCenterServer dataCenter = (DataCenterServer)ProcessGlobalData.gameProcess;
		
		for (MatchOperation_Entry entry : timeOutEntryList) {
			
			int playerId = entry.getPlayerId();
			
			DataCenterPlayerUnit playerUnit = playerService.getPlayerUnit(playerId);
			playerUnit.getPlayerBase().copyTo(dataBuilder);
			playerUnit.setGameType(null);
			builder.setResult(MessageConstant.TFRESULT_FALSE);
			builder.setCardData(dataBuilder.build());
			builder.setPlayerId(playerId);
			
			MessageWriteUtil.writeAndFlush(dataCenter.
					getServerNode(playerUnit.getRoomServerId()).
					getNode().getIoClient(), playerId, builder.build());
			
			dataBuilder.clear();
			builder.clear();
		}
		timeOutEntryList.clear();
	}
}
