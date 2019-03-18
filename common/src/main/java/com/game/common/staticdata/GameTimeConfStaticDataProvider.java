package com.game.common.staticdata;

import java.io.File;
import java.util.HashMap;

import com.game.common.staticdata.bean.GameTimeConfStaticData;
import com.game.framework.dataprovier.AbstractStaticDataProvider;
import com.game.framework.exception.StaticDataException;

public class GameTimeConfStaticDataProvider extends AbstractStaticDataProvider<GameTimeConfStaticData>{

	private HashMap<Integer, GameTimeConfStaticData> dataMap = new HashMap<Integer, GameTimeConfStaticData>();
	
	public GameTimeConfStaticDataProvider(File staticDataFile) {
		super(staticDataFile);
	}

	@Override
	protected void createStaticData(String[] fieldNameArray, Object[] fieldValueArray) throws StaticDataException {
		
		GameTimeConfStaticData gameTimeConfStaticData = new GameTimeConfStaticData();
		
		gameTimeConfStaticData.setId( (int)fieldValueArray[0] );
		gameTimeConfStaticData.setGameType( (String)fieldValueArray[1] );
		gameTimeConfStaticData.setWaitAllJoinTimeOut( (int)fieldValueArray[2] );
		gameTimeConfStaticData.setWaitAllReadyTimeOut( (int)fieldValueArray[3] );
		gameTimeConfStaticData.setGameDurationTime( (int)fieldValueArray[4] );
		gameTimeConfStaticData.setPlayerOperationTimeOut( (int)fieldValueArray[5] );
		
		dataMap.put( (int)fieldValueArray[0] , gameTimeConfStaticData);
	}
	
	@Override
	public Object getStaticData(Object key) {
		return dataMap.get(key);
	}

}
