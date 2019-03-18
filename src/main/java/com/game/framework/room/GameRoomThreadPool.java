package com.game.framework.room;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import com.game.framework.concurrent.SingleConsumerDoubleBufferList;

public class GameRoomThreadPool implements Executor {
	//	
	private int length;
	//	
	private GameRoomThread[] threadPool = null;
	//	
	private SingleConsumerDoubleBufferList<GameRoomRunnable>[] doubleBufferArray = null;
	//
	private long waringUseTime = 0;
	//
	private ArrayList<DelayGameRoomRunnable>[] delayGameRoomArray = null;
	
	public long getWaringUseTime() {
		return waringUseTime;
	}
	
	@SuppressWarnings("unchecked")
	public GameRoomThreadPool( int length, int bufferCount, 
			long waringUseTime, String threadNamePrefix ) {
		this.threadPool = new GameRoomThread[ length ];
		this.doubleBufferArray = new SingleConsumerDoubleBufferList[ length ];
		this.waringUseTime = waringUseTime;
		this.length = length;
		this.delayGameRoomArray = new ArrayList[ length ];
		
		for( int i = 0; i < length; i++ ) {
			doubleBufferArray[i] = new SingleConsumerDoubleBufferList<GameRoomRunnable>( bufferCount );
			delayGameRoomArray[i] = new ArrayList<>();
		}
		
		for( int i = 0; i < length; i++ ) {
			threadPool[i] = new GameRoomThread(i, this);
			threadPool[i].setName(threadNamePrefix + " " + i);
		}
	}
	
	public SingleConsumerDoubleBufferList<GameRoomRunnable> getConsumerQueue( int index ) {
		return doubleBufferArray[ index ];
	}
	public ArrayList<DelayGameRoomRunnable> getDelayGameRoomList( int threadIndex ) {
		return delayGameRoomArray[threadIndex];
	}
	
	public void start() {
		for( int i = 0; i < length; i++ ) {
			threadPool[i].start();
		}
	}
	
	@Override
	public void execute( Runnable runnable ) {
		if( runnable instanceof GameRoomRunnable ) {
			GameRoomRunnable flRunnable = (GameRoomRunnable) runnable;
			
			int threadIndex = flRunnable.getThreadIndex();
			
			if( threadIndex > doubleBufferArray.length || threadIndex < 0 ) {
				throw new IndexOutOfBoundsException();
			}
			doubleBufferArray[threadIndex].add(flRunnable);
		} else if ( runnable instanceof DelayGameRoomRunnable ) {
			
			DelayGameRoomRunnable flRunnable = (DelayGameRoomRunnable) runnable;
			
			int threadIndex = flRunnable.getRunnable().getThreadIndex();
			
			if( threadIndex > delayGameRoomArray.length || threadIndex < 0 ) {
				throw new IndexOutOfBoundsException();
			}
			delayGameRoomArray[threadIndex].add(flRunnable);
		}
		
		
	} 

	public int getLength() {
		return length;
	}
}
