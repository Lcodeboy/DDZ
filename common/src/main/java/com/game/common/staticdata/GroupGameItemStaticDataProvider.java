package com.game.common.staticdata;

import java.io.File;
import java.util.HashMap;

import com.game.common.staticdata.bean.GroupGameItemStaticData;
import com.game.framework.dataprovier.AbstractStaticDataProvider;
import com.game.framework.exception.StaticDataException;

public class GroupGameItemStaticDataProvider extends AbstractStaticDataProvider<GroupGameItemStaticData> {

	private HashMap<Integer, GroupGameItemStaticData> dataMap = new HashMap<Integer, GroupGameItemStaticData>();

	public GroupGameItemStaticDataProvider(File staticDataFile) {
		super(staticDataFile);
	}

	@Override
	protected void createStaticData(String[] fieldNameArray, Object[] fieldValueArray) throws StaticDataException {
		GroupGameItemStaticData configData = new GroupGameItemStaticData();

		configData.setId((int) fieldValueArray[0]);
		configData.setGamename((String) fieldValueArray[1]);
		configData.setOpenPeopleCount((int) fieldValueArray[2]);
		configData.setFullPeopleCount((int) fieldValueArray[3]);
		configData.setOpenNeedReady((int) fieldValueArray[4]);
		configData.setUseReady((int) fieldValueArray[5]);
		configData.setWaitOpenTimeOut((int) fieldValueArray[6]);
		configData.setWaitStartTime((int) fieldValueArray[7]);
		dataMap.put((int) fieldValueArray[0], configData);
	}

	public HashMap<Integer, GroupGameItemStaticData> getDataMap() {
		return dataMap;
	}

	public void setDataMap(HashMap<Integer, GroupGameItemStaticData> dataMap) {
		this.dataMap = dataMap;
	}

	@Override
	public Object getStaticData(Object key) {
		return dataMap.get(key);
	}

}
