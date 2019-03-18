package com.game.common.staticdata;

import java.io.File;
import java.util.HashMap;

import com.game.common.staticdata.bean.PunishmentStaticData;
import com.game.framework.dataprovier.AbstractStaticDataProvider;
import com.game.framework.exception.StaticDataException;

public class PunlishmentStaticDataProvider  extends AbstractStaticDataProvider<PunishmentStaticData>{

	private HashMap<Integer, PunishmentStaticData> dataMap = new HashMap<Integer, PunishmentStaticData>();
	
	
	public PunlishmentStaticDataProvider(File staticDataFile) {
		super(staticDataFile);
		
	}

	@Override
	protected void createStaticData(String[] fieldNameArray, Object[] fieldValueArray) throws StaticDataException {
		PunishmentStaticData punishmentStaticData = new PunishmentStaticData();
		punishmentStaticData.setQuitnumber((int)fieldValueArray[1]);
		punishmentStaticData.setPunishmenttime((int)fieldValueArray[2]);
		dataMap.put((int)fieldValueArray[1],punishmentStaticData);
	}
	
	public HashMap<Integer, PunishmentStaticData> getDataMap() {
		return dataMap;
	}

	public void setDataMap(HashMap<Integer, PunishmentStaticData> dataMap) {
		this.dataMap = dataMap;
	}
	@Override
	public Object getStaticData(Object key) {
		return dataMap.get(key);
	}

}
