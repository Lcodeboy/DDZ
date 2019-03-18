package com.game.common.staticdata.bean;

import com.game.framework.dataprovier.StaticData;

/**
 * 
 */
public class RobatStaticData implements StaticData {
	//
	private int id;
	//
	private String name;
	//
	private boolean sex;
	//
	private String birthday;
	//
	private String head;
	//
	private String city;

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

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
