package com.game.common.staticdata.bean;

import com.game.framework.dataprovier.StaticData;

/**
 * 
 */
public class RechargeStaticData implements StaticData {
	
	private int id = 0;
	
	private int diamonds = 0;
	
	private int rmb = 0;
	
	private int additional = 0;
	
	private int biaoji = 0;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getRmb() {
		return rmb;
	}

	public void setRmb(int rmb) {
		this.rmb = rmb;
	}

	public int getAdditional() {
		return additional;
	}

	public void setAdditional(int additional) {
		this.additional = additional;
	}

	public int getBiaoji() {
		return biaoji;
	}

	public void setBiaoji(int biaoji) {
		this.biaoji = biaoji;
	}

	public int getDiamonds() {
		return diamonds;
	}

	public void setDiamonds(int diamonds) {
		this.diamonds = diamonds;
	}
	
	
}
