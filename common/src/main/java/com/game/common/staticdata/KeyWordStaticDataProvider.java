package com.game.common.staticdata;

import java.io.File;

import com.game.common.staticdata.bean.KeyWordStaticData;
import com.game.framework.dataprovier.AbstractStaticDataProvider;
import com.game.framework.exception.StaticDataException;
import com.game.framework.util.filterword.SensitiveWordTree;

/**
 * 
 * @author wuyang
 * @date 2018年8月21日
 *
 */
public class KeyWordStaticDataProvider extends AbstractStaticDataProvider<KeyWordStaticData>{

	//	保存读取的屏蔽字
	private SensitiveWordTree tree = new SensitiveWordTree();
	
	public SensitiveWordTree getTree() {
		return tree;
	}

	public void setTree(SensitiveWordTree tree) {
		this.tree = tree;
	}

	public KeyWordStaticDataProvider(File staticDataFile) {
		super(staticDataFile);
	}

	@Override
	protected void createStaticData(String[] fieldNameArray, Object[] fieldValueArray) throws StaticDataException {
		
		tree.addWord( (String)fieldValueArray[1] );
	}

	@Override
	public Object getAllStaticDataObject() {
		return tree;
	}
}
