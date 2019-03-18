package com.game.common.mpg;

import com.game.message.proto.ProtoContext_Common.GameType;
import com.game.message.proto.ProtoContext_Common.MPGRoomType;

public final class MPGTypeZhuanHuan {

	private MPGTypeZhuanHuan() {
		
	}
	
	public static MPGRoomType gameTypeToRoomType(GameType gameType) {
		switch (gameType) {
		case Graffiti:
			return MPGRoomType.GRAFFITI;
		default:
			break;
		}

		throw new IllegalArgumentException();
	}

	public static GameType roomTypeToGameType(MPGRoomType roomType) {
		switch (roomType) {
		case GRAFFITI:
			return GameType.Graffiti;
		default:
			break;
		}

		throw new IllegalArgumentException();
	}
}
