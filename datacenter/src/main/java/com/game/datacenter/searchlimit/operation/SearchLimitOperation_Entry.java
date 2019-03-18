package com.game.datacenter.searchlimit.operation;

import com.game.datacenter.searchlimit.SearchLimitOperation;

/**
 * 
 * @author suchen
 * @date 2018年8月14日上午10:45:09
 */
public class SearchLimitOperation_Entry implements SearchLimitOperation {
	//
	private int playerId = 0;
	//
	private boolean sex = false;
	//
	private long time = 0;
	//	0 不限 1 男 2 女
	private byte searchSex = 0;
	//
	private int age = 0;
	//	0 和我一样 1比我大 2比我小	3不限
	private byte searchAge = 0;
	//	
	private MEMPlayerRelation relation = null;
	
	public SearchLimitOperation_Entry() {
		
	}
	
	/**
	 * 
	 * @param playerId
	 * @param mysex
	 * @param limit
	 * @param time
	 */
	public SearchLimitOperation_Entry(int playerId, boolean sex, int age, byte searchSex, byte searchAge, long time, MEMPlayerRelation relation) {
		this.playerId = playerId;
		this.sex = sex;
		this.time = time;
		this.searchSex = searchSex;
		this.age = age;
		this.searchAge = searchAge;
		this.relation = relation;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	
	public byte getSearchSex() {
		return searchSex;
	}

	public void setSearchSex(byte searchSex) {
		this.searchSex = searchSex;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}
	
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getAge() {
		return Math.max(Math.min(age, 100) - 1, 0);
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public byte getSearchAge() {
		return searchAge;
	}

	public void setSearchAge(byte searchAge) {
		this.searchAge = searchAge;
	}
	
	public MEMPlayerRelation getRelation() {
		return relation;
	}

	public void setRelation(MEMPlayerRelation relation) {
		this.relation = relation;
	}

	@Override
	public int compareTo(SearchLimitOperation e) {
		return e.getPlayerId() - playerId;
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
		SearchLimitOperation_Entry other = (SearchLimitOperation_Entry) obj;
		if (playerId != other.playerId)
			return false;
		return true;
	}
	
	
}
