package com.game.common.staticdata;

import java.io.File;
import java.util.HashMap;

import com.game.common.staticdata.bean.GiftShopStaticData;
import com.game.framework.dataprovier.AbstractStaticDataProvider;
import com.game.framework.exception.StaticDataException;

public class GiftShopStaticDataProvider extends AbstractStaticDataProvider<GiftShopStaticData> {

	private HashMap<Integer, GiftShopStaticData> dataMap = new HashMap<Integer, GiftShopStaticData>();
	
	public HashMap<Integer, GiftShopStaticData> getDataMap() {
		return dataMap;
	}

	public void setDataMap(HashMap<Integer, GiftShopStaticData> dataMap) {
		this.dataMap = dataMap;
	}

	public GiftShopStaticDataProvider(File staticDataFile) {
	
		super(staticDataFile);
	}
	
	public Object getAllStaticDataObject() {
		return dataMap;
	}
	
	@Override
	protected void createStaticData(String[] fieldNameArray, Object[] fieldValueArray) throws StaticDataException {
		
		GiftShopStaticData giftShopStaticData = new GiftShopStaticData();
		
		giftShopStaticData.setID( (int)fieldValueArray[0] );
		giftShopStaticData.setNumber( (int)fieldValueArray[1] );
		giftShopStaticData.setName( (String)fieldValueArray[2] );
		giftShopStaticData.setPrice( (int)fieldValueArray[3] );
		giftShopStaticData.setGlamour( (int)fieldValueArray[4] );
		giftShopStaticData.setGiftvalue( (float)fieldValueArray[5] );
		giftShopStaticData.setConsume( (float)fieldValueArray[6] );
		giftShopStaticData.setPicture( (String)fieldValueArray[7] );
		giftShopStaticData.setFlash( (String)fieldValueArray[8] );
		giftShopStaticData.setCoin( (int)fieldValueArray[9] );
		giftShopStaticData.setAtlasname( (String)fieldValueArray[10] );
		
		dataMap.put( (Integer)fieldValueArray[0], giftShopStaticData);
	}
	
	@Override
	public Object getStaticData(Object key) {
		return dataMap.get(key);
	}
}
