package com.game.framework.mmo.scene;

import com.game.framework.network.AbstractGamePlayer;

public class GameScenePlayerUnit extends AbstractGamePlayer {

	private GameScene gameScene = null;
	//	
	private int seatIndex = -1;
	//
	private volatile boolean reconnclose = false;
	
	public GameScenePlayerUnit() {
		
	}
	
	public GameScene getGameScene() {
		return gameScene;
	}

	public void setGameScene(GameScene gameScene) {
		this.gameScene = gameScene;
	}

	public int getSeatIndex() {
		return seatIndex;
	}

	public void setSeatIndex(int seatIndex) {
		this.seatIndex = seatIndex;
	}

	public boolean isReconnclose() {
		return reconnclose;
	}

	public void setReconnclose(boolean reconnclose) {
		this.reconnclose = reconnclose;
	}
	
	
	
}
