package com.game.framework.network.node;

import java.util.concurrent.CopyOnWriteArrayList;

import com.game.framework.process.SubSystem;

public class ServerNodeManager<T extends SubSystem> {
	
	//	
	private CopyOnWriteArrayList<ServerNode<T>> dynamicNodeList = null;
	
	/**
	 * 
	 */
	public ServerNodeManager( ) {
		dynamicNodeList = new CopyOnWriteArrayList<>();
	}
	
	public void addNode( ServerNode<T> node ) {
		dynamicNodeList.add( node );
	}
	
	public ServerNode<T> getServerNode( int id ) {
		return dynamicNodeList.get(id);
	}
	
	public ServerNode<T> getMinPeopleServer( int nodeType ) {
		ServerNode<T> minServerNode = null;
		ServerNode<T> serverNode = null;
		
		for( int i = 0, size = dynamicNodeList.size(); i < size; i++ ) {
			if( (serverNode = dynamicNodeList.get(i)) == null ) {
				continue;
			}
			if( serverNode.getNodeType() == nodeType ) {
				if( minServerNode == null || serverNode.getPeopleCount() < minServerNode.getPeopleCount() ) {
					minServerNode = serverNode;
				}
			}
		}
		
		return minServerNode;
	}
}
