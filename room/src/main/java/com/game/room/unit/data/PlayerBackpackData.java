package com.game.room.unit.data;

import java.util.ArrayList;

/**
 * room缓存背包数据
 * 
 *
 */
public class PlayerBackpackData {

	private ArrayList<Integer> itemid = null;
	
	private ArrayList<Integer> count = null;

	public ArrayList<Integer> getItemid() {
		return itemid;
	}

	public void setItemid(ArrayList<Integer> itemid) {
		this.itemid = itemid;
	}

	public ArrayList<Integer> getCount() {
		return count;
	}

	public void setCount(ArrayList<Integer> count) {
		this.count = count;
	}

}
