package com.game.datacenter.searchlimit.operation;

import com.game.datacenter.searchlimit.SearchLimitOperation;

public class SearchLimitOperation_Quit implements SearchLimitOperation {
	//
	private int playerId = 0;
	//
	private int age = 0;
	//
	private boolean sex = false;
	
	public SearchLimitOperation_Quit(int playerId, boolean sex, int age) {
		super();
		this.playerId = playerId;
		this.sex = sex;
		this.age = age;	
	}

	public int getPlayerId() {
		return playerId;
	}

	public int getAge() {
		return Math.max(Math.min(age, 100) - 1, 0);
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	@Override
	public int compareTo(SearchLimitOperation e) {
		return e.getPlayerId() - playerId;
	}
	
	

}
