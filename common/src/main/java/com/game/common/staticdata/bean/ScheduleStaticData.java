package com.game.common.staticdata.bean;

import com.game.framework.dataprovier.StaticData;

public class ScheduleStaticData implements StaticData {

	//	序号
	private int id;
	//	时间控制类型
	private int timeType;
	//	具体时间
	private String time;
	//	定时任务类型
	private int taskType;
	//	定时任务ID
	private int taskId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTimeType() {
		return timeType;
	}
	public void setTimeType(int timeType) {
		this.timeType = timeType;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getTaskType() {
		return taskType;
	}
	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	
	
	
}
