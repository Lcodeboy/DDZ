package com.game.framework.util.queue;

import java.util.LinkedList;

public class SwapQueue<T> {

	private LinkedList<T> readQueue = null;
	
	private LinkedList<T> writeQueue = null;
	
	public SwapQueue() {
		readQueue = new LinkedList<>();
		writeQueue = new LinkedList<>();
	}
	
	public void swap() {
		LinkedList<T> temp = readQueue;
		readQueue = writeQueue;
		writeQueue = temp;
	}
	
	public void copy() {
		while( !readQueue.isEmpty() ) {
			writeQueue.addLast(readQueue.poll());
		}
	}
	
	public LinkedList<T> getReadQueue() {
		return readQueue;
	}
	
	public LinkedList<T> getWriteQueue() {
		return writeQueue;
	}

}
