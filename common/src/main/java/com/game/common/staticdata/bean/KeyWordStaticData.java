package com.game.common.staticdata.bean;

import com.game.framework.dataprovier.StaticData;

/**
 * 屏蔽字
 * 
 *
 */
public class KeyWordStaticData implements StaticData{

	//
	private int id;
	//
	private String filtername;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFiltername() {
		return filtername;
	}
	public void setFiltername(String filtername) {
		this.filtername = filtername;
	}
	
}
