package com.game.common.staticdata;

import java.io.File;
import java.util.HashMap;

import com.game.common.staticdata.bean.PlaneRandomStaticData;
import com.game.framework.dataprovier.AbstractStaticDataProvider;
import com.game.framework.exception.StaticDataException;

public class PlaneRandomStaticDataProvider extends AbstractStaticDataProvider<PlaneRandomStaticData> {

	// 保存数据
	private HashMap<Integer, PlaneRandomStaticData> dataMap = new HashMap<Integer, PlaneRandomStaticData>();

	public HashMap<Integer, PlaneRandomStaticData> getDataMap() {
		return dataMap;
	}

	public void setDataMap(HashMap<Integer, PlaneRandomStaticData> dataMap) {
		this.dataMap = dataMap;
	}

	public PlaneRandomStaticDataProvider(File staticDataFile) {
		super(staticDataFile);
	}

	@Override
	protected void createStaticData(String[] fieldNameArray, Object[] fieldValueArray) throws StaticDataException {

		PlaneRandomStaticData scheduleStaticData = new PlaneRandomStaticData();

		scheduleStaticData.setId((int) fieldValueArray[0]);
		scheduleStaticData.setPlane1center((int) fieldValueArray[1]);
		scheduleStaticData.setPlane1rotate((int) fieldValueArray[2]);
		scheduleStaticData.setPlane2center((int) fieldValueArray[3]);
		scheduleStaticData.setPlane2rotate((int) fieldValueArray[4]);
		scheduleStaticData.setPlane3center((int) fieldValueArray[5]);
		scheduleStaticData.setPlane3rotate((int) fieldValueArray[6]);

		dataMap.put((int) fieldValueArray[0], scheduleStaticData);
	}

	@Override
	public Object getStaticData(Object key) {
		return dataMap.get(key);
	}
}
