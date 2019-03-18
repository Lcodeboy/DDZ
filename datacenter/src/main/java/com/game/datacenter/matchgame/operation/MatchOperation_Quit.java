package com.game.datacenter.matchgame.operation;

import com.game.datacenter.matchgame.MatchOperation;
import com.game.message.proto.ProtoContext_Common.GameType;

public class MatchOperation_Quit implements MatchOperation {

	//
	private int playerId = 0;
	//
	private GameType gameType = null;

	public MatchOperation_Quit(int playerId, GameType gameType) {
		super();
		this.playerId = playerId;
		this.gameType = gameType;
	}

	public int getPlayerId() {
		return playerId;
	}

	public GameType getGameType() {
		return gameType;
	}

	
	
}
