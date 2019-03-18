package com.game.common.staticdata;

import java.io.File;
import java.util.HashMap;

import com.game.common.staticdata.bean.ChatExpressionStaticData;
import com.game.framework.dataprovier.AbstractStaticDataProvider;
import com.game.framework.exception.StaticDataException;

/**
 * 聊天表情处理类
 * 
 * @author wuyang
 * @date 2018年7月5日
 *
 */
public class ChatExpressionStaticDataProvider extends AbstractStaticDataProvider<ChatExpressionStaticData>{

	//保存数据，一键多值
	private HashMap<String, ChatExpressionStaticData> dataMap = new HashMap<String, ChatExpressionStaticData>();
	
	public HashMap<String, ChatExpressionStaticData> getDataMap() {
		return dataMap;
	}

	public void setDataMap(HashMap<String, ChatExpressionStaticData> dataMap) {
		this.dataMap = dataMap;
	}

	public ChatExpressionStaticDataProvider(File staticDataFile) {
		super(staticDataFile);
	}

	@Override
	protected void createStaticData(String[] fieldNameArray, Object[] fieldValueArray) throws StaticDataException {
	
		ChatExpressionStaticData chatExpressionStaticData = new ChatExpressionStaticData();
		
		chatExpressionStaticData.setId( (int)fieldValueArray[0] );
		chatExpressionStaticData.setName( (String)fieldValueArray[1] );
		chatExpressionStaticData.setPicture((String)fieldValueArray[2]);
		chatExpressionStaticData.setJian((String)fieldValueArray[3]);
		chatExpressionStaticData.setAtlasname((String)fieldValueArray[4]);
		
		dataMap.put((String)fieldValueArray[3], chatExpressionStaticData);
	}

	@Override
	public Object getStaticData(Object key) {
	
		return dataMap.get( key );
	
	}
	
}
