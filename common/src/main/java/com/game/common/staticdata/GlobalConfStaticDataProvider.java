package com.game.common.staticdata;

import java.io.File;

import com.game.common.staticdata.bean.GlobalConfStaticData;
import com.game.framework.dataprovier.AbstractStaticDataProvider;
import com.game.framework.exception.StaticDataException;

public class GlobalConfStaticDataProvider extends AbstractStaticDataProvider<GlobalConfStaticData>{

	GlobalConfStaticData globalConfStaticData = new GlobalConfStaticData();
	
	public GlobalConfStaticDataProvider(File staticDataFile) {
		super(staticDataFile);
	}

	@Override
	protected void createStaticData(String[] fieldNameArray, Object[] fieldValueArray) throws StaticDataException {
		
		globalConfStaticData.setInitcoin1( (int)fieldValueArray[11] );
//		System.out.println( " coin1 " + (int)fieldValueArray[11] );
		globalConfStaticData.setInitcoin2( (int)fieldValueArray[12] );
		globalConfStaticData.setInitcoin3( (int)fieldValueArray[13] );
		globalConfStaticData.setRankcount( ( (int)fieldValueArray[14] ) );
		globalConfStaticData.setReadyGoTime( ( (float)fieldValueArray[52] ) );

		//  ------ GameRoom 用------
		globalConfStaticData.setAnimalChessGameDurationTime((int)fieldValueArray[54]);
		globalConfStaticData.setAnimalChessPlayerOperationTimeOut( (int)fieldValueArray[4] );
		globalConfStaticData.setAnimalChessWaitAllJoinTimeOut((int)fieldValueArray[56]);
		globalConfStaticData.setAnimalChessWaitAllReadyTimeOut((int)fieldValueArray[55]);

		globalConfStaticData.setGolangGameDurationTime((int)fieldValueArray[61]);
		globalConfStaticData.setGolangPlayerOperationTimeOut( (int)fieldValueArray[7] );
		globalConfStaticData.setGolangWaitAllJoinTimeOut((int)fieldValueArray[59]);
		globalConfStaticData.setGolangWaitAllReadyTimeOut((int)fieldValueArray[60]);

		globalConfStaticData.setGuessAircraftGameDurationTime((int)fieldValueArray[58]);
		globalConfStaticData.setGuessAircraftPlayerOperationTimeOut( (int)fieldValueArray[6] );
		globalConfStaticData.setGuessAircraftWaitAllJoinTimeOut((int)fieldValueArray[57]);
		globalConfStaticData.setGuessAircraftWaitAllReadyTimeOut( (int)fieldValueArray[5] );
		
		globalConfStaticData.setDiamond( (String)fieldValueArray[62] );

		
	}

	@Override
	public Object getStaticData(Object key) {
		return globalConfStaticData;
	}
}
