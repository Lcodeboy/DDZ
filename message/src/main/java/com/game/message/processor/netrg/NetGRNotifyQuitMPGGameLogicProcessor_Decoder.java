package com.game.message.processor.netrg;

import com.game.framework.network.AbstractGameLogicProcessor;
import com.game.framework.network.MessageContext;
import com.game.message.proto.ProtoContext_RG.NetGRNotifyQuitMPGGame;
import java.io.InputStream;

//	///////////////////////////////////
//	Author AkSu
//	MessageBuild Create
//	2019-02-19 14:59:02
//	///////////////////////////////////
public abstract class NetGRNotifyQuitMPGGameLogicProcessor_Decoder extends AbstractGameLogicProcessor {
		
	public Object decoder() throws Exception {
		return NetGRNotifyQuitMPGGame.parseFrom(MessageContext.EMPTY_ARRAY);
	}
		
	public Object decoder( byte[] array, int off, int length ) throws Exception {
		return NetGRNotifyQuitMPGGame.newBuilder().mergeFrom(array, off, length).build();
	}

	public Object decoder( InputStream in ) throws Exception {
		return NetGRNotifyQuitMPGGame.newBuilder().mergeFrom(in).build();
	}	
}
