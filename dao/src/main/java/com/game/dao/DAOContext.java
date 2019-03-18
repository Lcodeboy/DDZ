package com.game.dao;

import org.springframework.beans.factory.annotation.Autowired;

public class DAOContext {

	@Autowired
	private PlayerBaseMapper playerBaseMapper = null;

	public PlayerBaseMapper getPlayerBaseMapper() {
		return playerBaseMapper;
	}

	public void setPlayerBaseMapper(PlayerBaseMapper playerBaseMapper) {
		this.playerBaseMapper = playerBaseMapper;
	}

}
