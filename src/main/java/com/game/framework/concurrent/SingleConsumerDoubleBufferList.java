package com.game.framework.concurrent;

import java.util.ArrayList;

/**
 * 单消费者多生产者双缓冲队列
 * 
 */
public class SingleConsumerDoubleBufferList<T> {

	//
	private ArrayList<T> readList = null;
	//
	private ArrayList<T> writeList = null;
	//
	private Object lock = new Object();

	public SingleConsumerDoubleBufferList(int size) {
		readList = new ArrayList<T>(size);
		writeList = new ArrayList<T>(size);
	}

	// 多个生产者调用
	public void add(T t) {
		synchronized (lock) {
			writeList.add(t);
		}
	}
	
	// 单个消费者调用
	public ArrayList<T> getAll() {
		synchronized (lock) {
			ArrayList<T> temp = readList;
			readList = writeList;
			writeList = temp;
			return readList;
		}
		
	}

}