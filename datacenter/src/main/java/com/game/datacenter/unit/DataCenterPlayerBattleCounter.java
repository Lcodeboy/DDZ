package com.game.datacenter.unit;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import com.game.message.proto.ProtoContext_Common.GameType;

public class DataCenterPlayerBattleCounter {
	
	private CopyOnWriteArrayList<AtomicInteger> playBattleCounter = null;
	
	public DataCenterPlayerBattleCounter() {
		AtomicInteger[] counterArray = new AtomicInteger[GameType.values().length];
		
		for( int i = 0, size = counterArray.length; i < size; i++ ) {
			counterArray[i] = new AtomicInteger(0);
		}
		
		playBattleCounter = new CopyOnWriteArrayList<AtomicInteger>(counterArray);
	}
	
	public int incrBattleCounter(GameType gameType) {
		return playBattleCounter.get( gameType.getNumber() ).incrementAndGet();				
	}
	
	public void addGame( ) {
		playBattleCounter.add( new AtomicInteger() );
	}
	
	public void clearCounter( ) {
		for( int i = 0, size = playBattleCounter.size(); i < size; i++ ) {
			playBattleCounter.get(i).set(0);
		}
	}
	
	public int getBattleCounter( GameType gameType ) {
		return playBattleCounter.get( gameType.getNumber() ).get();
	}
	
}
