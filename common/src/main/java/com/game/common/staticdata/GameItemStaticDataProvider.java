package com.game.common.staticdata;

import java.io.File;
import java.util.HashMap;

import com.game.common.staticdata.bean.GameItemStaticData;
import com.game.framework.dataprovier.AbstractStaticDataProvider;
import com.game.framework.exception.StaticDataException;

/**
 * 游戏道具
 * 
 * @author wuyang
 * @date 2018年7月5日
 *
 */
public class GameItemStaticDataProvider extends AbstractStaticDataProvider<GameItemStaticData> {

	// 保存数据，一键多值
	private HashMap<String, GameItemStaticData> dataMap = new HashMap<String, GameItemStaticData>();
	
	public HashMap<String, GameItemStaticData> getDataMap() {
		return dataMap;
	}

	public void setDataMap(HashMap<String, GameItemStaticData> dataMap) {
		this.dataMap = dataMap;
	}

	public GameItemStaticDataProvider(File staticDataFile) {
		super(staticDataFile);
	}

	@Override
	protected void createStaticData(String[] fieldNameArray, Object[] fieldValueArray) throws StaticDataException {
		GameItemStaticData gameItemStaticData = new GameItemStaticData();

		// 每次调用createStaticData方法，则将本行的数据保存至GameItemStaticData中
		gameItemStaticData.setId((int) fieldValueArray[0]);
		gameItemStaticData.setName((String) (fieldValueArray[1]));
		gameItemStaticData.setClientSource((String) fieldValueArray[2]);

		dataMap.put((String) fieldValueArray[1], gameItemStaticData);
	}

	@Override
	public Object getStaticData(Object key) {
		return dataMap.get(key);
	}

}
