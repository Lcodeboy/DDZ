package com.game.common.staticdata;

import java.io.File;
import java.util.HashMap;

import com.game.common.staticdata.bean.PininsertionStaticData;
import com.game.framework.dataprovier.AbstractStaticDataProvider;
import com.game.framework.exception.StaticDataException;

public class PininsertionStaticDataProvider extends AbstractStaticDataProvider<PininsertionStaticData>{

	private HashMap<Integer, PininsertionStaticData> dataMap = new HashMap<Integer,PininsertionStaticData>();
	
	
	public HashMap<Integer, PininsertionStaticData> getDataMap() {
		return dataMap;
	}

	public void setDataMap(HashMap<Integer, PininsertionStaticData> dataMap) {
		this.dataMap = dataMap;
	}

	public PininsertionStaticDataProvider(File staticDataFile) {
		super(staticDataFile);
	}

	@Override
	protected void createStaticData(String[] fieldNameArray, Object[] fieldValueArray) throws StaticDataException {
		PininsertionStaticData pininsertionStaticData = new PininsertionStaticData();
		pininsertionStaticData.setId((int) fieldValueArray[0]);
		dataMap.put((int) fieldValueArray[0], pininsertionStaticData);
	}
	
	@Override
	public Object getStaticData(Object key) {
		return dataMap.get(key);
	}

}
