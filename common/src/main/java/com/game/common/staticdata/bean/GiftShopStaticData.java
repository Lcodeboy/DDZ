package com.game.common.staticdata.bean;

import com.game.framework.dataprovier.StaticData;

public class GiftShopStaticData implements StaticData{

	//	序号
	private int ID;
	//	道具编号
	private int number;
	//	礼物名称
	private String name;
	//	价格
	private int price;
	//	魅力值
	private int glamour;
	//	礼物值
	private float giftvalue;
	//	消费值
	private float consume;
	//	图片
	private String picture;
	//	动画
	private String flash;
	//	货币类型
	private int coin;
	//	图集名称
	private String atlasname;
	
	public String getAtlasname() {
		return atlasname;
	}
	public void setAtlasname(String atlasname) {
		this.atlasname = atlasname;
	}
	public float getConsume() {
		return consume;
	}
	public void setConsume(float consume) {
		this.consume = consume;
	}
	public int getCoin() {
		return coin;
	}
	public void setCoin(int coin) {
		this.coin = coin;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getGlamour() {
		return glamour;
	}
	public void setGlamour(int glamour) {
		this.glamour = glamour;
	}
	public float getGiftvalue() {
		return giftvalue;
	}
	public void setGiftvalue(float giftvalue) {
		this.giftvalue = giftvalue;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getFlash() {
		return flash;
	}
	public void setFlash(String flash) {
		this.flash = flash;
	}
}
