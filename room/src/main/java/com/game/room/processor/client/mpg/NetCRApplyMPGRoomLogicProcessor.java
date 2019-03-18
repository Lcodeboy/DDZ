package com.game.room.processor.client.mpg;

import org.springframework.stereotype.Controller;

import com.game.common.cache.redis.DefaultRedisContextFacade;
import com.game.common.staticdata.bean.PunishmentStaticData;
import com.game.framework.ProcessGlobalData;
import com.game.framework.annotation.SocketMessage;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.network.process.SubSystem;
import com.game.message.MessageWriteUtil;
import com.game.message.processor.netcr.NetCRApplyMPGRoomLogicProcessor_Decoder;
import com.game.message.proto.ProtoContext_CR.NetCRApplyMPGRoom;
import com.game.message.proto.ProtoContext_CR.NetRCMPGRoom;
import com.game.message.proto.ProtoContext_Common.FieldValue;
import com.game.message.proto.ProtoContext_Common.GameStaticDataType;
import com.game.message.proto.ProtoContext_Common.GameType;
import com.game.message.proto.ProtoContext_Common.MPGRoomType;
import com.game.message.proto.ProtoContext_Common.TFResult;
import com.game.message.proto.ProtoContext_RIG.NetRIGApplyMPGRoom;
import com.game.room.RoomServer;
import com.game.room.unit.RoomPlayerUnit;

/**
 * 客户端申请一个多人房间
 */
@Controller
@SocketMessage(NetCRApplyMPGRoom.class)
public class NetCRApplyMPGRoomLogicProcessor extends NetCRApplyMPGRoomLogicProcessor_Decoder {

	private static final NetRCMPGRoom FAIL_2 = NetRCMPGRoom.newBuilder().setResult(TFResult.newBuilder().setCode(2).setResult(false)).build();
//	private static final NetRCMPGRoom FAIL_7 = NetRCMPGRoom.newBuilder().setResult(TFResult.newBuilder().setCode(7).setResult(false)).build();
	
	@Override
	public void handler(SubSystem subSystem, GameTCPIOClient ioClient, Object message) throws Exception {
		NetCRApplyMPGRoom requestMSG = (NetCRApplyMPGRoom) message;
		RoomServer roomServer = (RoomServer) subSystem;
		
		if( requestMSG.getGameType() != GameType.Graffiti ) {
			MessageWriteUtil.writeAndFlush(ioClient, FAIL_2);
			return;
		}
		
		RoomPlayerUnit roomPlayerUnit = (RoomPlayerUnit) ioClient.getGamePlayer();

		long now = System.currentTimeMillis();
		
		ProcessGlobalData.gameLog.basic(Thread.currentThread() + "NetCRApplyMPGRoom " + (roomPlayerUnit.getPushDoubleTime() - System.currentTimeMillis()));
		
		if( System.currentTimeMillis() - roomPlayerUnit.getPushDoubleTime() <= 5000 ) {
			return;
		}
		
		roomPlayerUnit.setPushGameSceneTime(now);
		
		MPGRoomType roomType = MPGRoomType.GRAFFITI;
		
		DefaultRedisContextFacade redisContextFacade = (DefaultRedisContextFacade)ProcessGlobalData.redisContextFacade;
		
		//  退出次数
		int	punishTimeCounter = redisContextFacade.getPunishTimeCounter( ioClient.get32StubID() );
		
		if( punishTimeCounter > 0 ) {
			long waitTime = 0;
			long time = redisContextFacade.getGraffitiQuitTime(ioClient.get32StubID());
			if(punishTimeCounter > 5) {
				punishTimeCounter = 5;
			}
			PunishmentStaticData data = (PunishmentStaticData)roomServer.getStaticData(GameStaticDataType.PUBLISTMENT,punishTimeCounter);
			long punishTime = data.getPunishmenttime() * 1000;
			long keepTime = System.currentTimeMillis() - time;
			ProcessGlobalData.gameLog.basic("punishTimeCounter " + punishTimeCounter + " punishTime " + punishTime  + " keepTime " + keepTime + " time " + time + " Static Time " + data.getPunishmenttime() + " PlayerID " + roomPlayerUnit.get32PlayerID() );
			if(keepTime < punishTime ) {
				//	惩罚时间未满
				waitTime = punishTime - keepTime ;
				NetRCMPGRoom.Builder builder = NetRCMPGRoom.newBuilder();
			
				builder.setResult(TFResult.newBuilder().setCode(7).setResult(false).build());
				builder.addFieldArray(FieldValue.newBuilder().setIntValue((int) waitTime));
				MessageWriteUtil.writeAndFlush(ioClient, builder.build());
				return;
			}
		}
		
		NetRIGApplyMPGRoom sendMSG = NetRIGApplyMPGRoom.newBuilder().setRoomType(roomType)
				.setPlayerId(ioClient.get32StubID()).build();
		
		MessageWriteUtil.writeAndFlush(roomServer.getIniGateConn(), ioClient.get32StubID(), sendMSG);
	}
}
