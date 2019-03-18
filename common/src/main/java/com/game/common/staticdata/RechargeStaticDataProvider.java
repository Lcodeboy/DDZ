package com.game.common.staticdata;

import java.io.File;
import java.util.ArrayList;

import com.game.common.staticdata.bean.GameItemStaticData;
import com.game.common.staticdata.bean.RechargeStaticData;
import com.game.framework.dataprovier.AbstractStaticDataProvider;
import com.game.framework.exception.StaticDataException;

/**
 * 钻石商城
 * 
 * @author wuyang
 * @date 2018年7月5日
 *
 */
public class RechargeStaticDataProvider extends AbstractStaticDataProvider<GameItemStaticData> {

	private ArrayList<RechargeStaticData> dataList = new ArrayList<RechargeStaticData>();
	
	public RechargeStaticDataProvider(File staticDataFile) {
		super(staticDataFile);
	}
	
	public ArrayList<RechargeStaticData> getDataList() {
		return dataList;
	}

	@Override
	protected void createStaticData(String[] fieldNameArray, Object[] fieldValueArray) throws StaticDataException {
		RechargeStaticData rechargeStaticData = new RechargeStaticData();
		
		rechargeStaticData.setId( (int)fieldValueArray[0] );
		rechargeStaticData.setDiamonds( (int)fieldValueArray[1] );
		rechargeStaticData.setRmb( (int)fieldValueArray[2] );
		rechargeStaticData.setAdditional( ( (int)fieldValueArray[3] ) );
		rechargeStaticData.setBiaoji( ( (int)fieldValueArray[4] ) );
		
		dataList.add(rechargeStaticData);
	}
	
}
