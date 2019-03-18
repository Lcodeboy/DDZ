package com.game.framework.mmo.scene;

import com.game.framework.ProcessGlobalData;
import com.game.framework.concurrent.AtomicIntIDBuilder;

import java.util.concurrent.LinkedBlockingQueue;

public abstract class GameSceneManager implements Runnable {

	protected GameScene[] gameSceneArray = null;
	
	private Thread thread = null;

	private String name = null;

	private volatile boolean running = false;
	
	private LinkedBlockingQueue<GameSceneManagerCommand> commandQueue = null;
	
	private int id = 0;
	
	private static final AtomicIntIDBuilder idBuilder = new AtomicIntIDBuilder(1);
	
	public GameSceneManager( String name ) {
		this( name, true );
	}
	
	public GameSceneManager( String name, boolean asyncQueue ) {
		this.name = name;
		thread = new Thread( this );
		thread.setName("GameSceneThread " + name);
		if( asyncQueue ) {
			commandQueue = new LinkedBlockingQueue<>();
		}
		id = idBuilder.getInt();
	}
	
	public int getId() {
		return id;
	}
	
	public void addGameSceneManagerCommand( GameSceneManagerCommand command ) {
		commandQueue.add( command );
	}
	
	public String getName() {
		return name;
	}

	public GameScene[] getGameSceneArray() {
		return gameSceneArray;
	}

	public void setGameSceneArray(GameScene[] gameSceneArray) {
		this.gameSceneArray = gameSceneArray;
	}
	
	public void start() {
		running = true;
		thread.start();
	}
	
	public void stop() {
		running = false;
	}
	
	public void run() {
		
		long nowTime = 0;
		
		while( running ) {
			nowTime = System.currentTimeMillis();
			if( commandQueue != null ) {
				GameSceneManagerCommand command = null;
				while( (command = commandQueue.poll()) != null ) {
					try {
						command.processCommand(nowTime);
					} catch (Exception e) {
						ProcessGlobalData.gameLog.basicErr("", e);
					}
				}
			}
			for( GameScene gameScene : gameSceneArray ) {
				try {
					gameScene.update(nowTime);
				} catch (Exception e) {
					ProcessGlobalData.gameLog.basicErr("", e);
				}
			}
			try {
				Thread.sleep(ProcessGlobalData.sceneTickTimeOut);
			} catch (InterruptedException e) {
				ProcessGlobalData.gameLog.basicErr("", e);
			}
			
		}
		
	}
}
