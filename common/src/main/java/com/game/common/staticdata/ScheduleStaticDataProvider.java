package com.game.common.staticdata;

import java.io.File;
import java.util.HashMap;

import com.game.common.staticdata.bean.ScheduleStaticData;
import com.game.framework.dataprovier.AbstractStaticDataProvider;
import com.game.framework.exception.StaticDataException;

public class ScheduleStaticDataProvider extends AbstractStaticDataProvider<ScheduleStaticData>{

	
	// 保存数据
	private HashMap<Integer, ScheduleStaticData> dataMap = new HashMap<Integer, ScheduleStaticData>();
		
	public HashMap<Integer, ScheduleStaticData> getDataMap() {
		return dataMap;
	}

	public void setDataMap(HashMap<Integer, ScheduleStaticData> dataMap) {
		this.dataMap = dataMap;
	}
	

	public ScheduleStaticDataProvider(File staticDataFile) {
		super(staticDataFile);
	}

	@Override
	protected void createStaticData(String[] fieldNameArray, Object[] fieldValueArray) throws StaticDataException {
		
		ScheduleStaticData scheduleStaticData = new ScheduleStaticData();
		
		scheduleStaticData.setId( (int) fieldValueArray[0] );
		scheduleStaticData.setTimeType( (int) fieldValueArray[1] );
		scheduleStaticData.setTime( (String) fieldValueArray[2] );
		scheduleStaticData.setTaskType( (int) fieldValueArray[3] );
		scheduleStaticData.setTaskId( (int) fieldValueArray[4] );
		
		dataMap.put( (int) fieldValueArray[0], scheduleStaticData );
	}
	
	@Override
	public Object getStaticData(Object key) {
		return dataMap.get(key);
	}


}
