package com.game.common.staticdata;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.game.common.staticdata.bean.GameItemStaticData;
import com.game.common.staticdata.bean.RobatStaticData;
import com.game.framework.dataprovier.AbstractStaticDataProvider;
import com.game.framework.exception.StaticDataException;

/**
 * 游戏道具
 * 
 * @author wuyang
 * @date 2018年7月5日
 *
 */
public class RobatStaticDataProvider extends AbstractStaticDataProvider<GameItemStaticData> {

	// 保存数据，一键多值
	private HashMap<Integer, RobatStaticData> dataMap = new HashMap<Integer, RobatStaticData>();
	
	public HashMap<Integer, RobatStaticData> getDataMap() {
		return dataMap;
	}

	public void setDataMap(HashMap<Integer, RobatStaticData> dataMap) {
		this.dataMap = dataMap;
	}

	public RobatStaticDataProvider(File staticDataFile) {
		super(staticDataFile);
	}

	@Override
	protected void createStaticData(String[] fieldNameArray, Object[] fieldValueArray) throws StaticDataException {
		RobatStaticData robatStaticData = new RobatStaticData();

		robatStaticData.setId((int) fieldValueArray[0]);
		robatStaticData.setName((String) (fieldValueArray[1]));
		robatStaticData.setSex((boolean) fieldValueArray[2]);
		robatStaticData.setBirthday((String) fieldValueArray[3]);
		robatStaticData.setHead((String) fieldValueArray[4]);
		robatStaticData.setCity((String) fieldValueArray[5]);
		
		dataMap.put(robatStaticData.getId(), robatStaticData);
	}

	@Override
	public Object getStaticData(Object key) {
		return dataMap.get(key);
	}
	public List<Object> getAllStaticData() {
		return new ArrayList<>(dataMap.values());
		
	}
}
