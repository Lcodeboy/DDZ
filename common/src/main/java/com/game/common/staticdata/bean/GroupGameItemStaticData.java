package com.game.common.staticdata.bean;

import com.game.framework.dataprovier.StaticData;

public class GroupGameItemStaticData implements StaticData {

	private int id;

	private String gamename;

	private int openPeopleCount;

	private int fullPeopleCount;

	private int openNeedReady;

	private int useReady;

	private int waitOpenTimeOut;

	private int waitStartTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGamename() {
		return gamename;
	}

	public void setGamename(String gamename) {
		this.gamename = gamename;
	}

	public int getOpenPeopleCount() {
		return openPeopleCount;
	}

	public void setOpenPeopleCount(int openPeopleCount) {
		this.openPeopleCount = openPeopleCount;
	}

	public int getFullPeopleCount() {
		return fullPeopleCount;
	}

	public void setFullPeopleCount(int fullPeopleCount) {
		this.fullPeopleCount = fullPeopleCount;
	}

	public int getOpenNeedReady() {
		return openNeedReady;
	}

	public void setOpenNeedReady(int openNeedReady) {
		this.openNeedReady = openNeedReady;
	}

	public int getUseReady() {
		return useReady;
	}

	public void setUseReady(int useReady) {
		this.useReady = useReady;
	}

	public int getWaitOpenTimeOut() {
		return waitOpenTimeOut;
	}

	public void setWaitOpenTimeOut(int waitOpenTimeOut) {
		this.waitOpenTimeOut = waitOpenTimeOut;
	}

	public int getWaitStartTime() {
		return waitStartTime;
	}

	public void setWaitStartTime(int waitStartTime) {
		this.waitStartTime = waitStartTime;
	}

}
