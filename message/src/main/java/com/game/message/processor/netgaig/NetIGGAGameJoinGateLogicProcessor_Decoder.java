package com.game.message.processor.netgaig;

import com.game.framework.network.AbstractGameLogicProcessor;
import com.game.framework.network.MessageContext;
import com.game.message.proto.ProtoContext_GAIG.NetIGGAGameJoinGate;
import java.io.InputStream;

//	///////////////////////////////////
//	Author AkSu
//	MessageBuild Create
//	2019-02-19 14:59:02
//	///////////////////////////////////
public abstract class NetIGGAGameJoinGateLogicProcessor_Decoder extends AbstractGameLogicProcessor {
		
	public Object decoder() throws Exception {
		return NetIGGAGameJoinGate.parseFrom(MessageContext.EMPTY_ARRAY);
	}
		
	public Object decoder( byte[] array, int off, int length ) throws Exception {
		return NetIGGAGameJoinGate.newBuilder().mergeFrom(array, off, length).build();
	}

	public Object decoder( InputStream in ) throws Exception {
		return NetIGGAGameJoinGate.newBuilder().mergeFrom(in).build();
	}	
}
