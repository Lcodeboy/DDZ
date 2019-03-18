package com.game.common.staticdata.bean;

import com.game.framework.dataprovier.StaticData;

/**
 * 游戏道具
 * 
 *
 */
public class GameItemStaticData implements StaticData {

	private int id;
	
	private String name;
	
	private String clientSource;
	
	public GameItemStaticData() {
		super();
	}

	public GameItemStaticData(int id, String name, String clientSource) {
		super();
		this.id = id;
		this.name = name;
		this.clientSource = clientSource;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClientSource() {
		return clientSource;
	}

	public void setClientSource(String clientSource) {
		this.clientSource = clientSource;
	}
	
	
}
