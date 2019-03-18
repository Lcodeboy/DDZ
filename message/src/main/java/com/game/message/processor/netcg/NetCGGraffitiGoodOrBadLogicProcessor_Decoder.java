package com.game.message.processor.netcg;

import com.game.framework.network.AbstractGameLogicProcessor;
import com.game.framework.network.MessageContext;
import com.game.message.proto.ProtoContext_CG.NetCGGraffitiGoodOrBad;
import java.io.InputStream;
public abstract class NetCGGraffitiGoodOrBadLogicProcessor_Decoder extends AbstractGameLogicProcessor {
		
	public Object decoder() throws Exception {
		return NetCGGraffitiGoodOrBad.parseFrom(MessageContext.EMPTY_ARRAY);
	}
		
	public Object decoder( byte[] array, int off, int length ) throws Exception {
		return NetCGGraffitiGoodOrBad.newBuilder().mergeFrom(array, off, length).build();
	}

	public Object decoder( InputStream in ) throws Exception {
		return NetCGGraffitiGoodOrBad.newBuilder().mergeFrom(in).build();
	}	
}
