package com.game.game.scene;

import com.game.message.proto.ProtoContext_Common.MPGRoomType;

/**
 * 
 * @author suchen
 * @date 2018年11月26日上午10:39:17
 */
public class MPRConfig {

	//
	private boolean openMPR = false;
	// MPRStartIndex=1
	// MPRStopIndex=300
	private ClosedIntervalConfigLimit mprStartAndStop = null;

	// MPRTypeStartIndex=1:1;2:121;3:241;
	// MPRTypeStopIndex=1:120;2:240;3:300;
	// Index:MPGRoomType
	private ClosedIntervalConfigLimit[] mprTypeStartAndStopMap = null;

	// MPRTypeLineCount=1:120;2:60;
	// Index:MPGRoomType
	private int[] mprTypeLineCount = null;

	// Index:MPGRoomType
//	private ClosedIntervalConfigLimit[] mprTypeSceneIdMap = null;

	private int[] openPeopleCountArray = null;
	private int[] fullPeopleCountArray = null;
	private int[] waitOpenTimeOutArray = null;
	private int[] useReadyArray = null;
	private int[] openNeedReadyArray = null;
	private int[] waitStartTimeArray = null;
	private int[] waitStartTimeStopEntryArray = null;
	
	public int getSceneCount(MPGRoomType roomType) {
		ClosedIntervalConfigLimit limit = mprTypeStartAndStopMap[roomType.getNumber()];
		return limit.getStopIndex() - limit.getStartIndex() + 1;
	}

	// 闭区间
	public static final class ClosedIntervalConfigLimit {

		private int startIndex = 0;

		private int stopIndex = 0;

		public ClosedIntervalConfigLimit(int startIndex, int stopIndex) {
			this.startIndex = startIndex;
			this.stopIndex = stopIndex;
		}

		public int getStartIndex() {
			return startIndex;
		}

		public int getStopIndex() {
			return stopIndex;
		}

	}

	public boolean isOpenMPR() {
		return openMPR;
	}

	public void setOpenMPR(boolean openMPR) {
		this.openMPR = openMPR;
	}

	public ClosedIntervalConfigLimit getMprStartAndStop() {
		return mprStartAndStop;
	}

	public void setMprStartAndStop(ClosedIntervalConfigLimit mprStartAndStop) {
		this.mprStartAndStop = mprStartAndStop;
	}

	public ClosedIntervalConfigLimit[] getMprTypeStartAndStopMap() {
		return mprTypeStartAndStopMap;
	}

	public void setMprTypeStartAndStopMap(ClosedIntervalConfigLimit[] mprTypeStartAndStopMap) {
		this.mprTypeStartAndStopMap = mprTypeStartAndStopMap;
	}

	public int[] getMprTypeLineCount() {
		return mprTypeLineCount;
	}
	
	public void setMprTypeLineCount(int[] mprTypeLineCount) {
		this.mprTypeLineCount = mprTypeLineCount;
	}

	public int[] getOpenPeopleCountArray() {
		return openPeopleCountArray;
	}

	public void setOpenPeopleCountArray(int[] openPeopleCountArray) {
		this.openPeopleCountArray = openPeopleCountArray;
	}

	public int[] getFullPeopleCountArray() {
		return fullPeopleCountArray;
	}

	public void setFullPeopleCountArray(int[] fullPeopleCountArray) {
		this.fullPeopleCountArray = fullPeopleCountArray;
	}

	public int[] getWaitOpenTimeOutArray() {
		return waitOpenTimeOutArray;
	}

	public void setWaitOpenTimeOutArray(int[] waitOpenTimeOutArray) {
		this.waitOpenTimeOutArray = waitOpenTimeOutArray;
	}
	
	public int[] getUseReadyArray() {
		return useReadyArray;
	}

	public void setUseReadyArray(int[] useReadyArray) {
		this.useReadyArray = useReadyArray;
	}

	public int[] getOpenNeedReadyArray() {
		return openNeedReadyArray;
	}

	public void setOpenNeedReadyArray(int[] openNeedReadyArray) {
		this.openNeedReadyArray = openNeedReadyArray;
	}

	public int getMprTypeLineCount( MPGRoomType roomType ) {
		return mprTypeLineCount[ roomType.getNumber() ];
	}
	
	//	返回这个类型的房间共有多少条线
	public int getMprTypeTotalLineCount( MPGRoomType roomType ) {
		//	TODO @ZXF
		int limit = mprTypeLineCount[roomType.getNumber()];
		ClosedIntervalConfigLimit limitStartAndStop = mprTypeStartAndStopMap[roomType.getNumber()];
		return Math.round((limitStartAndStop.getStopIndex() - limitStartAndStop.getStartIndex() + 1) / limit);
	}

	public int getStartIndexByType( MPGRoomType roomType ) {
		ClosedIntervalConfigLimit limit = mprTypeStartAndStopMap[roomType.getNumber()];
		return limit.getStartIndex();
	}

	public int getStopIndexByType( MPGRoomType roomType ) {
		ClosedIntervalConfigLimit limit = mprTypeStartAndStopMap[roomType.getNumber()];
		return limit.getStopIndex();
	}

	public int[] getWaitStartTimeArray() {
		return waitStartTimeArray;
	}

	public void setWaitStartTimeArray(int[] waitStartTimeArray) {
		this.waitStartTimeArray = waitStartTimeArray;
	}

//	public int[] getWaitStartTimeStopEntryArray() {
//		return waitStartTimeStopEntryArray;
//	}
//
//	public void setWaitStartTimeStopEntryArray(int[] waitStartTimeStopEntryArray) {
//		this.waitStartTimeStopEntryArray = waitStartTimeStopEntryArray;
//	}
		
}
