package com.game.datacenter.matchgame.operation;

import com.game.datacenter.matchgame.MatchOperation;
import com.game.message.proto.ProtoContext_Common.GameType;

/**
 * 
 * @author suchen
 * @date 2018年7月3日下午5:08:20
 */
public class MatchOperation_Entry implements MatchOperation {
	
	//
	private int playerId = 0;
	//
	private GameType gameType = null;
	//
	private long time = 0;
	//	保存关系
	private MEMPlayerRelation relation = null;
	
	public MatchOperation_Entry() {}
	
	public MatchOperation_Entry(int playerId, GameType gameType, long time, MEMPlayerRelation relation ) {
		this.playerId = playerId;
		this.gameType = gameType;
		this.time = time;
		this.relation = relation;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public GameType getGameType() {
		return gameType;
	}

	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	public MEMPlayerRelation getRelation() {
		return relation;
	}

	public void setRelation(MEMPlayerRelation relation) {
		this.relation = relation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + playerId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MatchOperation_Entry other = (MatchOperation_Entry) obj;
		if (playerId != other.playerId)
			return false;
		return true;
	}
	
	
}
