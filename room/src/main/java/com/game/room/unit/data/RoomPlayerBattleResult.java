package com.game.room.unit.data;

import com.game.message.proto.ProtoContext_Common.GameType;
import com.game.message.proto.ProtoContext_RD.NetRDNotifyBattleResult;
import com.game.message.proto.ProtoContext_RG.NetGRNotifyBattleResult;


public class RoomPlayerBattleResult {
	private int active_playerid;
	 
	private GameType  gametype ;
	
	public void copyFrom(NetGRNotifyBattleResult playerBattleresult) {
		active_playerid = playerBattleresult.getActiveID();
		gametype = playerBattleresult.getGametype();
		
	}
	public void copyFrom(NetRDNotifyBattleResult playerBattleresult) {
		active_playerid = playerBattleresult.getActiveID();
		gametype = playerBattleresult.getGametype();
		
	}
	public NetGRNotifyBattleResult.Builder copyTo(NetGRNotifyBattleResult.Builder builder){
		builder.setActiveID(active_playerid);
		builder.setGametype(gametype);
		return builder;
	}

	public int getActive_playerid() {
		return active_playerid;
	}

	public void setActive_playerid(int active_playerid) {
		this.active_playerid = active_playerid;
	}

	public GameType getGametype() {
		return gametype;
	}

	public void setGametype(GameType gametype) {
		this.gametype = gametype;
	}


}
