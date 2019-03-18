package com.game.framework.network.node;

import java.util.concurrent.atomic.AtomicInteger;

import com.game.framework.process.SubSystem;

public class ServerNode<T extends SubSystem> {

	//
	private int id = 0;
	//
	private volatile ServerNodeState nodeState = ServerNodeState.NONE;
	//
	private int nodeType = 0;
	//
	private T node = null;
	//
	private AtomicInteger peopleCount = new AtomicInteger(0);

	public ServerNode() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ServerNodeState getNodeState() {
		return nodeState;
	}

	public void setNodeState(ServerNodeState nodeState) {
		this.nodeState = nodeState;
	}

	public int getNodeType() {
		return nodeType;
	}

	public void setNodeType(int nodeType) {
		this.nodeType = nodeType;
	}

	public T getNode() {
		return node;
	}

	public void setNode(T node) {
		this.node = node;
	}

	public int getPeopleCount() {
		return peopleCount.get();
	}

	public void setPeopleCount(int peopleCount) {
		this.peopleCount.set(peopleCount);
	}

	public int incrPeopleCount() {
		return peopleCount.incrementAndGet();
	}

	public int decrPeopleCount() {
		return peopleCount.decrementAndGet();
	}

	public int addPeopleCount(int count) {
		return peopleCount.addAndGet(count);
	}

}
