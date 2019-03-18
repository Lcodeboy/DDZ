package com.game.common.staticdata;

import java.io.File;
import java.util.HashMap;

import com.game.common.staticdata.bean.GameExpressionStaticData;
import com.game.framework.dataprovier.AbstractStaticDataProvider;
import com.game.framework.exception.StaticDataException;

/**
 * 游戏表情
 * 
 * @author wuyang
 * @date 2018年7月5日
 *
 */
public class GameExpressionStaticDataProvider extends AbstractStaticDataProvider<GameExpressionStaticData> {

	private HashMap<Integer, GameExpressionStaticData> dataMap = new HashMap<Integer, GameExpressionStaticData>();

	public HashMap<Integer, GameExpressionStaticData> getDataMap() {
		return dataMap;
	}

	public void setDataMap(HashMap<Integer, GameExpressionStaticData> dataMap) {
		this.dataMap = dataMap;
	}

	public GameExpressionStaticDataProvider(File staticDataFile) {
		super(staticDataFile);
	}

	@Override
	protected void createStaticData(String[] fieldNameArray, Object[] fieldValueArray) throws StaticDataException {

		GameExpressionStaticData gameExpressionStaticData = new GameExpressionStaticData();

		gameExpressionStaticData.setId((int) fieldValueArray[0]);
		gameExpressionStaticData.setExpressionname((String) (fieldValueArray[1]));
		gameExpressionStaticData.setAtlasname((String) fieldValueArray[2]);
		gameExpressionStaticData.setExpressiontype((int)fieldValueArray[3]);

		dataMap.put((Integer) fieldValueArray[0], gameExpressionStaticData);

	}

	@Override
	public Object getStaticData(Object key) {
		return dataMap.get(key);
	}

}
