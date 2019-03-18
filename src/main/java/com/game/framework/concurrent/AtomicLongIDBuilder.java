package com.game.framework.concurrent;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 原子ID生成器
 * 
 * @author suchen
 * @date 2018年5月15日 下午1:35:02 email xiaochen_su@126.com
 */
public class AtomicLongIDBuilder implements AtomicIDBuilder {

	private AtomicLong counter = null;

	public AtomicLongIDBuilder(long count) {
		counter = new AtomicLong(count);
	}

	public long getID() {
		return counter.getAndIncrement();
	}

}
