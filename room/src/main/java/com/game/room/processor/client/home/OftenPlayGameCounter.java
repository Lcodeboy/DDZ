package com.game.room.processor.client.home;

import com.game.message.proto.ProtoContext_Common.GameType;

public class OftenPlayGameCounter implements Comparable<OftenPlayGameCounter> {
	private int gameCounter = 0;

	private GameType gameType = null;

	private OftenPlayGameCounter() {

	}

	public static OftenPlayGameCounter valueOf(int counter, GameType gameType) {
		OftenPlayGameCounter instance = new OftenPlayGameCounter();
		instance.gameCounter = counter;
		instance.gameType = gameType;
		return instance;
	}

	public int getGameCounter() {
		return gameCounter;
	}

	public void setGameCounter(int gameCounter) {
		this.gameCounter = gameCounter;
	}

	public GameType getGameType() {
		return gameType;
	}

	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}

	@Override
	public int compareTo(OftenPlayGameCounter o) {
		return o.gameCounter - gameCounter;
	}
	
	public String toString() {
		return "GameType " + gameType + " gameCounter " + gameCounter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gameType == null) ? 0 : gameType.hashCode());
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
		OftenPlayGameCounter other = (OftenPlayGameCounter) obj;
		if (gameType != other.gameType)
			return false;
		return true;
	}
	

}
