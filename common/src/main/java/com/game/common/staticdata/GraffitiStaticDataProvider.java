package com.game.common.staticdata;

import java.io.File;
import java.util.HashMap;


import com.game.common.staticdata.bean.GraffitiStaticData;
import com.game.framework.dataprovier.AbstractStaticDataProvider;
import com.game.framework.exception.StaticDataException;

public class GraffitiStaticDataProvider extends AbstractStaticDataProvider<GraffitiStaticData> {

	private HashMap<Integer, GraffitiStaticData> dataMap = new HashMap<Integer, GraffitiStaticData>();

	public HashMap<Integer, GraffitiStaticData> getDataMap() {
		return dataMap;
	}

	public GraffitiStaticDataProvider(File staticDataFile) {
		super(staticDataFile);
	}

	@Override
	protected void createStaticData(String[] fieldNameArray, Object[] fieldValueArray) throws StaticDataException {

		GraffitiStaticData graffitiStaticData = new GraffitiStaticData();
		graffitiStaticData.setId((int)fieldValueArray[0]);
		graffitiStaticData.setClassification((String)fieldValueArray[1]);
		graffitiStaticData.setTerms((String) fieldValueArray[2]);
		graffitiStaticData.setWordnumber((int) fieldValueArray[3]);
		graffitiStaticData.setType((int) fieldValueArray[4]);
		
		dataMap.put( (int) fieldValueArray[0], graffitiStaticData );
	}
	
	@Override
	public Object getStaticData(Object key) {
		return dataMap.get(key);
	}

}
