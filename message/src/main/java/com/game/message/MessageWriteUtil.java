package com.game.message;

import com.game.framework.network.GameTCPIOClient;

import com.game.message.proto.ProtoContext_WEBD.NetWEBDSendCoin1;

import com.game.message.proto.ProtoContext_WEBD.NetDWEBSendCoin1;

import com.game.message.proto.ProtoContext_WEBD.NetWEBDSendNotify;

import com.game.message.proto.ProtoContext_WEBD.NetWEBDPlayerServerInfo;

import com.game.message.proto.ProtoContext_WEBD.NetDWEBPlayerServerInfo;

import com.game.message.proto.ProtoContext_WEBD.NetWEBDServerInfo;

import com.game.message.proto.ProtoContext_WEBD.NetDWEBServerInfo;

import com.game.message.proto.ProtoContext_GAIG.NetGAIGGameJoinGate;

import com.game.message.proto.ProtoContext_GAIG.NetIGGAGameJoinGate;

import com.game.message.proto.ProtoContext_GAIG.NetIGGATryConnGame;

import com.game.message.proto.ProtoContext_GAIG.NetIGGACreateGame;

import com.game.message.proto.ProtoContext_GAIG.NetGAIGCreateGame;

import com.game.message.proto.ProtoContext_GAIG.NetIGGAActivationMPRRoom;

import com.game.message.proto.ProtoContext_GAIG.NetGAIGSyncMPRData;

import com.game.message.proto.ProtoContext_GAIG.NetGAIGRegMPRRoom;

import com.game.message.proto.ProtoContext_GAIG.NetGAIGActivationMPRRoom;

import com.game.message.proto.ProtoContext_GAIG.NetIGGASyncMPRData;

import com.game.message.proto.ProtoContext_GAIG.NetIGGATiPlayer;

import com.game.message.proto.ProtoContext_GAIG.NetGAIGClearGameSceneData;

import com.game.message.proto.ProtoContext_GAIG.NetIGGAClearGameSceneData;

import com.game.message.proto.ProtoContext_WEBG.NetWEBGCloseDoorServer;

import com.game.message.proto.ProtoContext_WEBG.NetGWEBCloseDoorServer;

import com.game.message.proto.ProtoContext_WEBG.NetWEBGOpenDoorServer;

import com.game.message.proto.ProtoContext_WEBG.NetGWEBOpenDoorServer;

import com.game.message.proto.ProtoContext_WEBG.NetWEBGPlayerServerInfo;

import com.game.message.proto.ProtoContext_WEBG.NetGWEBPlayerServerInfo;

import com.game.message.proto.ProtoContext_WEBG.NetWEBGServerInfo;

import com.game.message.proto.ProtoContext_WEBG.NetGWEBServerInfo;

import com.game.message.proto.ProtoContext_WEBG.NetWEBGOpenRecvMSGLog;

import com.game.message.proto.ProtoContext_WEBG.NetGWEBOpenRecvMSGLog;

import com.game.message.proto.ProtoContext_WEBG.NetWEBGCloseRecvMSGLog;

import com.game.message.proto.ProtoContext_WEBG.NetGWEBCloseRecvMSGLog;

import com.game.message.proto.ProtoContext_WEBG.NetWEBGGameScene;

import com.game.message.proto.ProtoContext_WEBG.NetGWEBGameScene;

import com.game.message.proto.ProtoContext_WEBG.NetWEBGGameSceneGlobalData;

import com.game.message.proto.ProtoContext_WEBG.NetGWEBGameSceneGlobalData;

import com.game.message.proto.ProtoContext_WEBG.NetWEBGDoublePlayerRoomInfo;

import com.game.message.proto.ProtoContext_WEBG.NetGWEBDoublePlayerRoomInfo;

import com.game.message.proto.ProtoContext_WEBG.NetWEBGStopServer;

import com.game.message.proto.ProtoContext_WEBG.NetGWEBStopServer;

import com.game.message.proto.ProtoContext_CR.NetCRJoinRoom;

import com.game.message.proto.ProtoContext_CR.NetRCJoinRoom;

import com.game.message.proto.ProtoContext_CR.NetRCGetAllFriend;

import com.game.message.proto.ProtoContext_CR.NetRCNewFriend;

import com.game.message.proto.ProtoContext_CR.NetRCStateNotify;

import com.game.message.proto.ProtoContext_CR.NetCRDuadMSG;

import com.game.message.proto.ProtoContext_CR.NetRCDuadMSG;

import com.game.message.proto.ProtoContext_CR.NetCRJoinFriend;

import com.game.message.proto.ProtoContext_CR.NetRCJoinFriend;

import com.game.message.proto.ProtoContext_CR.NetRCNewGame;

import com.game.message.proto.ProtoContext_CR.NetCRMatchPlayer;

import com.game.message.proto.ProtoContext_CR.NetRCMatchPlayer;

import com.game.message.proto.ProtoContext_CR.NetCRBlackOther;

import com.game.message.proto.ProtoContext_CR.NetRCBlackOther;

import com.game.message.proto.ProtoContext_CR.NetRCGetAllBlack;

import com.game.message.proto.ProtoContext_CR.NetCRGetAllFriend;

import com.game.message.proto.ProtoContext_CR.NetCRGetAllBlack;

import com.game.message.proto.ProtoContext_CR.NetCRDeleteFriend;

import com.game.message.proto.ProtoContext_CR.NetRCDeleteFriend;

import com.game.message.proto.ProtoContext_CR.NetRCCheckFriend;

import com.game.message.proto.ProtoContext_CR.NetCRCheckFriend;

import com.game.message.proto.ProtoContext_CR.NetCRSeachPlayerByLimit;

import com.game.message.proto.ProtoContext_CR.NetCRSeachPlayerById;

import com.game.message.proto.ProtoContext_CR.NetRCSeachPlayerById;

import com.game.message.proto.ProtoContext_CR.NetCRCheckFriendList;

import com.game.message.proto.ProtoContext_CR.NetRCCheckFriendList;

import com.game.message.proto.ProtoContext_CR.NetRCSeachPlayerByLimit;

import com.game.message.proto.ProtoContext_CR.NetCRCancelMatchPlayer;

import com.game.message.proto.ProtoContext_CR.NetRCCancelMatchPlayer;

import com.game.message.proto.ProtoContext_CR.NetCRShowPlayerData;

import com.game.message.proto.ProtoContext_CR.NetRCShowPlayerData;

import com.game.message.proto.ProtoContext_CR.NetCRBattleMatchLogList;

import com.game.message.proto.ProtoContext_CR.NetRCBattleMatchLogList;

import com.game.message.proto.ProtoContext_CR.NetCRGetNearbyPeople;

import com.game.message.proto.ProtoContext_CR.NetCRUpdateNearby;

import com.game.message.proto.ProtoContext_CR.NetRCGetNearbyPeople;

import com.game.message.proto.ProtoContext_CR.NetCRSendGift;

import com.game.message.proto.ProtoContext_CR.NetRCSendGift;

import com.game.message.proto.ProtoContext_CR.NetCRGetRankFirst;

import com.game.message.proto.ProtoContext_CR.NetRCGetRankFirst;

import com.game.message.proto.ProtoContext_CR.NetCRGetRank;

import com.game.message.proto.ProtoContext_CR.NetRCGetRank;

import com.game.message.proto.ProtoContext_CR.NetCRSeachPlayerByLimitLog;

import com.game.message.proto.ProtoContext_CR.NetRCSeachPlayerByLimitLog;

import com.game.message.proto.ProtoContext_CR.NetCRCancelSeachPlayerByLimit;

import com.game.message.proto.ProtoContext_CR.NetRCCancelSeachPlayerByLimit;

import com.game.message.proto.ProtoContext_CR.NetCROftenPlayGame;

import com.game.message.proto.ProtoContext_CR.NetRCOftenPlayGame;

import com.game.message.proto.ProtoContext_CR.NetCRPhotosBackGround;

import com.game.message.proto.ProtoContext_CR.NetRCPhotosBackGround;

import com.game.message.proto.ProtoContext_CR.NetCRModifyMyselfData;

import com.game.message.proto.ProtoContext_CR.NetRCModifyMyselfData;

import com.game.message.proto.ProtoContext_CR.NetCRRemoveBlack;

import com.game.message.proto.ProtoContext_CR.NetRCRemoveBlack;

import com.game.message.proto.ProtoContext_CR.NetRCDuadMSGErrorCode;

import com.game.message.proto.ProtoContext_CR.NetRCCheckFriendErrorCode;

import com.game.message.proto.ProtoContext_CR.NetCROpenRealTimeVoice;

import com.game.message.proto.ProtoContext_CR.NetRCOpenRealTimeVoice;

import com.game.message.proto.ProtoContext_CR.NetCRPlayingGamePeople;

import com.game.message.proto.ProtoContext_CR.NetRCPlayingGamePeople;

import com.game.message.proto.ProtoContext_CR.NetCRSettlementThreeState;

import com.game.message.proto.ProtoContext_CR.NetRCSettlementThreeState;

import com.game.message.proto.ProtoContext_CR.NetCRGetCharmRankFirst;

import com.game.message.proto.ProtoContext_CR.NetRCGetCharmRankFirst;

import com.game.message.proto.ProtoContext_CR.NetCRGetCharmRank;

import com.game.message.proto.ProtoContext_CR.NetRCGetCharmRank;

import com.game.message.proto.ProtoContext_CR.NetCRGetConsumeRankFirst;

import com.game.message.proto.ProtoContext_CR.NetRCGetConsumeRankFirst;

import com.game.message.proto.ProtoContext_CR.NetCRGetConsumeRank;

import com.game.message.proto.ProtoContext_CR.NetRCGetConsumeRank;

import com.game.message.proto.ProtoContext_CR.NetRCSendCoin1;

import com.game.message.proto.ProtoContext_CR.NetRCSendNotify;

import com.game.message.proto.ProtoContext_CR.NetCRApplyMPGRoom;

import com.game.message.proto.ProtoContext_CR.NetRCMPGRoom;

import com.game.message.proto.ProtoContext_CR.NetCRJoinMPGRoom;

import com.game.message.proto.ProtoContext_CR.NetCRQuitWaitJoinGame;

import com.game.message.proto.ProtoContext_CR.NetRCQuitWaitJoinGame;

import com.game.message.proto.ProtoContext_CR.NetRCTiPlayer;

import com.game.message.proto.ProtoContext_RD.NetRDRoomJoinDataCenter;

import com.game.message.proto.ProtoContext_RD.NetDRRoomJoinDataCenter;

import com.game.message.proto.ProtoContext_RD.NetDRTryConnRoom;

import com.game.message.proto.ProtoContext_RD.NetRDPlayerJoinRoom;

import com.game.message.proto.ProtoContext_RD.NetDRPlayerJoinRoom;

import com.game.message.proto.ProtoContext_RD.NetRDCheckFriend;

import com.game.message.proto.ProtoContext_RD.NetDRJoinFriend;

import com.game.message.proto.ProtoContext_RD.NetRDSearchPlayerByGame;

import com.game.message.proto.ProtoContext_RD.NetDRSearchPlayerByGame;

import com.game.message.proto.ProtoContext_RD.NetRDSeachLimit;

import com.game.message.proto.ProtoContext_RD.NetDRSeachLimit;

import com.game.message.proto.ProtoContext_RD.NetRDCancelMatch;

import com.game.message.proto.ProtoContext_RD.NetRDPlayerQuitRoom;

import com.game.message.proto.ProtoContext_RD.NetDRPlayerQuitRoom;

import com.game.message.proto.ProtoContext_RD.NetDRUpdatePlayerState;

import com.game.message.proto.ProtoContext_RD.NetDRTempFriend;

import com.game.message.proto.ProtoContext_RD.NetRDBlackOther;

import com.game.message.proto.ProtoContext_RD.NetRDDeleteFriend;

import com.game.message.proto.ProtoContext_RD.NetDRBlackOther;

import com.game.message.proto.ProtoContext_RD.NetDRDeleteFriend;

import com.game.message.proto.ProtoContext_RD.NetRDNotifyBattleResult;

import com.game.message.proto.ProtoContext_RD.NetRDSendGift;

import com.game.message.proto.ProtoContext_RD.NetDRSendGift;

import com.game.message.proto.ProtoContext_RD.NetRDShowPlayerData;

import com.game.message.proto.ProtoContext_RD.NetDRShowPlayerData;

import com.game.message.proto.ProtoContext_RD.NetRDJoinFriend;

import com.game.message.proto.ProtoContext_RD.NetRDRemoveTempFriend;

import com.game.message.proto.ProtoContext_RD.NetRDModifyMyselfData;

import com.game.message.proto.ProtoContext_RD.NetDRModifyMyselfData;

import com.game.message.proto.ProtoContext_RD.NetRDPlayerQuitGame;

import com.game.message.proto.ProtoContext_RD.NetRDRemoveBlack;

import com.game.message.proto.ProtoContext_RD.NetDRRemoveBlack;

import com.game.message.proto.ProtoContext_RD.NetRDSearchPlayerById;

import com.game.message.proto.ProtoContext_RD.NetDRSearchPlayerById;

import com.game.message.proto.ProtoContext_RD.NetRDCheckOpenRealTimeVoice;

import com.game.message.proto.ProtoContext_RD.NetDRCheckOpenRealTimeVoice;

import com.game.message.proto.ProtoContext_RD.NetRDCancelSeachPlayerByLimit;

import com.game.message.proto.ProtoContext_RD.NetDRSendGiftOfflineReceive;

import com.game.message.proto.ProtoContext_RD.NetRDNearyPeopleData;

import com.game.message.proto.ProtoContext_RD.NetDRNearyPeopleData;

import com.game.message.proto.ProtoContext_RD.NetRDInBlack;

import com.game.message.proto.ProtoContext_RD.NetDRInBlack;

import com.game.message.proto.ProtoContext_RD.NetDRSendCoin1;

import com.game.message.proto.ProtoContext_RD.NetDRSendNotify;

import com.game.message.proto.ProtoContext_RD.NetRDQuitWaitJoinGame;

import com.game.message.proto.ProtoContext_RD.NetDRQuitWaitJoinGame;

import com.game.message.proto.ProtoContext_RD.NetRDJoinMPGGame;

import com.game.message.proto.ProtoContext_WEBR.NetWEBRCloseDoorServer;

import com.game.message.proto.ProtoContext_WEBR.NetRWEBCloseDoorServer;

import com.game.message.proto.ProtoContext_WEBR.NetWEBROpenDoorServer;

import com.game.message.proto.ProtoContext_WEBR.NetRWEBOpenDoorServer;

import com.game.message.proto.ProtoContext_WEBR.NetWEBRPlayerServerInfo;

import com.game.message.proto.ProtoContext_WEBR.NetRWEBPlayerServerInfo;

import com.game.message.proto.ProtoContext_WEBR.NetWEBRServerInfo;

import com.game.message.proto.ProtoContext_WEBR.NetRWEBServerInfo;

import com.game.message.proto.ProtoContext_WEBR.NetWEBROpenRecvMSGLog;

import com.game.message.proto.ProtoContext_WEBR.NetRWEBOpenRecvMSGLog;

import com.game.message.proto.ProtoContext_WEBR.NetWEBRCloseRecvMSGLog;

import com.game.message.proto.ProtoContext_WEBR.NetRWEBCloseRecvMSGLog;

import com.game.message.proto.ProtoContext_WEBR.NetWEBRStopServer;

import com.game.message.proto.ProtoContext_WEBR.NetRWEBStopServer;

import com.game.message.proto.ProtoContext_CG.NetCGJoinGame;

import com.game.message.proto.ProtoContext_CG.NetGCJoinGame;

import com.game.message.proto.ProtoContext_CG.NetCGTest;

import com.game.message.proto.ProtoContext_CG.NetCGItem;

import com.game.message.proto.ProtoContext_CG.NetCGThrowItem;

import com.game.message.proto.ProtoContext_CG.NetCGThrowExpression;

import com.game.message.proto.ProtoContext_CG.NetGCThrowItem;

import com.game.message.proto.ProtoContext_CG.NetGCThrowExpression;

import com.game.message.proto.ProtoContext_CG.NetCGLoser;

import com.game.message.proto.ProtoContext_CG.NetCGPeaceRequest;

import com.game.message.proto.ProtoContext_CG.NetGCPeaceNotify;

import com.game.message.proto.ProtoContext_CG.NetCGPeaceResult;

import com.game.message.proto.ProtoContext_CG.NetGCPeaceResult;

import com.game.message.proto.ProtoContext_CG.NetGCAllJoin;

import com.game.message.proto.ProtoContext_CG.NetCGQuitRoom;

import com.game.message.proto.ProtoContext_CG.NetCGAircraftInit;

import com.game.message.proto.ProtoContext_CG.NetGCAircraftInit;

import com.game.message.proto.ProtoContext_CG.NetCGAircraftHitPush;

import com.game.message.proto.ProtoContext_CG.NetGCAircraftHitPush;

import com.game.message.proto.ProtoContext_CG.NetGCGameResult;

import com.game.message.proto.ProtoContext_CG.NetCGGolangPush;

import com.game.message.proto.ProtoContext_CG.NetGCGolangPush;

import com.game.message.proto.ProtoContext_CG.NetGCGolangSuccess;

import com.game.message.proto.ProtoContext_CG.NetCGAnimalChessOpen;

import com.game.message.proto.ProtoContext_CG.NetGCAnimalChessOpen;

import com.game.message.proto.ProtoContext_CG.NetCGAnimalChessMove;

import com.game.message.proto.ProtoContext_CG.NetGCAnimalChessMove;

import com.game.message.proto.ProtoContext_CG.NetGCAnimalChessResultWithDarkPiece;

import com.game.message.proto.ProtoContext_CG.NetGCLinkChessPanel;

import com.game.message.proto.ProtoContext_CG.NetCGLinkChessSelect;

import com.game.message.proto.ProtoContext_CG.NetGCLinkChessSelect;

import com.game.message.proto.ProtoContext_CG.NetCGOthelloPush;

import com.game.message.proto.ProtoContext_CG.NetGCOthelloPush;

import com.game.message.proto.ProtoContext_CG.NetGCOthelloSuccess;

import com.game.message.proto.ProtoContext_CG.NetCGFlappyBirdStart;

import com.game.message.proto.ProtoContext_CG.NetCGFlappyBirdMap;

import com.game.message.proto.ProtoContext_CG.NetGCFlappyBirdMap;

import com.game.message.proto.ProtoContext_CG.NetCGFlappyBirdClick;

import com.game.message.proto.ProtoContext_CG.NetGCFlappyBirdClick;

import com.game.message.proto.ProtoContext_CG.NetCGCoreBallFire;

import com.game.message.proto.ProtoContext_CG.NetGCCoreBallFire;

import com.game.message.proto.ProtoContext_CG.NetGCInitCoreBall;

import com.game.message.proto.ProtoContext_CG.NetCGRemoveBrick;

import com.game.message.proto.ProtoContext_CG.NetGCRemoveBrick;

import com.game.message.proto.ProtoContext_CG.NetCGPopStarClick;

import com.game.message.proto.ProtoContext_CG.NetGCPopStarClick;

import com.game.message.proto.ProtoContext_CG.NetCGFeed;

import com.game.message.proto.ProtoContext_CG.NetGCFeed;

import com.game.message.proto.ProtoContext_CG.NetCGJoinMPGRoom;

import com.game.message.proto.ProtoContext_CG.NetGCJoinMPGRoom;

import com.game.message.proto.ProtoContext_CG.NetCGQuitMPGRoom;

import com.game.message.proto.ProtoContext_CG.NetGCQuitMPGRoom;

import com.game.message.proto.ProtoContext_CG.NetCGMPGRoomMSG;

import com.game.message.proto.ProtoContext_CG.NetGCMPGDuadMSG;

import com.game.message.proto.ProtoContext_CG.NetGCMPGPlayerOffline;

import com.game.message.proto.ProtoContext_CG.NetCGMPGThrowExpression;

import com.game.message.proto.ProtoContext_CG.NetGCMPGThrowExpression;

import com.game.message.proto.ProtoContext_CG.NetGCOtherJoinMPGRoom;

import com.game.message.proto.ProtoContext_CG.NetCGMPGPlayerOffline;

import com.game.message.proto.ProtoContext_CG.NetCGGraffitiReady;

import com.game.message.proto.ProtoContext_CG.NetGCGraffitiReady;

import com.game.message.proto.ProtoContext_CG.NetGCGraffitiCountDown;

import com.game.message.proto.ProtoContext_CG.NetGCGraffitiGameStart;

import com.game.message.proto.ProtoContext_CG.NetGCGraffitiWordsStage;

import com.game.message.proto.ProtoContext_CG.NetGCGraffitiUpdateWord;

import com.game.message.proto.ProtoContext_CG.NetCGGraffitiUpdateWord;

import com.game.message.proto.ProtoContext_CG.NetCGGraffitiSelectWord;

import com.game.message.proto.ProtoContext_CG.NetGCGraffitiSelectWords;

import com.game.message.proto.ProtoContext_CG.NetGCGraffitiNotifyWords;

import com.game.message.proto.ProtoContext_CG.NetCGGraffitiGuessWords;

import com.game.message.proto.ProtoContext_CG.NetGCGraffitiGuessWords;

import com.game.message.proto.ProtoContext_CG.NetGCGraffitiRound;

import com.game.message.proto.ProtoContext_CG.NetCGGraffitiGoodOrBad;

import com.game.message.proto.ProtoContext_CG.NetGCGraffitiGoodOrBad;

import com.game.message.proto.ProtoContext_CG.NetGCGraffitiRankList;

import com.game.message.proto.ProtoContext_CG.NetCGGraffitiPenLine;

import com.game.message.proto.ProtoContext_CG.NetGCGraffitiPenLine;

import com.game.message.proto.ProtoContext_CG.NetCGGraffitiPenUpdate;

import com.game.message.proto.ProtoContext_CG.NetGCGraffitiPenUpdate;

import com.game.message.proto.ProtoContext_CG.NetCGGraffitiLineController;

import com.game.message.proto.ProtoContext_CG.NetGCGraffitiLineController;

import com.game.message.proto.ProtoContext_RG.NetRGNewRoom;

import com.game.message.proto.ProtoContext_RG.NetGRNewRoom;

import com.game.message.proto.ProtoContext_RG.NetGRGameNotifyState;

import com.game.message.proto.ProtoContext_RG.NetGRNewGame;

import com.game.message.proto.ProtoContext_RG.NetRGNewGame;

import com.game.message.proto.ProtoContext_RG.NetGRNotifyBattleResult;

import com.game.message.proto.ProtoContext_RG.NetGRRequestGameData;

import com.game.message.proto.ProtoContext_RG.NetRGCreateGame;

import com.game.message.proto.ProtoContext_RG.NetGRCreateGame;

import com.game.message.proto.ProtoContext_RG.NetGRNotifyQuitGame;

import com.game.message.proto.ProtoContext_RG.NetGRNotifyQuitMPGGame;

import com.game.message.proto.ProtoContext_BASIC.NetKeepLivePing;

import com.game.message.proto.ProtoContext_BASIC.NetKeepLivePong;

import com.game.message.proto.ProtoContext_BASIC.NetReConn;

import com.game.message.proto.ProtoContext_BASIC.NetReConnResult;

import com.game.message.proto.ProtoContext_BASIC.NetReConnResultStubTest;

import com.game.message.proto.ProtoContext_BASIC.NetCheckTime;

import com.game.message.proto.ProtoContext_BASIC.NetServerStoping;

import com.game.message.proto.ProtoContext_BASIC.NetGraffitiPoint;

import com.game.message.proto.ProtoContext_BASIC.NetRobotConnTest;

import com.game.message.proto.ProtoContext_WEBIG.NetWEBIGTest;

import com.game.message.proto.ProtoContext_WEBIG.NetIGWEBTest;

import com.game.message.proto.ProtoContext_WEBIG.NetWEBIGPlayerServerInfo;

import com.game.message.proto.ProtoContext_WEBIG.NetIGWEBPlayerServerInfo;

import com.game.message.proto.ProtoContext_WEBIG.NetWEBIGGameScene;

import com.game.message.proto.ProtoContext_WEBIG.NetIGWEBGameScene;

import com.game.message.proto.ProtoContext_WEBIG.NetWEBIGGameSceneGlobalData;

import com.game.message.proto.ProtoContext_WEBIG.NetIGWEBGameSceneGlobalData;

import com.game.message.proto.ProtoContext_WEBIG.NetWEBIGServerInfo;

import com.game.message.proto.ProtoContext_WEBIG.NetIGWEBServerInfo;

import com.game.message.proto.ProtoContext_WEBIG.NetWEBIGTiPlayer;

import com.game.message.proto.ProtoContext_WEBIG.NetIGWEBTiPlayer;

import com.game.message.proto.ProtoContext_RIG.NetRIGRoomJoinGate;

import com.game.message.proto.ProtoContext_RIG.NetIGRRoomJoinGate;

import com.game.message.proto.ProtoContext_RIG.NetIGRTryConnRoom;

import com.game.message.proto.ProtoContext_RIG.NetRIGPlayerJoinRoom;

import com.game.message.proto.ProtoContext_RIG.NetIGRPlayerJoinRoom;

import com.game.message.proto.ProtoContext_RIG.NetRIGPlayerQuitRoom;

import com.game.message.proto.ProtoContext_RIG.NetIGRPlayerQuitRoom;

import com.game.message.proto.ProtoContext_RIG.NetRIGSendDuadMSG;

import com.game.message.proto.ProtoContext_RIG.NetIGRSendDuadMSG;

import com.game.message.proto.ProtoContext_RIG.NetIGRSendDuadMSGFail;

import com.game.message.proto.ProtoContext_RIG.NetRIGCreateGame;

import com.game.message.proto.ProtoContext_RIG.NetIGRCreateGame;

import com.game.message.proto.ProtoContext_RIG.NetRIGPlayerChatMSG;

import com.game.message.proto.ProtoContext_RIG.NetIGRPlayerChatMSG;

import com.game.message.proto.ProtoContext_RIG.NetRIGGetPlayerState;

import com.game.message.proto.ProtoContext_RIG.NetIGRGetPlayerState;

import com.game.message.proto.ProtoContext_RIG.NetRIGPlayerQuitGame;

import com.game.message.proto.ProtoContext_RIG.NetRIGApplyMPGRoom;

import com.game.message.proto.ProtoContext_RIG.NetIGRApplyMPGRoom;

import com.game.message.proto.ProtoContext_RIG.NetRIGNewMPGRoom;

import com.game.message.proto.ProtoContext_RIG.NetIGRNewMPGRoom;

import com.game.message.proto.ProtoContext_RIG.NetRIGGameRequestMPGRoom;

import com.game.message.proto.ProtoContext_RIG.NetIGRTiPlayer;


public final class MessageWriteUtil {
	private MessageWriteUtil() {	
	}


	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBDSendCoin1 msg ) {
		ioClient.writeAndFlush( 
				NetWEBDSendCoin1.ID.CODE1_VALUE, 
				NetWEBDSendCoin1.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBDSendCoin1 msg ) {
		ioClient.writeAndFlush( 
				NetWEBDSendCoin1.ID.CODE1_VALUE, 
				NetWEBDSendCoin1.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetDWEBSendCoin1 msg ) {
		ioClient.writeAndFlush( 
				NetDWEBSendCoin1.ID.CODE1_VALUE, 
				NetDWEBSendCoin1.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetDWEBSendCoin1 msg ) {
		ioClient.writeAndFlush( 
				NetDWEBSendCoin1.ID.CODE1_VALUE, 
				NetDWEBSendCoin1.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBDSendNotify msg ) {
		ioClient.writeAndFlush( 
				NetWEBDSendNotify.ID.CODE1_VALUE, 
				NetWEBDSendNotify.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBDSendNotify msg ) {
		ioClient.writeAndFlush( 
				NetWEBDSendNotify.ID.CODE1_VALUE, 
				NetWEBDSendNotify.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBDPlayerServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetWEBDPlayerServerInfo.ID.CODE1_VALUE, 
				NetWEBDPlayerServerInfo.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBDPlayerServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetWEBDPlayerServerInfo.ID.CODE1_VALUE, 
				NetWEBDPlayerServerInfo.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetDWEBPlayerServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetDWEBPlayerServerInfo.ID.CODE1_VALUE, 
				NetDWEBPlayerServerInfo.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetDWEBPlayerServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetDWEBPlayerServerInfo.ID.CODE1_VALUE, 
				NetDWEBPlayerServerInfo.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBDServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetWEBDServerInfo.ID.CODE1_VALUE, 
				NetWEBDServerInfo.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBDServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetWEBDServerInfo.ID.CODE1_VALUE, 
				NetWEBDServerInfo.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetDWEBServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetDWEBServerInfo.ID.CODE1_VALUE, 
				NetDWEBServerInfo.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetDWEBServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetDWEBServerInfo.ID.CODE1_VALUE, 
				NetDWEBServerInfo.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGAIGGameJoinGate msg ) {
		ioClient.writeAndFlush(
				NetGAIGGameJoinGate.ID.CODE1_VALUE, 
				NetGAIGGameJoinGate.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGAIGGameJoinGate msg ) {
		ioClient.writeAndFlush( 
				NetGAIGGameJoinGate.ID.CODE1_VALUE, 
				NetGAIGGameJoinGate.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetIGGAGameJoinGate msg ) {
		ioClient.writeAndFlush( 
				NetIGGAGameJoinGate.ID.CODE1_VALUE, 
				NetIGGAGameJoinGate.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetIGGAGameJoinGate msg ) {
		ioClient.writeAndFlush( 
				NetIGGAGameJoinGate.ID.CODE1_VALUE, 
				NetIGGAGameJoinGate.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetIGGATryConnGame msg ) {
		ioClient.writeAndFlush( 
				NetIGGATryConnGame.ID.CODE1_VALUE, 
				NetIGGATryConnGame.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetIGGATryConnGame msg ) {
		ioClient.writeAndFlush( 
				NetIGGATryConnGame.ID.CODE1_VALUE, 
				NetIGGATryConnGame.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetIGGACreateGame msg ) {
		ioClient.writeAndFlush( 
				NetIGGACreateGame.ID.CODE1_VALUE, 
				NetIGGACreateGame.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetIGGACreateGame msg ) {
		ioClient.writeAndFlush( 
				NetIGGACreateGame.ID.CODE1_VALUE, 
				NetIGGACreateGame.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGAIGCreateGame msg ) {
		ioClient.writeAndFlush( 
				NetGAIGCreateGame.ID.CODE1_VALUE, 
				NetGAIGCreateGame.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGAIGCreateGame msg ) {
		ioClient.writeAndFlush( 
				NetGAIGCreateGame.ID.CODE1_VALUE, 
				NetGAIGCreateGame.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetIGGAActivationMPRRoom msg ) {
		ioClient.writeAndFlush( 
				NetIGGAActivationMPRRoom.ID.CODE1_VALUE, 
				NetIGGAActivationMPRRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetIGGAActivationMPRRoom msg ) {
		ioClient.writeAndFlush( 
				NetIGGAActivationMPRRoom.ID.CODE1_VALUE, 
				NetIGGAActivationMPRRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGAIGSyncMPRData msg ) {
		ioClient.writeAndFlush( 
				NetGAIGSyncMPRData.ID.CODE1_VALUE, 
				NetGAIGSyncMPRData.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGAIGSyncMPRData msg ) {
		ioClient.writeAndFlush( 
				NetGAIGSyncMPRData.ID.CODE1_VALUE, 
				NetGAIGSyncMPRData.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGAIGRegMPRRoom msg ) {
		ioClient.writeAndFlush( 
				NetGAIGRegMPRRoom.ID.CODE1_VALUE, 
				NetGAIGRegMPRRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGAIGRegMPRRoom msg ) {
		ioClient.writeAndFlush( 
				NetGAIGRegMPRRoom.ID.CODE1_VALUE, 
				NetGAIGRegMPRRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGAIGActivationMPRRoom msg ) {
		ioClient.writeAndFlush( 
				NetGAIGActivationMPRRoom.ID.CODE1_VALUE, 
				NetGAIGActivationMPRRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGAIGActivationMPRRoom msg ) {
		ioClient.writeAndFlush( 
				NetGAIGActivationMPRRoom.ID.CODE1_VALUE, 
				NetGAIGActivationMPRRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetIGGASyncMPRData msg ) {
		ioClient.writeAndFlush( 
				NetIGGASyncMPRData.ID.CODE1_VALUE, 
				NetIGGASyncMPRData.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetIGGASyncMPRData msg ) {
		ioClient.writeAndFlush( 
				NetIGGASyncMPRData.ID.CODE1_VALUE, 
				NetIGGASyncMPRData.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetIGGATiPlayer msg ) {
		ioClient.writeAndFlush( 
				NetIGGATiPlayer.ID.CODE1_VALUE, 
				NetIGGATiPlayer.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetIGGATiPlayer msg ) {
		ioClient.writeAndFlush( 
				NetIGGATiPlayer.ID.CODE1_VALUE, 
				NetIGGATiPlayer.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGAIGClearGameSceneData msg ) {
		ioClient.writeAndFlush( 
				NetGAIGClearGameSceneData.ID.CODE1_VALUE, 
				NetGAIGClearGameSceneData.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGAIGClearGameSceneData msg ) {
		ioClient.writeAndFlush( 
				NetGAIGClearGameSceneData.ID.CODE1_VALUE, 
				NetGAIGClearGameSceneData.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetIGGAClearGameSceneData msg ) {
		ioClient.writeAndFlush( 
				NetIGGAClearGameSceneData.ID.CODE1_VALUE, 
				NetIGGAClearGameSceneData.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetIGGAClearGameSceneData msg ) {
		ioClient.writeAndFlush( 
				NetIGGAClearGameSceneData.ID.CODE1_VALUE, 
				NetIGGAClearGameSceneData.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBGCloseDoorServer msg ) {
		ioClient.writeAndFlush( 
				NetWEBGCloseDoorServer.ID.CODE1_VALUE, 
				NetWEBGCloseDoorServer.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBGCloseDoorServer msg ) {
		ioClient.writeAndFlush( 
				NetWEBGCloseDoorServer.ID.CODE1_VALUE, 
				NetWEBGCloseDoorServer.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGWEBCloseDoorServer msg ) {
		ioClient.writeAndFlush( 
				NetGWEBCloseDoorServer.ID.CODE1_VALUE, 
				NetGWEBCloseDoorServer.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGWEBCloseDoorServer msg ) {
		ioClient.writeAndFlush( 
				NetGWEBCloseDoorServer.ID.CODE1_VALUE, 
				NetGWEBCloseDoorServer.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBGOpenDoorServer msg ) {
		ioClient.writeAndFlush( 
				NetWEBGOpenDoorServer.ID.CODE1_VALUE, 
				NetWEBGOpenDoorServer.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBGOpenDoorServer msg ) {
		ioClient.writeAndFlush( 
				NetWEBGOpenDoorServer.ID.CODE1_VALUE, 
				NetWEBGOpenDoorServer.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGWEBOpenDoorServer msg ) {
		ioClient.writeAndFlush( 
				NetGWEBOpenDoorServer.ID.CODE1_VALUE, 
				NetGWEBOpenDoorServer.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGWEBOpenDoorServer msg ) {
		ioClient.writeAndFlush( 
				NetGWEBOpenDoorServer.ID.CODE1_VALUE, 
				NetGWEBOpenDoorServer.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBGPlayerServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetWEBGPlayerServerInfo.ID.CODE1_VALUE, 
				NetWEBGPlayerServerInfo.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBGPlayerServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetWEBGPlayerServerInfo.ID.CODE1_VALUE, 
				NetWEBGPlayerServerInfo.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGWEBPlayerServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetGWEBPlayerServerInfo.ID.CODE1_VALUE, 
				NetGWEBPlayerServerInfo.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGWEBPlayerServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetGWEBPlayerServerInfo.ID.CODE1_VALUE, 
				NetGWEBPlayerServerInfo.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBGServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetWEBGServerInfo.ID.CODE1_VALUE, 
				NetWEBGServerInfo.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBGServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetWEBGServerInfo.ID.CODE1_VALUE, 
				NetWEBGServerInfo.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGWEBServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetGWEBServerInfo.ID.CODE1_VALUE, 
				NetGWEBServerInfo.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGWEBServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetGWEBServerInfo.ID.CODE1_VALUE, 
				NetGWEBServerInfo.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBGOpenRecvMSGLog msg ) {
		ioClient.writeAndFlush( 
				NetWEBGOpenRecvMSGLog.ID.CODE1_VALUE, 
				NetWEBGOpenRecvMSGLog.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBGOpenRecvMSGLog msg ) {
		ioClient.writeAndFlush( 
				NetWEBGOpenRecvMSGLog.ID.CODE1_VALUE, 
				NetWEBGOpenRecvMSGLog.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGWEBOpenRecvMSGLog msg ) {
		ioClient.writeAndFlush( 
				NetGWEBOpenRecvMSGLog.ID.CODE1_VALUE, 
				NetGWEBOpenRecvMSGLog.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGWEBOpenRecvMSGLog msg ) {
		ioClient.writeAndFlush( 
				NetGWEBOpenRecvMSGLog.ID.CODE1_VALUE, 
				NetGWEBOpenRecvMSGLog.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBGCloseRecvMSGLog msg ) {
		ioClient.writeAndFlush( 
				NetWEBGCloseRecvMSGLog.ID.CODE1_VALUE, 
				NetWEBGCloseRecvMSGLog.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBGCloseRecvMSGLog msg ) {
		ioClient.writeAndFlush( 
				NetWEBGCloseRecvMSGLog.ID.CODE1_VALUE, 
				NetWEBGCloseRecvMSGLog.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGWEBCloseRecvMSGLog msg ) {
		ioClient.writeAndFlush( 
				NetGWEBCloseRecvMSGLog.ID.CODE1_VALUE, 
				NetGWEBCloseRecvMSGLog.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGWEBCloseRecvMSGLog msg ) {
		ioClient.writeAndFlush( 
				NetGWEBCloseRecvMSGLog.ID.CODE1_VALUE, 
				NetGWEBCloseRecvMSGLog.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBGGameScene msg ) {
		ioClient.writeAndFlush( 
				NetWEBGGameScene.ID.CODE1_VALUE, 
				NetWEBGGameScene.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBGGameScene msg ) {
		ioClient.writeAndFlush( 
				NetWEBGGameScene.ID.CODE1_VALUE, 
				NetWEBGGameScene.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGWEBGameScene msg ) {
		ioClient.writeAndFlush( 
				NetGWEBGameScene.ID.CODE1_VALUE, 
				NetGWEBGameScene.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGWEBGameScene msg ) {
		ioClient.writeAndFlush( 
				NetGWEBGameScene.ID.CODE1_VALUE, 
				NetGWEBGameScene.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBGGameSceneGlobalData msg ) {
		ioClient.writeAndFlush( 
				NetWEBGGameSceneGlobalData.ID.CODE1_VALUE, 
				NetWEBGGameSceneGlobalData.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBGGameSceneGlobalData msg ) {
		ioClient.writeAndFlush( 
				NetWEBGGameSceneGlobalData.ID.CODE1_VALUE, 
				NetWEBGGameSceneGlobalData.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGWEBGameSceneGlobalData msg ) {
		ioClient.writeAndFlush( 
				NetGWEBGameSceneGlobalData.ID.CODE1_VALUE, 
				NetGWEBGameSceneGlobalData.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGWEBGameSceneGlobalData msg ) {
		ioClient.writeAndFlush( 
				NetGWEBGameSceneGlobalData.ID.CODE1_VALUE, 
				NetGWEBGameSceneGlobalData.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBGDoublePlayerRoomInfo msg ) {
		ioClient.writeAndFlush( 
				NetWEBGDoublePlayerRoomInfo.ID.CODE1_VALUE, 
				NetWEBGDoublePlayerRoomInfo.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBGDoublePlayerRoomInfo msg ) {
		ioClient.writeAndFlush( 
				NetWEBGDoublePlayerRoomInfo.ID.CODE1_VALUE, 
				NetWEBGDoublePlayerRoomInfo.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGWEBDoublePlayerRoomInfo msg ) {
		ioClient.writeAndFlush(
				NetGWEBDoublePlayerRoomInfo.ID.CODE1_VALUE, 
				NetGWEBDoublePlayerRoomInfo.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGWEBDoublePlayerRoomInfo msg ) {
		ioClient.writeAndFlush( 
				NetGWEBDoublePlayerRoomInfo.ID.CODE1_VALUE, 
				NetGWEBDoublePlayerRoomInfo.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBGStopServer msg ) {
		ioClient.writeAndFlush( 
				NetWEBGStopServer.ID.CODE1_VALUE, 
				NetWEBGStopServer.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBGStopServer msg ) {
		ioClient.writeAndFlush( 
				NetWEBGStopServer.ID.CODE1_VALUE, 
				NetWEBGStopServer.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGWEBStopServer msg ) {
		ioClient.writeAndFlush( 
				NetGWEBStopServer.ID.CODE1_VALUE, 
				NetGWEBStopServer.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGWEBStopServer msg ) {
		ioClient.writeAndFlush( 
				NetGWEBStopServer.ID.CODE1_VALUE, 
				NetGWEBStopServer.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRJoinRoom msg ) {
		ioClient.writeAndFlush( 
				NetCRJoinRoom.ID.CODE1_VALUE, 
				NetCRJoinRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRJoinRoom msg ) {
		ioClient.writeAndFlush( 
				NetCRJoinRoom.ID.CODE1_VALUE, 
				NetCRJoinRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCJoinRoom msg ) {
		ioClient.writeAndFlush( 
				NetRCJoinRoom.ID.CODE1_VALUE, 
				NetRCJoinRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCJoinRoom msg ) {
		ioClient.writeAndFlush( 
				NetRCJoinRoom.ID.CODE1_VALUE, 
				NetRCJoinRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCGetAllFriend msg ) {
		ioClient.writeAndFlush( 
				NetRCGetAllFriend.ID.CODE1_VALUE, 
				NetRCGetAllFriend.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCGetAllFriend msg ) {
		ioClient.writeAndFlush( 
				NetRCGetAllFriend.ID.CODE1_VALUE, 
				NetRCGetAllFriend.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCNewFriend msg ) {
		ioClient.writeAndFlush( 
				NetRCNewFriend.ID.CODE1_VALUE, 
				NetRCNewFriend.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCNewFriend msg ) {
		ioClient.writeAndFlush( 
				NetRCNewFriend.ID.CODE1_VALUE, 
				NetRCNewFriend.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCStateNotify msg ) {
		ioClient.writeAndFlush( 
				NetRCStateNotify.ID.CODE1_VALUE, 
				NetRCStateNotify.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCStateNotify msg ) {
		ioClient.writeAndFlush( 
				NetRCStateNotify.ID.CODE1_VALUE, 
				NetRCStateNotify.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRDuadMSG msg ) {
		ioClient.writeAndFlush( 
				NetCRDuadMSG.ID.CODE1_VALUE, 
				NetCRDuadMSG.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRDuadMSG msg ) {
		ioClient.writeAndFlush( 
				NetCRDuadMSG.ID.CODE1_VALUE, 
				NetCRDuadMSG.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCDuadMSG msg ) {
		ioClient.writeAndFlush( 
				NetRCDuadMSG.ID.CODE1_VALUE, 
				NetRCDuadMSG.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCDuadMSG msg ) {
		ioClient.writeAndFlush( 
				NetRCDuadMSG.ID.CODE1_VALUE, 
				NetRCDuadMSG.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRJoinFriend msg ) {
		ioClient.writeAndFlush( 
				NetCRJoinFriend.ID.CODE1_VALUE, 
				NetCRJoinFriend.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRJoinFriend msg ) {
		ioClient.writeAndFlush( 
				NetCRJoinFriend.ID.CODE1_VALUE, 
				NetCRJoinFriend.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCJoinFriend msg ) {
		ioClient.writeAndFlush( 
				NetRCJoinFriend.ID.CODE1_VALUE, 
				NetRCJoinFriend.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCJoinFriend msg ) {
		ioClient.writeAndFlush( 
				NetRCJoinFriend.ID.CODE1_VALUE, 
				NetRCJoinFriend.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCNewGame msg ) {
		ioClient.writeAndFlush( 
				NetRCNewGame.ID.CODE1_VALUE, 
				NetRCNewGame.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCNewGame msg ) {
		ioClient.writeAndFlush( 
				NetRCNewGame.ID.CODE1_VALUE, 
				NetRCNewGame.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRMatchPlayer msg ) {
		ioClient.writeAndFlush( 
				NetCRMatchPlayer.ID.CODE1_VALUE, 
				NetCRMatchPlayer.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRMatchPlayer msg ) {
		ioClient.writeAndFlush( 
				NetCRMatchPlayer.ID.CODE1_VALUE, 
				NetCRMatchPlayer.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCMatchPlayer msg ) {
		ioClient.writeAndFlush( 
				NetRCMatchPlayer.ID.CODE1_VALUE, 
				NetRCMatchPlayer.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCMatchPlayer msg ) {
		ioClient.writeAndFlush( 
				NetRCMatchPlayer.ID.CODE1_VALUE, 
				NetRCMatchPlayer.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRBlackOther msg ) {
		ioClient.writeAndFlush( 
				NetCRBlackOther.ID.CODE1_VALUE, 
				NetCRBlackOther.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRBlackOther msg ) {
		ioClient.writeAndFlush( 
				NetCRBlackOther.ID.CODE1_VALUE, 
				NetCRBlackOther.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCBlackOther msg ) {
		ioClient.writeAndFlush( 
				NetRCBlackOther.ID.CODE1_VALUE, 
				NetRCBlackOther.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCBlackOther msg ) {
		ioClient.writeAndFlush( 
				NetRCBlackOther.ID.CODE1_VALUE, 
				NetRCBlackOther.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCGetAllBlack msg ) {
		ioClient.writeAndFlush( 
				NetRCGetAllBlack.ID.CODE1_VALUE, 
				NetRCGetAllBlack.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCGetAllBlack msg ) {
		ioClient.writeAndFlush( 
				NetRCGetAllBlack.ID.CODE1_VALUE, 
				NetRCGetAllBlack.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRGetAllFriend msg ) {
		ioClient.writeAndFlush( 
				NetCRGetAllFriend.ID.CODE1_VALUE, 
				NetCRGetAllFriend.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRGetAllFriend msg ) {
		ioClient.writeAndFlush( 
				NetCRGetAllFriend.ID.CODE1_VALUE, 
				NetCRGetAllFriend.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRGetAllBlack msg ) {
		ioClient.writeAndFlush( 
				NetCRGetAllBlack.ID.CODE1_VALUE, 
				NetCRGetAllBlack.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRGetAllBlack msg ) {
		ioClient.writeAndFlush( 
				NetCRGetAllBlack.ID.CODE1_VALUE, 
				NetCRGetAllBlack.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRDeleteFriend msg ) {
		ioClient.writeAndFlush( 
				NetCRDeleteFriend.ID.CODE1_VALUE, 
				NetCRDeleteFriend.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRDeleteFriend msg ) {
		ioClient.writeAndFlush( 
				NetCRDeleteFriend.ID.CODE1_VALUE, 
				NetCRDeleteFriend.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCDeleteFriend msg ) {
		ioClient.writeAndFlush( 
				NetRCDeleteFriend.ID.CODE1_VALUE, 
				NetRCDeleteFriend.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCDeleteFriend msg ) {
		ioClient.writeAndFlush( 
				NetRCDeleteFriend.ID.CODE1_VALUE, 
				NetRCDeleteFriend.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCCheckFriend msg ) {
		ioClient.writeAndFlush( 
				NetRCCheckFriend.ID.CODE1_VALUE, 
				NetRCCheckFriend.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCCheckFriend msg ) {
		ioClient.writeAndFlush( 
				NetRCCheckFriend.ID.CODE1_VALUE, 
				NetRCCheckFriend.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRCheckFriend msg ) {
		ioClient.writeAndFlush( 
				NetCRCheckFriend.ID.CODE1_VALUE, 
				NetCRCheckFriend.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRCheckFriend msg ) {
		ioClient.writeAndFlush( 
				NetCRCheckFriend.ID.CODE1_VALUE, 
				NetCRCheckFriend.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRSeachPlayerByLimit msg ) {
		ioClient.writeAndFlush( 
				NetCRSeachPlayerByLimit.ID.CODE1_VALUE, 
				NetCRSeachPlayerByLimit.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRSeachPlayerByLimit msg ) {
		ioClient.writeAndFlush( 
				NetCRSeachPlayerByLimit.ID.CODE1_VALUE, 
				NetCRSeachPlayerByLimit.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRSeachPlayerById msg ) {
		ioClient.writeAndFlush( 
				NetCRSeachPlayerById.ID.CODE1_VALUE, 
				NetCRSeachPlayerById.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRSeachPlayerById msg ) {
		ioClient.writeAndFlush( 
				NetCRSeachPlayerById.ID.CODE1_VALUE, 
				NetCRSeachPlayerById.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCSeachPlayerById msg ) {
		ioClient.writeAndFlush( 
				NetRCSeachPlayerById.ID.CODE1_VALUE, 
				NetRCSeachPlayerById.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCSeachPlayerById msg ) {
		ioClient.writeAndFlush( 
				NetRCSeachPlayerById.ID.CODE1_VALUE, 
				NetRCSeachPlayerById.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRCheckFriendList msg ) {
		ioClient.writeAndFlush( 
				NetCRCheckFriendList.ID.CODE1_VALUE, 
				NetCRCheckFriendList.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRCheckFriendList msg ) {
		ioClient.writeAndFlush( 
				NetCRCheckFriendList.ID.CODE1_VALUE, 
				NetCRCheckFriendList.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCCheckFriendList msg ) {
		ioClient.writeAndFlush( 
				NetRCCheckFriendList.ID.CODE1_VALUE, 
				NetRCCheckFriendList.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCCheckFriendList msg ) {
		ioClient.writeAndFlush( 
				NetRCCheckFriendList.ID.CODE1_VALUE, 
				NetRCCheckFriendList.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCSeachPlayerByLimit msg ) {
		ioClient.writeAndFlush( 
				NetRCSeachPlayerByLimit.ID.CODE1_VALUE, 
				NetRCSeachPlayerByLimit.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCSeachPlayerByLimit msg ) {
		ioClient.writeAndFlush( 
				NetRCSeachPlayerByLimit.ID.CODE1_VALUE, 
				NetRCSeachPlayerByLimit.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRCancelMatchPlayer msg ) {
		ioClient.writeAndFlush( 
				NetCRCancelMatchPlayer.ID.CODE1_VALUE, 
				NetCRCancelMatchPlayer.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRCancelMatchPlayer msg ) {
		ioClient.writeAndFlush( 
				NetCRCancelMatchPlayer.ID.CODE1_VALUE, 
				NetCRCancelMatchPlayer.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCCancelMatchPlayer msg ) {
		ioClient.writeAndFlush( 
				NetRCCancelMatchPlayer.ID.CODE1_VALUE, 
				NetRCCancelMatchPlayer.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCCancelMatchPlayer msg ) {
		ioClient.writeAndFlush( 
				NetRCCancelMatchPlayer.ID.CODE1_VALUE, 
				NetRCCancelMatchPlayer.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRShowPlayerData msg ) {
		ioClient.writeAndFlush( 
				NetCRShowPlayerData.ID.CODE1_VALUE, 
				NetCRShowPlayerData.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRShowPlayerData msg ) {
		ioClient.writeAndFlush( 
				NetCRShowPlayerData.ID.CODE1_VALUE, 
				NetCRShowPlayerData.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCShowPlayerData msg ) {
		ioClient.writeAndFlush( 
				NetRCShowPlayerData.ID.CODE1_VALUE, 
				NetRCShowPlayerData.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCShowPlayerData msg ) {
		ioClient.writeAndFlush( 
				NetRCShowPlayerData.ID.CODE1_VALUE, 
				NetRCShowPlayerData.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRBattleMatchLogList msg ) {
		ioClient.writeAndFlush( 
				NetCRBattleMatchLogList.ID.CODE1_VALUE, 
				NetCRBattleMatchLogList.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRBattleMatchLogList msg ) {
		ioClient.writeAndFlush( 
				NetCRBattleMatchLogList.ID.CODE1_VALUE, 
				NetCRBattleMatchLogList.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCBattleMatchLogList msg ) {
		ioClient.writeAndFlush( 
				NetRCBattleMatchLogList.ID.CODE1_VALUE, 
				NetRCBattleMatchLogList.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCBattleMatchLogList msg ) {
		ioClient.writeAndFlush( 
				NetRCBattleMatchLogList.ID.CODE1_VALUE, 
				NetRCBattleMatchLogList.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRGetNearbyPeople msg ) {
		ioClient.writeAndFlush( 
				NetCRGetNearbyPeople.ID.CODE1_VALUE, 
				NetCRGetNearbyPeople.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRGetNearbyPeople msg ) {
		ioClient.writeAndFlush( 
				NetCRGetNearbyPeople.ID.CODE1_VALUE, 
				NetCRGetNearbyPeople.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRUpdateNearby msg ) {
		ioClient.writeAndFlush( 
				NetCRUpdateNearby.ID.CODE1_VALUE, 
				NetCRUpdateNearby.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRUpdateNearby msg ) {
		ioClient.writeAndFlush( 
				NetCRUpdateNearby.ID.CODE1_VALUE, 
				NetCRUpdateNearby.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCGetNearbyPeople msg ) {
		ioClient.writeAndFlush( 
				NetRCGetNearbyPeople.ID.CODE1_VALUE, 
				NetRCGetNearbyPeople.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCGetNearbyPeople msg ) {
		ioClient.writeAndFlush( 
				NetRCGetNearbyPeople.ID.CODE1_VALUE, 
				NetRCGetNearbyPeople.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRSendGift msg ) {
		ioClient.writeAndFlush( 
				NetCRSendGift.ID.CODE1_VALUE, 
				NetCRSendGift.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRSendGift msg ) {
		ioClient.writeAndFlush( 
				NetCRSendGift.ID.CODE1_VALUE, 
				NetCRSendGift.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCSendGift msg ) {
		ioClient.writeAndFlush( 
				NetRCSendGift.ID.CODE1_VALUE, 
				NetRCSendGift.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCSendGift msg ) {
		ioClient.writeAndFlush( 
				NetRCSendGift.ID.CODE1_VALUE, 
				NetRCSendGift.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRGetRankFirst msg ) {
		ioClient.writeAndFlush( 
				NetCRGetRankFirst.ID.CODE1_VALUE, 
				NetCRGetRankFirst.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRGetRankFirst msg ) {
		ioClient.writeAndFlush( 
				NetCRGetRankFirst.ID.CODE1_VALUE, 
				NetCRGetRankFirst.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCGetRankFirst msg ) {
		ioClient.writeAndFlush( 
				NetRCGetRankFirst.ID.CODE1_VALUE, 
				NetRCGetRankFirst.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCGetRankFirst msg ) {
		ioClient.writeAndFlush( 
				NetRCGetRankFirst.ID.CODE1_VALUE, 
				NetRCGetRankFirst.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRGetRank msg ) {
		ioClient.writeAndFlush( 
				NetCRGetRank.ID.CODE1_VALUE, 
				NetCRGetRank.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRGetRank msg ) {
		ioClient.writeAndFlush( 
				NetCRGetRank.ID.CODE1_VALUE, 
				NetCRGetRank.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCGetRank msg ) {
		ioClient.writeAndFlush( 
				NetRCGetRank.ID.CODE1_VALUE, 
				NetRCGetRank.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCGetRank msg ) {
		ioClient.writeAndFlush( 
				NetRCGetRank.ID.CODE1_VALUE, 
				NetRCGetRank.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRSeachPlayerByLimitLog msg ) {
		ioClient.writeAndFlush( 
				NetCRSeachPlayerByLimitLog.ID.CODE1_VALUE, 
				NetCRSeachPlayerByLimitLog.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRSeachPlayerByLimitLog msg ) {
		ioClient.writeAndFlush( 
				NetCRSeachPlayerByLimitLog.ID.CODE1_VALUE, 
				NetCRSeachPlayerByLimitLog.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCSeachPlayerByLimitLog msg ) {
		ioClient.writeAndFlush( 
				NetRCSeachPlayerByLimitLog.ID.CODE1_VALUE, 
				NetRCSeachPlayerByLimitLog.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCSeachPlayerByLimitLog msg ) {
		ioClient.writeAndFlush( 
				NetRCSeachPlayerByLimitLog.ID.CODE1_VALUE, 
				NetRCSeachPlayerByLimitLog.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRCancelSeachPlayerByLimit msg ) {
		ioClient.writeAndFlush( 
				NetCRCancelSeachPlayerByLimit.ID.CODE1_VALUE, 
				NetCRCancelSeachPlayerByLimit.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRCancelSeachPlayerByLimit msg ) {
		ioClient.writeAndFlush( 
				NetCRCancelSeachPlayerByLimit.ID.CODE1_VALUE, 
				NetCRCancelSeachPlayerByLimit.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCCancelSeachPlayerByLimit msg ) {
		ioClient.writeAndFlush( 
				NetRCCancelSeachPlayerByLimit.ID.CODE1_VALUE, 
				NetRCCancelSeachPlayerByLimit.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCCancelSeachPlayerByLimit msg ) {
		ioClient.writeAndFlush( 
				NetRCCancelSeachPlayerByLimit.ID.CODE1_VALUE, 
				NetRCCancelSeachPlayerByLimit.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCROftenPlayGame msg ) {
		ioClient.writeAndFlush( 
				NetCROftenPlayGame.ID.CODE1_VALUE, 
				NetCROftenPlayGame.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCROftenPlayGame msg ) {
		ioClient.writeAndFlush( 
				NetCROftenPlayGame.ID.CODE1_VALUE, 
				NetCROftenPlayGame.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCOftenPlayGame msg ) {
		ioClient.writeAndFlush( 
				NetRCOftenPlayGame.ID.CODE1_VALUE, 
				NetRCOftenPlayGame.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCOftenPlayGame msg ) {
		ioClient.writeAndFlush( 
				NetRCOftenPlayGame.ID.CODE1_VALUE, 
				NetRCOftenPlayGame.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRPhotosBackGround msg ) {
		ioClient.writeAndFlush( 
				NetCRPhotosBackGround.ID.CODE1_VALUE, 
				NetCRPhotosBackGround.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRPhotosBackGround msg ) {
		ioClient.writeAndFlush( 
				NetCRPhotosBackGround.ID.CODE1_VALUE, 
				NetCRPhotosBackGround.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCPhotosBackGround msg ) {
		ioClient.writeAndFlush( 
				NetRCPhotosBackGround.ID.CODE1_VALUE, 
				NetRCPhotosBackGround.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCPhotosBackGround msg ) {
		ioClient.writeAndFlush( 
				NetRCPhotosBackGround.ID.CODE1_VALUE, 
				NetRCPhotosBackGround.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRModifyMyselfData msg ) {
		ioClient.writeAndFlush( 
				NetCRModifyMyselfData.ID.CODE1_VALUE, 
				NetCRModifyMyselfData.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRModifyMyselfData msg ) {
		ioClient.writeAndFlush( 
				NetCRModifyMyselfData.ID.CODE1_VALUE, 
				NetCRModifyMyselfData.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCModifyMyselfData msg ) {
		ioClient.writeAndFlush( 
				NetRCModifyMyselfData.ID.CODE1_VALUE, 
				NetRCModifyMyselfData.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCModifyMyselfData msg ) {
		ioClient.writeAndFlush( 
				NetRCModifyMyselfData.ID.CODE1_VALUE, 
				NetRCModifyMyselfData.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRRemoveBlack msg ) {
		ioClient.writeAndFlush( 
				NetCRRemoveBlack.ID.CODE1_VALUE, 
				NetCRRemoveBlack.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRRemoveBlack msg ) {
		ioClient.writeAndFlush( 
				NetCRRemoveBlack.ID.CODE1_VALUE, 
				NetCRRemoveBlack.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCRemoveBlack msg ) {
		ioClient.writeAndFlush( 
				NetRCRemoveBlack.ID.CODE1_VALUE, 
				NetRCRemoveBlack.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCRemoveBlack msg ) {
		ioClient.writeAndFlush( 
				NetRCRemoveBlack.ID.CODE1_VALUE, 
				NetRCRemoveBlack.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCDuadMSGErrorCode msg ) {
		ioClient.writeAndFlush( 
				NetRCDuadMSGErrorCode.ID.CODE1_VALUE, 
				NetRCDuadMSGErrorCode.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCDuadMSGErrorCode msg ) {
		ioClient.writeAndFlush( 
				NetRCDuadMSGErrorCode.ID.CODE1_VALUE, 
				NetRCDuadMSGErrorCode.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCCheckFriendErrorCode msg ) {
		ioClient.writeAndFlush( 
				NetRCCheckFriendErrorCode.ID.CODE1_VALUE, 
				NetRCCheckFriendErrorCode.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCCheckFriendErrorCode msg ) {
		ioClient.writeAndFlush( 
				NetRCCheckFriendErrorCode.ID.CODE1_VALUE, 
				NetRCCheckFriendErrorCode.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCROpenRealTimeVoice msg ) {
		ioClient.writeAndFlush( 
				NetCROpenRealTimeVoice.ID.CODE1_VALUE, 
				NetCROpenRealTimeVoice.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCROpenRealTimeVoice msg ) {
		ioClient.writeAndFlush( 
				NetCROpenRealTimeVoice.ID.CODE1_VALUE, 
				NetCROpenRealTimeVoice.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCOpenRealTimeVoice msg ) {
		ioClient.writeAndFlush( 
				NetRCOpenRealTimeVoice.ID.CODE1_VALUE, 
				NetRCOpenRealTimeVoice.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCOpenRealTimeVoice msg ) {
		ioClient.writeAndFlush( 
				NetRCOpenRealTimeVoice.ID.CODE1_VALUE, 
				NetRCOpenRealTimeVoice.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRPlayingGamePeople msg ) {
		ioClient.writeAndFlush( 
				NetCRPlayingGamePeople.ID.CODE1_VALUE, 
				NetCRPlayingGamePeople.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRPlayingGamePeople msg ) {
		ioClient.writeAndFlush( 
				NetCRPlayingGamePeople.ID.CODE1_VALUE, 
				NetCRPlayingGamePeople.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCPlayingGamePeople msg ) {
		ioClient.writeAndFlush( 
				NetRCPlayingGamePeople.ID.CODE1_VALUE, 
				NetRCPlayingGamePeople.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCPlayingGamePeople msg ) {
		ioClient.writeAndFlush( 
				NetRCPlayingGamePeople.ID.CODE1_VALUE, 
				NetRCPlayingGamePeople.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRSettlementThreeState msg ) {
		ioClient.writeAndFlush( 
				NetCRSettlementThreeState.ID.CODE1_VALUE, 
				NetCRSettlementThreeState.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRSettlementThreeState msg ) {
		ioClient.writeAndFlush( 
				NetCRSettlementThreeState.ID.CODE1_VALUE, 
				NetCRSettlementThreeState.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCSettlementThreeState msg ) {
		ioClient.writeAndFlush( 
				NetRCSettlementThreeState.ID.CODE1_VALUE, 
				NetRCSettlementThreeState.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCSettlementThreeState msg ) {
		ioClient.writeAndFlush( 
				NetRCSettlementThreeState.ID.CODE1_VALUE, 
				NetRCSettlementThreeState.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRGetCharmRankFirst msg ) {
		ioClient.writeAndFlush( 
				NetCRGetCharmRankFirst.ID.CODE1_VALUE, 
				NetCRGetCharmRankFirst.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRGetCharmRankFirst msg ) {
		ioClient.writeAndFlush( 
				NetCRGetCharmRankFirst.ID.CODE1_VALUE, 
				NetCRGetCharmRankFirst.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCGetCharmRankFirst msg ) {
		ioClient.writeAndFlush( 
				NetRCGetCharmRankFirst.ID.CODE1_VALUE, 
				NetRCGetCharmRankFirst.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCGetCharmRankFirst msg ) {
		ioClient.writeAndFlush( 
				NetRCGetCharmRankFirst.ID.CODE1_VALUE, 
				NetRCGetCharmRankFirst.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRGetCharmRank msg ) {
		ioClient.writeAndFlush( 
				NetCRGetCharmRank.ID.CODE1_VALUE, 
				NetCRGetCharmRank.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRGetCharmRank msg ) {
		ioClient.writeAndFlush( 
				NetCRGetCharmRank.ID.CODE1_VALUE, 
				NetCRGetCharmRank.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCGetCharmRank msg ) {
		ioClient.writeAndFlush( 
				NetRCGetCharmRank.ID.CODE1_VALUE, 
				NetRCGetCharmRank.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCGetCharmRank msg ) {
		ioClient.writeAndFlush( 
				NetRCGetCharmRank.ID.CODE1_VALUE, 
				NetRCGetCharmRank.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRGetConsumeRankFirst msg ) {
		ioClient.writeAndFlush( 
				NetCRGetConsumeRankFirst.ID.CODE1_VALUE, 
				NetCRGetConsumeRankFirst.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRGetConsumeRankFirst msg ) {
		ioClient.writeAndFlush( 
				NetCRGetConsumeRankFirst.ID.CODE1_VALUE, 
				NetCRGetConsumeRankFirst.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCGetConsumeRankFirst msg ) {
		ioClient.writeAndFlush( 
				NetRCGetConsumeRankFirst.ID.CODE1_VALUE, 
				NetRCGetConsumeRankFirst.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCGetConsumeRankFirst msg ) {
		ioClient.writeAndFlush( 
				NetRCGetConsumeRankFirst.ID.CODE1_VALUE, 
				NetRCGetConsumeRankFirst.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRGetConsumeRank msg ) {
		ioClient.writeAndFlush( 
				NetCRGetConsumeRank.ID.CODE1_VALUE, 
				NetCRGetConsumeRank.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRGetConsumeRank msg ) {
		ioClient.writeAndFlush( 
				NetCRGetConsumeRank.ID.CODE1_VALUE, 
				NetCRGetConsumeRank.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCGetConsumeRank msg ) {
		ioClient.writeAndFlush( 
				NetRCGetConsumeRank.ID.CODE1_VALUE, 
				NetRCGetConsumeRank.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCGetConsumeRank msg ) {
		ioClient.writeAndFlush( 
				NetRCGetConsumeRank.ID.CODE1_VALUE, 
				NetRCGetConsumeRank.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCSendCoin1 msg ) {
		ioClient.writeAndFlush( 
				NetRCSendCoin1.ID.CODE1_VALUE, 
				NetRCSendCoin1.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCSendCoin1 msg ) {
		ioClient.writeAndFlush( 
				NetRCSendCoin1.ID.CODE1_VALUE, 
				NetRCSendCoin1.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCSendNotify msg ) {
		ioClient.writeAndFlush( 
				NetRCSendNotify.ID.CODE1_VALUE, 
				NetRCSendNotify.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCSendNotify msg ) {
		ioClient.writeAndFlush( 
				NetRCSendNotify.ID.CODE1_VALUE, 
				NetRCSendNotify.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRApplyMPGRoom msg ) {
		ioClient.writeAndFlush( 
				NetCRApplyMPGRoom.ID.CODE1_VALUE, 
				NetCRApplyMPGRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRApplyMPGRoom msg ) {
		ioClient.writeAndFlush( 
				NetCRApplyMPGRoom.ID.CODE1_VALUE, 
				NetCRApplyMPGRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCMPGRoom msg ) {
		ioClient.writeAndFlush( 
				NetRCMPGRoom.ID.CODE1_VALUE, 
				NetRCMPGRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCMPGRoom msg ) {
		ioClient.writeAndFlush( 
				NetRCMPGRoom.ID.CODE1_VALUE, 
				NetRCMPGRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRJoinMPGRoom msg ) {
		ioClient.writeAndFlush( 
				NetCRJoinMPGRoom.ID.CODE1_VALUE, 
				NetCRJoinMPGRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRJoinMPGRoom msg ) {
		ioClient.writeAndFlush( 
				NetCRJoinMPGRoom.ID.CODE1_VALUE, 
				NetCRJoinMPGRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCRQuitWaitJoinGame msg ) {
		ioClient.writeAndFlush( 
				NetCRQuitWaitJoinGame.ID.CODE1_VALUE, 
				NetCRQuitWaitJoinGame.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCRQuitWaitJoinGame msg ) {
		ioClient.writeAndFlush( 
				NetCRQuitWaitJoinGame.ID.CODE1_VALUE, 
				NetCRQuitWaitJoinGame.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCQuitWaitJoinGame msg ) {
		ioClient.writeAndFlush( 
				NetRCQuitWaitJoinGame.ID.CODE1_VALUE, 
				NetRCQuitWaitJoinGame.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCQuitWaitJoinGame msg ) {
		ioClient.writeAndFlush( 
				NetRCQuitWaitJoinGame.ID.CODE1_VALUE, 
				NetRCQuitWaitJoinGame.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRCTiPlayer msg ) {
		ioClient.writeAndFlush( 
				NetRCTiPlayer.ID.CODE1_VALUE, 
				NetRCTiPlayer.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRCTiPlayer msg ) {
		ioClient.writeAndFlush( 
				NetRCTiPlayer.ID.CODE1_VALUE, 
				NetRCTiPlayer.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRDRoomJoinDataCenter msg ) {
		ioClient.writeAndFlush( 
				NetRDRoomJoinDataCenter.ID.CODE1_VALUE, 
				NetRDRoomJoinDataCenter.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRDRoomJoinDataCenter msg ) {
		ioClient.writeAndFlush( 
				NetRDRoomJoinDataCenter.ID.CODE1_VALUE, 
				NetRDRoomJoinDataCenter.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetDRRoomJoinDataCenter msg ) {
		ioClient.writeAndFlush( 
				NetDRRoomJoinDataCenter.ID.CODE1_VALUE, 
				NetDRRoomJoinDataCenter.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetDRRoomJoinDataCenter msg ) {
		ioClient.writeAndFlush( 
				NetDRRoomJoinDataCenter.ID.CODE1_VALUE, 
				NetDRRoomJoinDataCenter.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetDRTryConnRoom msg ) {
		ioClient.writeAndFlush( 
				NetDRTryConnRoom.ID.CODE1_VALUE, 
				NetDRTryConnRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetDRTryConnRoom msg ) {
		ioClient.writeAndFlush( 
				NetDRTryConnRoom.ID.CODE1_VALUE, 
				NetDRTryConnRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRDPlayerJoinRoom msg ) {
		ioClient.writeAndFlush( 
				NetRDPlayerJoinRoom.ID.CODE1_VALUE, 
				NetRDPlayerJoinRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRDPlayerJoinRoom msg ) {
		ioClient.writeAndFlush( 
				NetRDPlayerJoinRoom.ID.CODE1_VALUE, 
				NetRDPlayerJoinRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetDRPlayerJoinRoom msg ) {
		ioClient.writeAndFlush( 
				NetDRPlayerJoinRoom.ID.CODE1_VALUE, 
				NetDRPlayerJoinRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetDRPlayerJoinRoom msg ) {
		ioClient.writeAndFlush( 
				NetDRPlayerJoinRoom.ID.CODE1_VALUE, 
				NetDRPlayerJoinRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRDCheckFriend msg ) {
		ioClient.writeAndFlush( 
				NetRDCheckFriend.ID.CODE1_VALUE, 
				NetRDCheckFriend.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRDCheckFriend msg ) {
		ioClient.writeAndFlush( 
				NetRDCheckFriend.ID.CODE1_VALUE, 
				NetRDCheckFriend.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetDRJoinFriend msg ) {
		ioClient.writeAndFlush( 
				NetDRJoinFriend.ID.CODE1_VALUE, 
				NetDRJoinFriend.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetDRJoinFriend msg ) {
		ioClient.writeAndFlush( 
				NetDRJoinFriend.ID.CODE1_VALUE, 
				NetDRJoinFriend.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRDSearchPlayerByGame msg ) {
		ioClient.writeAndFlush( 
				NetRDSearchPlayerByGame.ID.CODE1_VALUE, 
				NetRDSearchPlayerByGame.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRDSearchPlayerByGame msg ) {
		ioClient.writeAndFlush( 
				NetRDSearchPlayerByGame.ID.CODE1_VALUE, 
				NetRDSearchPlayerByGame.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetDRSearchPlayerByGame msg ) {
		ioClient.writeAndFlush( 
				NetDRSearchPlayerByGame.ID.CODE1_VALUE, 
				NetDRSearchPlayerByGame.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetDRSearchPlayerByGame msg ) {
		ioClient.writeAndFlush( 
				NetDRSearchPlayerByGame.ID.CODE1_VALUE, 
				NetDRSearchPlayerByGame.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRDSeachLimit msg ) {
		ioClient.writeAndFlush( 
				NetRDSeachLimit.ID.CODE1_VALUE, 
				NetRDSeachLimit.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRDSeachLimit msg ) {
		ioClient.writeAndFlush( 
				NetRDSeachLimit.ID.CODE1_VALUE, 
				NetRDSeachLimit.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetDRSeachLimit msg ) {
		ioClient.writeAndFlush( 
				NetDRSeachLimit.ID.CODE1_VALUE, 
				NetDRSeachLimit.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetDRSeachLimit msg ) {
		ioClient.writeAndFlush( 
				NetDRSeachLimit.ID.CODE1_VALUE, 
				NetDRSeachLimit.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRDCancelMatch msg ) {
		ioClient.writeAndFlush( 
				NetRDCancelMatch.ID.CODE1_VALUE, 
				NetRDCancelMatch.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRDCancelMatch msg ) {
		ioClient.writeAndFlush( 
				NetRDCancelMatch.ID.CODE1_VALUE, 
				NetRDCancelMatch.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRDPlayerQuitRoom msg ) {
		ioClient.writeAndFlush( 
				NetRDPlayerQuitRoom.ID.CODE1_VALUE, 
				NetRDPlayerQuitRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRDPlayerQuitRoom msg ) {
		ioClient.writeAndFlush( 
				NetRDPlayerQuitRoom.ID.CODE1_VALUE, 
				NetRDPlayerQuitRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetDRPlayerQuitRoom msg ) {
		ioClient.writeAndFlush( 
				NetDRPlayerQuitRoom.ID.CODE1_VALUE, 
				NetDRPlayerQuitRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetDRPlayerQuitRoom msg ) {
		ioClient.writeAndFlush( 
				NetDRPlayerQuitRoom.ID.CODE1_VALUE, 
				NetDRPlayerQuitRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetDRUpdatePlayerState msg ) {
		ioClient.writeAndFlush( 
				NetDRUpdatePlayerState.ID.CODE1_VALUE, 
				NetDRUpdatePlayerState.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetDRUpdatePlayerState msg ) {
		ioClient.writeAndFlush( 
				NetDRUpdatePlayerState.ID.CODE1_VALUE, 
				NetDRUpdatePlayerState.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetDRTempFriend msg ) {
		ioClient.writeAndFlush( 
				NetDRTempFriend.ID.CODE1_VALUE, 
				NetDRTempFriend.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetDRTempFriend msg ) {
		ioClient.writeAndFlush( 
				NetDRTempFriend.ID.CODE1_VALUE, 
				NetDRTempFriend.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRDBlackOther msg ) {
		ioClient.writeAndFlush( 
				NetRDBlackOther.ID.CODE1_VALUE, 
				NetRDBlackOther.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRDBlackOther msg ) {
		ioClient.writeAndFlush( 
				NetRDBlackOther.ID.CODE1_VALUE, 
				NetRDBlackOther.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRDDeleteFriend msg ) {
		ioClient.writeAndFlush( 
				NetRDDeleteFriend.ID.CODE1_VALUE, 
				NetRDDeleteFriend.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRDDeleteFriend msg ) {
		ioClient.writeAndFlush( 
				NetRDDeleteFriend.ID.CODE1_VALUE, 
				NetRDDeleteFriend.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetDRBlackOther msg ) {
		ioClient.writeAndFlush( 
				NetDRBlackOther.ID.CODE1_VALUE, 
				NetDRBlackOther.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetDRBlackOther msg ) {
		ioClient.writeAndFlush( 
				NetDRBlackOther.ID.CODE1_VALUE, 
				NetDRBlackOther.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetDRDeleteFriend msg ) {
		ioClient.writeAndFlush( 
				NetDRDeleteFriend.ID.CODE1_VALUE, 
				NetDRDeleteFriend.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetDRDeleteFriend msg ) {
		ioClient.writeAndFlush( 
				NetDRDeleteFriend.ID.CODE1_VALUE, 
				NetDRDeleteFriend.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRDNotifyBattleResult msg ) {
		ioClient.writeAndFlush( 
				NetRDNotifyBattleResult.ID.CODE1_VALUE, 
				NetRDNotifyBattleResult.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRDNotifyBattleResult msg ) {
		ioClient.writeAndFlush( 
				NetRDNotifyBattleResult.ID.CODE1_VALUE, 
				NetRDNotifyBattleResult.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRDSendGift msg ) {
		ioClient.writeAndFlush( 
				NetRDSendGift.ID.CODE1_VALUE, 
				NetRDSendGift.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRDSendGift msg ) {
		ioClient.writeAndFlush( 
				NetRDSendGift.ID.CODE1_VALUE, 
				NetRDSendGift.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetDRSendGift msg ) {
		ioClient.writeAndFlush( 
				NetDRSendGift.ID.CODE1_VALUE, 
				NetDRSendGift.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetDRSendGift msg ) {
		ioClient.writeAndFlush( 
				NetDRSendGift.ID.CODE1_VALUE, 
				NetDRSendGift.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRDShowPlayerData msg ) {
		ioClient.writeAndFlush( 
				NetRDShowPlayerData.ID.CODE1_VALUE, 
				NetRDShowPlayerData.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRDShowPlayerData msg ) {
		ioClient.writeAndFlush( 
				NetRDShowPlayerData.ID.CODE1_VALUE, 
				NetRDShowPlayerData.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetDRShowPlayerData msg ) {
		ioClient.writeAndFlush( 
				NetDRShowPlayerData.ID.CODE1_VALUE, 
				NetDRShowPlayerData.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetDRShowPlayerData msg ) {
		ioClient.writeAndFlush( 
				NetDRShowPlayerData.ID.CODE1_VALUE, 
				NetDRShowPlayerData.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRDJoinFriend msg ) {
		ioClient.writeAndFlush( 
				NetRDJoinFriend.ID.CODE1_VALUE, 
				NetRDJoinFriend.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRDJoinFriend msg ) {
		ioClient.writeAndFlush( 
				NetRDJoinFriend.ID.CODE1_VALUE, 
				NetRDJoinFriend.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRDRemoveTempFriend msg ) {
		ioClient.writeAndFlush( 
				NetRDRemoveTempFriend.ID.CODE1_VALUE, 
				NetRDRemoveTempFriend.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRDRemoveTempFriend msg ) {
		ioClient.writeAndFlush( 
				NetRDRemoveTempFriend.ID.CODE1_VALUE, 
				NetRDRemoveTempFriend.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRDModifyMyselfData msg ) {
		ioClient.writeAndFlush( 
				NetRDModifyMyselfData.ID.CODE1_VALUE, 
				NetRDModifyMyselfData.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRDModifyMyselfData msg ) {
		ioClient.writeAndFlush( 
				NetRDModifyMyselfData.ID.CODE1_VALUE, 
				NetRDModifyMyselfData.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetDRModifyMyselfData msg ) {
		ioClient.writeAndFlush( 
				NetDRModifyMyselfData.ID.CODE1_VALUE, 
				NetDRModifyMyselfData.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetDRModifyMyselfData msg ) {
		ioClient.writeAndFlush( 
				NetDRModifyMyselfData.ID.CODE1_VALUE, 
				NetDRModifyMyselfData.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRDPlayerQuitGame msg ) {
		ioClient.writeAndFlush( 
				NetRDPlayerQuitGame.ID.CODE1_VALUE, 
				NetRDPlayerQuitGame.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRDPlayerQuitGame msg ) {
		ioClient.writeAndFlush( 
				NetRDPlayerQuitGame.ID.CODE1_VALUE, 
				NetRDPlayerQuitGame.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRDRemoveBlack msg ) {
		ioClient.writeAndFlush( 
				NetRDRemoveBlack.ID.CODE1_VALUE, 
				NetRDRemoveBlack.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRDRemoveBlack msg ) {
		ioClient.writeAndFlush( 
				NetRDRemoveBlack.ID.CODE1_VALUE, 
				NetRDRemoveBlack.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetDRRemoveBlack msg ) {
		ioClient.writeAndFlush( 
				NetDRRemoveBlack.ID.CODE1_VALUE, 
				NetDRRemoveBlack.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetDRRemoveBlack msg ) {
		ioClient.writeAndFlush( 
				NetDRRemoveBlack.ID.CODE1_VALUE, 
				NetDRRemoveBlack.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRDSearchPlayerById msg ) {
		ioClient.writeAndFlush( 
				NetRDSearchPlayerById.ID.CODE1_VALUE, 
				NetRDSearchPlayerById.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRDSearchPlayerById msg ) {
		ioClient.writeAndFlush( 
				NetRDSearchPlayerById.ID.CODE1_VALUE, 
				NetRDSearchPlayerById.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetDRSearchPlayerById msg ) {
		ioClient.writeAndFlush( 
				NetDRSearchPlayerById.ID.CODE1_VALUE, 
				NetDRSearchPlayerById.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetDRSearchPlayerById msg ) {
		ioClient.writeAndFlush( 
				NetDRSearchPlayerById.ID.CODE1_VALUE, 
				NetDRSearchPlayerById.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRDCheckOpenRealTimeVoice msg ) {
		ioClient.writeAndFlush( 
				NetRDCheckOpenRealTimeVoice.ID.CODE1_VALUE, 
				NetRDCheckOpenRealTimeVoice.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRDCheckOpenRealTimeVoice msg ) {
		ioClient.writeAndFlush( 
				NetRDCheckOpenRealTimeVoice.ID.CODE1_VALUE, 
				NetRDCheckOpenRealTimeVoice.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetDRCheckOpenRealTimeVoice msg ) {
		ioClient.writeAndFlush( 
				NetDRCheckOpenRealTimeVoice.ID.CODE1_VALUE, 
				NetDRCheckOpenRealTimeVoice.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetDRCheckOpenRealTimeVoice msg ) {
		ioClient.writeAndFlush( 
				NetDRCheckOpenRealTimeVoice.ID.CODE1_VALUE, 
				NetDRCheckOpenRealTimeVoice.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRDCancelSeachPlayerByLimit msg ) {
		ioClient.writeAndFlush( 
				NetRDCancelSeachPlayerByLimit.ID.CODE1_VALUE, 
				NetRDCancelSeachPlayerByLimit.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRDCancelSeachPlayerByLimit msg ) {
		ioClient.writeAndFlush( 
				NetRDCancelSeachPlayerByLimit.ID.CODE1_VALUE, 
				NetRDCancelSeachPlayerByLimit.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetDRSendGiftOfflineReceive msg ) {
		ioClient.writeAndFlush( 
				NetDRSendGiftOfflineReceive.ID.CODE1_VALUE, 
				NetDRSendGiftOfflineReceive.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetDRSendGiftOfflineReceive msg ) {
		ioClient.writeAndFlush( 
				NetDRSendGiftOfflineReceive.ID.CODE1_VALUE, 
				NetDRSendGiftOfflineReceive.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRDNearyPeopleData msg ) {
		ioClient.writeAndFlush( 
				NetRDNearyPeopleData.ID.CODE1_VALUE, 
				NetRDNearyPeopleData.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRDNearyPeopleData msg ) {
		ioClient.writeAndFlush( 
				NetRDNearyPeopleData.ID.CODE1_VALUE, 
				NetRDNearyPeopleData.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetDRNearyPeopleData msg ) {
		ioClient.writeAndFlush( 
				NetDRNearyPeopleData.ID.CODE1_VALUE, 
				NetDRNearyPeopleData.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetDRNearyPeopleData msg ) {
		ioClient.writeAndFlush( 
				NetDRNearyPeopleData.ID.CODE1_VALUE, 
				NetDRNearyPeopleData.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRDInBlack msg ) {
		ioClient.writeAndFlush( 
				NetRDInBlack.ID.CODE1_VALUE, 
				NetRDInBlack.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRDInBlack msg ) {
		ioClient.writeAndFlush( 
				NetRDInBlack.ID.CODE1_VALUE, 
				NetRDInBlack.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetDRInBlack msg ) {
		ioClient.writeAndFlush( 
				NetDRInBlack.ID.CODE1_VALUE, 
				NetDRInBlack.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetDRInBlack msg ) {
		ioClient.writeAndFlush( 
				NetDRInBlack.ID.CODE1_VALUE, 
				NetDRInBlack.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetDRSendCoin1 msg ) {
		ioClient.writeAndFlush( 
				NetDRSendCoin1.ID.CODE1_VALUE, 
				NetDRSendCoin1.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetDRSendCoin1 msg ) {
		ioClient.writeAndFlush( 
				NetDRSendCoin1.ID.CODE1_VALUE, 
				NetDRSendCoin1.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetDRSendNotify msg ) {
		ioClient.writeAndFlush( 
				NetDRSendNotify.ID.CODE1_VALUE, 
				NetDRSendNotify.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetDRSendNotify msg ) {
		ioClient.writeAndFlush( 
				NetDRSendNotify.ID.CODE1_VALUE, 
				NetDRSendNotify.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRDQuitWaitJoinGame msg ) {
		ioClient.writeAndFlush( 
				NetRDQuitWaitJoinGame.ID.CODE1_VALUE, 
				NetRDQuitWaitJoinGame.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRDQuitWaitJoinGame msg ) {
		ioClient.writeAndFlush( 
				NetRDQuitWaitJoinGame.ID.CODE1_VALUE, 
				NetRDQuitWaitJoinGame.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetDRQuitWaitJoinGame msg ) {
		ioClient.writeAndFlush( 
				NetDRQuitWaitJoinGame.ID.CODE1_VALUE, 
				NetDRQuitWaitJoinGame.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetDRQuitWaitJoinGame msg ) {
		ioClient.writeAndFlush( 
				NetDRQuitWaitJoinGame.ID.CODE1_VALUE, 
				NetDRQuitWaitJoinGame.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRDJoinMPGGame msg ) {
		ioClient.writeAndFlush( 
				NetRDJoinMPGGame.ID.CODE1_VALUE, 
				NetRDJoinMPGGame.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRDJoinMPGGame msg ) {
		ioClient.writeAndFlush( 
				NetRDJoinMPGGame.ID.CODE1_VALUE, 
				NetRDJoinMPGGame.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBRCloseDoorServer msg ) {
		ioClient.writeAndFlush( 
				NetWEBRCloseDoorServer.ID.CODE1_VALUE, 
				NetWEBRCloseDoorServer.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBRCloseDoorServer msg ) {
		ioClient.writeAndFlush( 
				NetWEBRCloseDoorServer.ID.CODE1_VALUE, 
				NetWEBRCloseDoorServer.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRWEBCloseDoorServer msg ) {
		ioClient.writeAndFlush( 
				NetRWEBCloseDoorServer.ID.CODE1_VALUE, 
				NetRWEBCloseDoorServer.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRWEBCloseDoorServer msg ) {
		ioClient.writeAndFlush( 
				NetRWEBCloseDoorServer.ID.CODE1_VALUE, 
				NetRWEBCloseDoorServer.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBROpenDoorServer msg ) {
		ioClient.writeAndFlush( 
				NetWEBROpenDoorServer.ID.CODE1_VALUE, 
				NetWEBROpenDoorServer.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBROpenDoorServer msg ) {
		ioClient.writeAndFlush( 
				NetWEBROpenDoorServer.ID.CODE1_VALUE, 
				NetWEBROpenDoorServer.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRWEBOpenDoorServer msg ) {
		ioClient.writeAndFlush( 
				NetRWEBOpenDoorServer.ID.CODE1_VALUE, 
				NetRWEBOpenDoorServer.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRWEBOpenDoorServer msg ) {
		ioClient.writeAndFlush( 
				NetRWEBOpenDoorServer.ID.CODE1_VALUE, 
				NetRWEBOpenDoorServer.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBRPlayerServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetWEBRPlayerServerInfo.ID.CODE1_VALUE, 
				NetWEBRPlayerServerInfo.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBRPlayerServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetWEBRPlayerServerInfo.ID.CODE1_VALUE, 
				NetWEBRPlayerServerInfo.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRWEBPlayerServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetRWEBPlayerServerInfo.ID.CODE1_VALUE, 
				NetRWEBPlayerServerInfo.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRWEBPlayerServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetRWEBPlayerServerInfo.ID.CODE1_VALUE, 
				NetRWEBPlayerServerInfo.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBRServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetWEBRServerInfo.ID.CODE1_VALUE, 
				NetWEBRServerInfo.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBRServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetWEBRServerInfo.ID.CODE1_VALUE, 
				NetWEBRServerInfo.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRWEBServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetRWEBServerInfo.ID.CODE1_VALUE, 
				NetRWEBServerInfo.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRWEBServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetRWEBServerInfo.ID.CODE1_VALUE, 
				NetRWEBServerInfo.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBROpenRecvMSGLog msg ) {
		ioClient.writeAndFlush( 
				NetWEBROpenRecvMSGLog.ID.CODE1_VALUE, 
				NetWEBROpenRecvMSGLog.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBROpenRecvMSGLog msg ) {
		ioClient.writeAndFlush( 
				NetWEBROpenRecvMSGLog.ID.CODE1_VALUE, 
				NetWEBROpenRecvMSGLog.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRWEBOpenRecvMSGLog msg ) {
		ioClient.writeAndFlush( 
				NetRWEBOpenRecvMSGLog.ID.CODE1_VALUE, 
				NetRWEBOpenRecvMSGLog.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRWEBOpenRecvMSGLog msg ) {
		ioClient.writeAndFlush( 
				NetRWEBOpenRecvMSGLog.ID.CODE1_VALUE, 
				NetRWEBOpenRecvMSGLog.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBRCloseRecvMSGLog msg ) {
		ioClient.writeAndFlush( 
				NetWEBRCloseRecvMSGLog.ID.CODE1_VALUE, 
				NetWEBRCloseRecvMSGLog.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBRCloseRecvMSGLog msg ) {
		ioClient.writeAndFlush( 
				NetWEBRCloseRecvMSGLog.ID.CODE1_VALUE, 
				NetWEBRCloseRecvMSGLog.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRWEBCloseRecvMSGLog msg ) {
		ioClient.writeAndFlush( 
				NetRWEBCloseRecvMSGLog.ID.CODE1_VALUE, 
				NetRWEBCloseRecvMSGLog.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRWEBCloseRecvMSGLog msg ) {
		ioClient.writeAndFlush( 
				NetRWEBCloseRecvMSGLog.ID.CODE1_VALUE, 
				NetRWEBCloseRecvMSGLog.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBRStopServer msg ) {
		ioClient.writeAndFlush( 
				NetWEBRStopServer.ID.CODE1_VALUE, 
				NetWEBRStopServer.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBRStopServer msg ) {
		ioClient.writeAndFlush( 
				NetWEBRStopServer.ID.CODE1_VALUE, 
				NetWEBRStopServer.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRWEBStopServer msg ) {
		ioClient.writeAndFlush( 
				NetRWEBStopServer.ID.CODE1_VALUE, 
				NetRWEBStopServer.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRWEBStopServer msg ) {
		ioClient.writeAndFlush( 
				NetRWEBStopServer.ID.CODE1_VALUE, 
				NetRWEBStopServer.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGJoinGame msg ) {
		ioClient.writeAndFlush( 
				NetCGJoinGame.ID.CODE1_VALUE, 
				NetCGJoinGame.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGJoinGame msg ) {
		ioClient.writeAndFlush( 
				NetCGJoinGame.ID.CODE1_VALUE, 
				NetCGJoinGame.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCJoinGame msg ) {
		ioClient.writeAndFlush( 
				NetGCJoinGame.ID.CODE1_VALUE, 
				NetGCJoinGame.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCJoinGame msg ) {
		ioClient.writeAndFlush( 
				NetGCJoinGame.ID.CODE1_VALUE, 
				NetGCJoinGame.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGTest msg ) {
		ioClient.writeAndFlush( 
				NetCGTest.ID.CODE1_VALUE, 
				NetCGTest.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGTest msg ) {
		ioClient.writeAndFlush( 
				NetCGTest.ID.CODE1_VALUE, 
				NetCGTest.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGItem msg ) {
		ioClient.writeAndFlush( 
				NetCGItem.ID.CODE1_VALUE, 
				NetCGItem.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGItem msg ) {
		ioClient.writeAndFlush( 
				NetCGItem.ID.CODE1_VALUE, 
				NetCGItem.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGThrowItem msg ) {
		ioClient.writeAndFlush( 
				NetCGThrowItem.ID.CODE1_VALUE, 
				NetCGThrowItem.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGThrowItem msg ) {
		ioClient.writeAndFlush( 
				NetCGThrowItem.ID.CODE1_VALUE, 
				NetCGThrowItem.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGThrowExpression msg ) {
		ioClient.writeAndFlush( 
				NetCGThrowExpression.ID.CODE1_VALUE, 
				NetCGThrowExpression.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGThrowExpression msg ) {
		ioClient.writeAndFlush( 
				NetCGThrowExpression.ID.CODE1_VALUE, 
				NetCGThrowExpression.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCThrowItem msg ) {
		ioClient.writeAndFlush( 
				NetGCThrowItem.ID.CODE1_VALUE, 
				NetGCThrowItem.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCThrowItem msg ) {
		ioClient.writeAndFlush( 
				NetGCThrowItem.ID.CODE1_VALUE, 
				NetGCThrowItem.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCThrowExpression msg ) {
		ioClient.writeAndFlush( 
				NetGCThrowExpression.ID.CODE1_VALUE, 
				NetGCThrowExpression.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCThrowExpression msg ) {
		ioClient.writeAndFlush( 
				NetGCThrowExpression.ID.CODE1_VALUE, 
				NetGCThrowExpression.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGLoser msg ) {
		ioClient.writeAndFlush( 
				NetCGLoser.ID.CODE1_VALUE, 
				NetCGLoser.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGLoser msg ) {
		ioClient.writeAndFlush( 
				NetCGLoser.ID.CODE1_VALUE, 
				NetCGLoser.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGPeaceRequest msg ) {
		ioClient.writeAndFlush( 
				NetCGPeaceRequest.ID.CODE1_VALUE, 
				NetCGPeaceRequest.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGPeaceRequest msg ) {
		ioClient.writeAndFlush( 
				NetCGPeaceRequest.ID.CODE1_VALUE, 
				NetCGPeaceRequest.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCPeaceNotify msg ) {
		ioClient.writeAndFlush( 
				NetGCPeaceNotify.ID.CODE1_VALUE, 
				NetGCPeaceNotify.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCPeaceNotify msg ) {
		ioClient.writeAndFlush( 
				NetGCPeaceNotify.ID.CODE1_VALUE, 
				NetGCPeaceNotify.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGPeaceResult msg ) {
		ioClient.writeAndFlush( 
				NetCGPeaceResult.ID.CODE1_VALUE, 
				NetCGPeaceResult.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGPeaceResult msg ) {
		ioClient.writeAndFlush( 
				NetCGPeaceResult.ID.CODE1_VALUE, 
				NetCGPeaceResult.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCPeaceResult msg ) {
		ioClient.writeAndFlush( 
				NetGCPeaceResult.ID.CODE1_VALUE, 
				NetGCPeaceResult.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCPeaceResult msg ) {
		ioClient.writeAndFlush( 
				NetGCPeaceResult.ID.CODE1_VALUE, 
				NetGCPeaceResult.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCAllJoin msg ) {
		ioClient.writeAndFlush( 
				NetGCAllJoin.ID.CODE1_VALUE, 
				NetGCAllJoin.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCAllJoin msg ) {
		ioClient.writeAndFlush( 
				NetGCAllJoin.ID.CODE1_VALUE, 
				NetGCAllJoin.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGQuitRoom msg ) {
		ioClient.writeAndFlush( 
				NetCGQuitRoom.ID.CODE1_VALUE, 
				NetCGQuitRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGQuitRoom msg ) {
		ioClient.writeAndFlush( 
				NetCGQuitRoom.ID.CODE1_VALUE, 
				NetCGQuitRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGAircraftInit msg ) {
		ioClient.writeAndFlush( 
				NetCGAircraftInit.ID.CODE1_VALUE, 
				NetCGAircraftInit.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGAircraftInit msg ) {
		ioClient.writeAndFlush( 
				NetCGAircraftInit.ID.CODE1_VALUE, 
				NetCGAircraftInit.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCAircraftInit msg ) {
		ioClient.writeAndFlush( 
				NetGCAircraftInit.ID.CODE1_VALUE, 
				NetGCAircraftInit.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCAircraftInit msg ) {
		ioClient.writeAndFlush( 
				NetGCAircraftInit.ID.CODE1_VALUE, 
				NetGCAircraftInit.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGAircraftHitPush msg ) {
		ioClient.writeAndFlush( 
				NetCGAircraftHitPush.ID.CODE1_VALUE, 
				NetCGAircraftHitPush.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGAircraftHitPush msg ) {
		ioClient.writeAndFlush( 
				NetCGAircraftHitPush.ID.CODE1_VALUE, 
				NetCGAircraftHitPush.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCAircraftHitPush msg ) {
		ioClient.writeAndFlush( 
				NetGCAircraftHitPush.ID.CODE1_VALUE, 
				NetGCAircraftHitPush.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCAircraftHitPush msg ) {
		ioClient.writeAndFlush( 
				NetGCAircraftHitPush.ID.CODE1_VALUE, 
				NetGCAircraftHitPush.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCGameResult msg ) {
		ioClient.writeAndFlush( 
				NetGCGameResult.ID.CODE1_VALUE, 
				NetGCGameResult.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCGameResult msg ) {
		ioClient.writeAndFlush( 
				NetGCGameResult.ID.CODE1_VALUE, 
				NetGCGameResult.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGGolangPush msg ) {
		ioClient.writeAndFlush( 
				NetCGGolangPush.ID.CODE1_VALUE, 
				NetCGGolangPush.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGGolangPush msg ) {
		ioClient.writeAndFlush( 
				NetCGGolangPush.ID.CODE1_VALUE, 
				NetCGGolangPush.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCGolangPush msg ) {
		ioClient.writeAndFlush( 
				NetGCGolangPush.ID.CODE1_VALUE, 
				NetGCGolangPush.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCGolangPush msg ) {
		ioClient.writeAndFlush( 
				NetGCGolangPush.ID.CODE1_VALUE, 
				NetGCGolangPush.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCGolangSuccess msg ) {
		ioClient.writeAndFlush( 
				NetGCGolangSuccess.ID.CODE1_VALUE, 
				NetGCGolangSuccess.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCGolangSuccess msg ) {
		ioClient.writeAndFlush( 
				NetGCGolangSuccess.ID.CODE1_VALUE, 
				NetGCGolangSuccess.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGAnimalChessOpen msg ) {
		ioClient.writeAndFlush( 
				NetCGAnimalChessOpen.ID.CODE1_VALUE, 
				NetCGAnimalChessOpen.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGAnimalChessOpen msg ) {
		ioClient.writeAndFlush( 
				NetCGAnimalChessOpen.ID.CODE1_VALUE, 
				NetCGAnimalChessOpen.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCAnimalChessOpen msg ) {
		ioClient.writeAndFlush( 
				NetGCAnimalChessOpen.ID.CODE1_VALUE, 
				NetGCAnimalChessOpen.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCAnimalChessOpen msg ) {
		ioClient.writeAndFlush( 
				NetGCAnimalChessOpen.ID.CODE1_VALUE, 
				NetGCAnimalChessOpen.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGAnimalChessMove msg ) {
		ioClient.writeAndFlush( 
				NetCGAnimalChessMove.ID.CODE1_VALUE, 
				NetCGAnimalChessMove.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGAnimalChessMove msg ) {
		ioClient.writeAndFlush( 
				NetCGAnimalChessMove.ID.CODE1_VALUE, 
				NetCGAnimalChessMove.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCAnimalChessMove msg ) {
		ioClient.writeAndFlush( 
				NetGCAnimalChessMove.ID.CODE1_VALUE, 
				NetGCAnimalChessMove.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCAnimalChessMove msg ) {
		ioClient.writeAndFlush( 
				NetGCAnimalChessMove.ID.CODE1_VALUE, 
				NetGCAnimalChessMove.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCAnimalChessResultWithDarkPiece msg ) {
		ioClient.writeAndFlush( 
				NetGCAnimalChessResultWithDarkPiece.ID.CODE1_VALUE, 
				NetGCAnimalChessResultWithDarkPiece.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCAnimalChessResultWithDarkPiece msg ) {
		ioClient.writeAndFlush( 
				NetGCAnimalChessResultWithDarkPiece.ID.CODE1_VALUE, 
				NetGCAnimalChessResultWithDarkPiece.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCLinkChessPanel msg ) {
		ioClient.writeAndFlush( 
				NetGCLinkChessPanel.ID.CODE1_VALUE, 
				NetGCLinkChessPanel.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCLinkChessPanel msg ) {
		ioClient.writeAndFlush( 
				NetGCLinkChessPanel.ID.CODE1_VALUE, 
				NetGCLinkChessPanel.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGLinkChessSelect msg ) {
		ioClient.writeAndFlush( 
				NetCGLinkChessSelect.ID.CODE1_VALUE, 
				NetCGLinkChessSelect.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGLinkChessSelect msg ) {
		ioClient.writeAndFlush( 
				NetCGLinkChessSelect.ID.CODE1_VALUE, 
				NetCGLinkChessSelect.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCLinkChessSelect msg ) {
		ioClient.writeAndFlush( 
				NetGCLinkChessSelect.ID.CODE1_VALUE, 
				NetGCLinkChessSelect.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCLinkChessSelect msg ) {
		ioClient.writeAndFlush( 
				NetGCLinkChessSelect.ID.CODE1_VALUE, 
				NetGCLinkChessSelect.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGOthelloPush msg ) {
		ioClient.writeAndFlush( 
				NetCGOthelloPush.ID.CODE1_VALUE, 
				NetCGOthelloPush.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGOthelloPush msg ) {
		ioClient.writeAndFlush( 
				NetCGOthelloPush.ID.CODE1_VALUE, 
				NetCGOthelloPush.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCOthelloPush msg ) {
		ioClient.writeAndFlush( 
				NetGCOthelloPush.ID.CODE1_VALUE, 
				NetGCOthelloPush.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCOthelloPush msg ) {
		ioClient.writeAndFlush( 
				NetGCOthelloPush.ID.CODE1_VALUE, 
				NetGCOthelloPush.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCOthelloSuccess msg ) {
		ioClient.writeAndFlush( 
				NetGCOthelloSuccess.ID.CODE1_VALUE, 
				NetGCOthelloSuccess.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCOthelloSuccess msg ) {
		ioClient.writeAndFlush( 
				NetGCOthelloSuccess.ID.CODE1_VALUE, 
				NetGCOthelloSuccess.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGFlappyBirdStart msg ) {
		ioClient.writeAndFlush( 
				NetCGFlappyBirdStart.ID.CODE1_VALUE, 
				NetCGFlappyBirdStart.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGFlappyBirdStart msg ) {
		ioClient.writeAndFlush( 
				NetCGFlappyBirdStart.ID.CODE1_VALUE, 
				NetCGFlappyBirdStart.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGFlappyBirdMap msg ) {
		ioClient.writeAndFlush( 
				NetCGFlappyBirdMap.ID.CODE1_VALUE, 
				NetCGFlappyBirdMap.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGFlappyBirdMap msg ) {
		ioClient.writeAndFlush( 
				NetCGFlappyBirdMap.ID.CODE1_VALUE, 
				NetCGFlappyBirdMap.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCFlappyBirdMap msg ) {
		ioClient.writeAndFlush( 
				NetGCFlappyBirdMap.ID.CODE1_VALUE, 
				NetGCFlappyBirdMap.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCFlappyBirdMap msg ) {
		ioClient.writeAndFlush( 
				NetGCFlappyBirdMap.ID.CODE1_VALUE, 
				NetGCFlappyBirdMap.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGFlappyBirdClick msg ) {
		ioClient.writeAndFlush( 
				NetCGFlappyBirdClick.ID.CODE1_VALUE, 
				NetCGFlappyBirdClick.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGFlappyBirdClick msg ) {
		ioClient.writeAndFlush( 
				NetCGFlappyBirdClick.ID.CODE1_VALUE, 
				NetCGFlappyBirdClick.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCFlappyBirdClick msg ) {
		ioClient.writeAndFlush( 
				NetGCFlappyBirdClick.ID.CODE1_VALUE, 
				NetGCFlappyBirdClick.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCFlappyBirdClick msg ) {
		ioClient.writeAndFlush( 
				NetGCFlappyBirdClick.ID.CODE1_VALUE, 
				NetGCFlappyBirdClick.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGCoreBallFire msg ) {
		ioClient.writeAndFlush( 
				NetCGCoreBallFire.ID.CODE1_VALUE, 
				NetCGCoreBallFire.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGCoreBallFire msg ) {
		ioClient.writeAndFlush( 
				NetCGCoreBallFire.ID.CODE1_VALUE, 
				NetCGCoreBallFire.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCCoreBallFire msg ) {
		ioClient.writeAndFlush( 
				NetGCCoreBallFire.ID.CODE1_VALUE, 
				NetGCCoreBallFire.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCCoreBallFire msg ) {
		ioClient.writeAndFlush( 
				NetGCCoreBallFire.ID.CODE1_VALUE, 
				NetGCCoreBallFire.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCInitCoreBall msg ) {
		ioClient.writeAndFlush( 
				NetGCInitCoreBall.ID.CODE1_VALUE, 
				NetGCInitCoreBall.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCInitCoreBall msg ) {
		ioClient.writeAndFlush( 
				NetGCInitCoreBall.ID.CODE1_VALUE, 
				NetGCInitCoreBall.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGRemoveBrick msg ) {
		ioClient.writeAndFlush( 
				NetCGRemoveBrick.ID.CODE1_VALUE, 
				NetCGRemoveBrick.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGRemoveBrick msg ) {
		ioClient.writeAndFlush( 
				NetCGRemoveBrick.ID.CODE1_VALUE, 
				NetCGRemoveBrick.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCRemoveBrick msg ) {
		ioClient.writeAndFlush( 
				NetGCRemoveBrick.ID.CODE1_VALUE, 
				NetGCRemoveBrick.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCRemoveBrick msg ) {
		ioClient.writeAndFlush( 
				NetGCRemoveBrick.ID.CODE1_VALUE, 
				NetGCRemoveBrick.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGPopStarClick msg ) {
		ioClient.writeAndFlush( 
				NetCGPopStarClick.ID.CODE1_VALUE, 
				NetCGPopStarClick.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGPopStarClick msg ) {
		ioClient.writeAndFlush( 
				NetCGPopStarClick.ID.CODE1_VALUE, 
				NetCGPopStarClick.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCPopStarClick msg ) {
		ioClient.writeAndFlush( 
				NetGCPopStarClick.ID.CODE1_VALUE, 
				NetGCPopStarClick.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCPopStarClick msg ) {
		ioClient.writeAndFlush( 
				NetGCPopStarClick.ID.CODE1_VALUE, 
				NetGCPopStarClick.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGFeed msg ) {
		ioClient.writeAndFlush( 
				NetCGFeed.ID.CODE1_VALUE, 
				NetCGFeed.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGFeed msg ) {
		ioClient.writeAndFlush( 
				NetCGFeed.ID.CODE1_VALUE, 
				NetCGFeed.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCFeed msg ) {
		ioClient.writeAndFlush( 
				NetGCFeed.ID.CODE1_VALUE, 
				NetGCFeed.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCFeed msg ) {
		ioClient.writeAndFlush( 
				NetGCFeed.ID.CODE1_VALUE, 
				NetGCFeed.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGJoinMPGRoom msg ) {
		ioClient.writeAndFlush( 
				NetCGJoinMPGRoom.ID.CODE1_VALUE, 
				NetCGJoinMPGRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGJoinMPGRoom msg ) {
		ioClient.writeAndFlush( 
				NetCGJoinMPGRoom.ID.CODE1_VALUE, 
				NetCGJoinMPGRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCJoinMPGRoom msg ) {
		ioClient.writeAndFlush( 
				NetGCJoinMPGRoom.ID.CODE1_VALUE, 
				NetGCJoinMPGRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCJoinMPGRoom msg ) {
		ioClient.writeAndFlush( 
				NetGCJoinMPGRoom.ID.CODE1_VALUE, 
				NetGCJoinMPGRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGQuitMPGRoom msg ) {
		ioClient.writeAndFlush( 
				NetCGQuitMPGRoom.ID.CODE1_VALUE, 
				NetCGQuitMPGRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGQuitMPGRoom msg ) {
		ioClient.writeAndFlush( 
				NetCGQuitMPGRoom.ID.CODE1_VALUE, 
				NetCGQuitMPGRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCQuitMPGRoom msg ) {
		ioClient.writeAndFlush( 
				NetGCQuitMPGRoom.ID.CODE1_VALUE, 
				NetGCQuitMPGRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCQuitMPGRoom msg ) {
		ioClient.writeAndFlush( 
				NetGCQuitMPGRoom.ID.CODE1_VALUE, 
				NetGCQuitMPGRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGMPGRoomMSG msg ) {
		ioClient.writeAndFlush( 
				NetCGMPGRoomMSG.ID.CODE1_VALUE, 
				NetCGMPGRoomMSG.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGMPGRoomMSG msg ) {
		ioClient.writeAndFlush( 
				NetCGMPGRoomMSG.ID.CODE1_VALUE, 
				NetCGMPGRoomMSG.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCMPGDuadMSG msg ) {
		ioClient.writeAndFlush( 
				NetGCMPGDuadMSG.ID.CODE1_VALUE, 
				NetGCMPGDuadMSG.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCMPGDuadMSG msg ) {
		ioClient.writeAndFlush( 
				NetGCMPGDuadMSG.ID.CODE1_VALUE, 
				NetGCMPGDuadMSG.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCMPGPlayerOffline msg ) {
		ioClient.writeAndFlush( 
				NetGCMPGPlayerOffline.ID.CODE1_VALUE, 
				NetGCMPGPlayerOffline.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCMPGPlayerOffline msg ) {
		ioClient.writeAndFlush( 
				NetGCMPGPlayerOffline.ID.CODE1_VALUE, 
				NetGCMPGPlayerOffline.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGMPGThrowExpression msg ) {
		ioClient.writeAndFlush( 
				NetCGMPGThrowExpression.ID.CODE1_VALUE, 
				NetCGMPGThrowExpression.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGMPGThrowExpression msg ) {
		ioClient.writeAndFlush( 
				NetCGMPGThrowExpression.ID.CODE1_VALUE, 
				NetCGMPGThrowExpression.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCMPGThrowExpression msg ) {
		ioClient.writeAndFlush( 
				NetGCMPGThrowExpression.ID.CODE1_VALUE, 
				NetGCMPGThrowExpression.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCMPGThrowExpression msg ) {
		ioClient.writeAndFlush( 
				NetGCMPGThrowExpression.ID.CODE1_VALUE, 
				NetGCMPGThrowExpression.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCOtherJoinMPGRoom msg ) {
		ioClient.writeAndFlush( 
				NetGCOtherJoinMPGRoom.ID.CODE1_VALUE, 
				NetGCOtherJoinMPGRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCOtherJoinMPGRoom msg ) {
		ioClient.writeAndFlush( 
				NetGCOtherJoinMPGRoom.ID.CODE1_VALUE, 
				NetGCOtherJoinMPGRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGMPGPlayerOffline msg ) {
		ioClient.writeAndFlush( 
				NetCGMPGPlayerOffline.ID.CODE1_VALUE, 
				NetCGMPGPlayerOffline.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGMPGPlayerOffline msg ) {
		ioClient.writeAndFlush( 
				NetCGMPGPlayerOffline.ID.CODE1_VALUE, 
				NetCGMPGPlayerOffline.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGGraffitiReady msg ) {
		ioClient.writeAndFlush( 
				NetCGGraffitiReady.ID.CODE1_VALUE, 
				NetCGGraffitiReady.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGGraffitiReady msg ) {
		ioClient.writeAndFlush( 
				NetCGGraffitiReady.ID.CODE1_VALUE, 
				NetCGGraffitiReady.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCGraffitiReady msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiReady.ID.CODE1_VALUE, 
				NetGCGraffitiReady.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCGraffitiReady msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiReady.ID.CODE1_VALUE, 
				NetGCGraffitiReady.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCGraffitiCountDown msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiCountDown.ID.CODE1_VALUE, 
				NetGCGraffitiCountDown.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCGraffitiCountDown msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiCountDown.ID.CODE1_VALUE, 
				NetGCGraffitiCountDown.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCGraffitiGameStart msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiGameStart.ID.CODE1_VALUE, 
				NetGCGraffitiGameStart.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCGraffitiGameStart msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiGameStart.ID.CODE1_VALUE, 
				NetGCGraffitiGameStart.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCGraffitiWordsStage msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiWordsStage.ID.CODE1_VALUE, 
				NetGCGraffitiWordsStage.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCGraffitiWordsStage msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiWordsStage.ID.CODE1_VALUE, 
				NetGCGraffitiWordsStage.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCGraffitiUpdateWord msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiUpdateWord.ID.CODE1_VALUE, 
				NetGCGraffitiUpdateWord.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCGraffitiUpdateWord msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiUpdateWord.ID.CODE1_VALUE, 
				NetGCGraffitiUpdateWord.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGGraffitiUpdateWord msg ) {
		ioClient.writeAndFlush( 
				NetCGGraffitiUpdateWord.ID.CODE1_VALUE, 
				NetCGGraffitiUpdateWord.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGGraffitiUpdateWord msg ) {
		ioClient.writeAndFlush( 
				NetCGGraffitiUpdateWord.ID.CODE1_VALUE, 
				NetCGGraffitiUpdateWord.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGGraffitiSelectWord msg ) {
		ioClient.writeAndFlush( 
				NetCGGraffitiSelectWord.ID.CODE1_VALUE, 
				NetCGGraffitiSelectWord.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGGraffitiSelectWord msg ) {
		ioClient.writeAndFlush( 
				NetCGGraffitiSelectWord.ID.CODE1_VALUE, 
				NetCGGraffitiSelectWord.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCGraffitiSelectWords msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiSelectWords.ID.CODE1_VALUE, 
				NetGCGraffitiSelectWords.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCGraffitiSelectWords msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiSelectWords.ID.CODE1_VALUE, 
				NetGCGraffitiSelectWords.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCGraffitiNotifyWords msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiNotifyWords.ID.CODE1_VALUE, 
				NetGCGraffitiNotifyWords.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCGraffitiNotifyWords msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiNotifyWords.ID.CODE1_VALUE, 
				NetGCGraffitiNotifyWords.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGGraffitiGuessWords msg ) {
		ioClient.writeAndFlush( 
				NetCGGraffitiGuessWords.ID.CODE1_VALUE, 
				NetCGGraffitiGuessWords.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGGraffitiGuessWords msg ) {
		ioClient.writeAndFlush( 
				NetCGGraffitiGuessWords.ID.CODE1_VALUE, 
				NetCGGraffitiGuessWords.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCGraffitiGuessWords msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiGuessWords.ID.CODE1_VALUE, 
				NetGCGraffitiGuessWords.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCGraffitiGuessWords msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiGuessWords.ID.CODE1_VALUE, 
				NetGCGraffitiGuessWords.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCGraffitiRound msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiRound.ID.CODE1_VALUE, 
				NetGCGraffitiRound.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCGraffitiRound msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiRound.ID.CODE1_VALUE, 
				NetGCGraffitiRound.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGGraffitiGoodOrBad msg ) {
		ioClient.writeAndFlush( 
				NetCGGraffitiGoodOrBad.ID.CODE1_VALUE, 
				NetCGGraffitiGoodOrBad.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGGraffitiGoodOrBad msg ) {
		ioClient.writeAndFlush( 
				NetCGGraffitiGoodOrBad.ID.CODE1_VALUE, 
				NetCGGraffitiGoodOrBad.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCGraffitiGoodOrBad msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiGoodOrBad.ID.CODE1_VALUE, 
				NetGCGraffitiGoodOrBad.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCGraffitiGoodOrBad msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiGoodOrBad.ID.CODE1_VALUE, 
				NetGCGraffitiGoodOrBad.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCGraffitiRankList msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiRankList.ID.CODE1_VALUE, 
				NetGCGraffitiRankList.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCGraffitiRankList msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiRankList.ID.CODE1_VALUE, 
				NetGCGraffitiRankList.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGGraffitiPenLine msg ) {
		ioClient.writeAndFlush( 
				NetCGGraffitiPenLine.ID.CODE1_VALUE, 
				NetCGGraffitiPenLine.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGGraffitiPenLine msg ) {
		ioClient.writeAndFlush( 
				NetCGGraffitiPenLine.ID.CODE1_VALUE, 
				NetCGGraffitiPenLine.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCGraffitiPenLine msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiPenLine.ID.CODE1_VALUE, 
				NetGCGraffitiPenLine.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCGraffitiPenLine msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiPenLine.ID.CODE1_VALUE, 
				NetGCGraffitiPenLine.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGGraffitiPenUpdate msg ) {
		ioClient.writeAndFlush( 
				NetCGGraffitiPenUpdate.ID.CODE1_VALUE, 
				NetCGGraffitiPenUpdate.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGGraffitiPenUpdate msg ) {
		ioClient.writeAndFlush( 
				NetCGGraffitiPenUpdate.ID.CODE1_VALUE, 
				NetCGGraffitiPenUpdate.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCGraffitiPenUpdate msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiPenUpdate.ID.CODE1_VALUE, 
				NetGCGraffitiPenUpdate.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCGraffitiPenUpdate msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiPenUpdate.ID.CODE1_VALUE, 
				NetGCGraffitiPenUpdate.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCGGraffitiLineController msg ) {
		ioClient.writeAndFlush( 
				NetCGGraffitiLineController.ID.CODE1_VALUE, 
				NetCGGraffitiLineController.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCGGraffitiLineController msg ) {
		ioClient.writeAndFlush( 
				NetCGGraffitiLineController.ID.CODE1_VALUE, 
				NetCGGraffitiLineController.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGCGraffitiLineController msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiLineController.ID.CODE1_VALUE, 
				NetGCGraffitiLineController.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGCGraffitiLineController msg ) {
		ioClient.writeAndFlush( 
				NetGCGraffitiLineController.ID.CODE1_VALUE, 
				NetGCGraffitiLineController.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRGNewRoom msg ) {
		ioClient.writeAndFlush( 
				NetRGNewRoom.ID.CODE1_VALUE, 
				NetRGNewRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRGNewRoom msg ) {
		ioClient.writeAndFlush( 
				NetRGNewRoom.ID.CODE1_VALUE, 
				NetRGNewRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGRNewRoom msg ) {
		ioClient.writeAndFlush( 
				NetGRNewRoom.ID.CODE1_VALUE, 
				NetGRNewRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGRNewRoom msg ) {
		ioClient.writeAndFlush( 
				NetGRNewRoom.ID.CODE1_VALUE, 
				NetGRNewRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGRGameNotifyState msg ) {
		ioClient.writeAndFlush( 
				NetGRGameNotifyState.ID.CODE1_VALUE, 
				NetGRGameNotifyState.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGRGameNotifyState msg ) {
		ioClient.writeAndFlush( 
				NetGRGameNotifyState.ID.CODE1_VALUE, 
				NetGRGameNotifyState.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGRNewGame msg ) {
		ioClient.writeAndFlush( 
				NetGRNewGame.ID.CODE1_VALUE, 
				NetGRNewGame.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGRNewGame msg ) {
		ioClient.writeAndFlush( 
				NetGRNewGame.ID.CODE1_VALUE, 
				NetGRNewGame.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRGNewGame msg ) {
		ioClient.writeAndFlush( 
				NetRGNewGame.ID.CODE1_VALUE, 
				NetRGNewGame.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRGNewGame msg ) {
		ioClient.writeAndFlush( 
				NetRGNewGame.ID.CODE1_VALUE, 
				NetRGNewGame.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGRNotifyBattleResult msg ) {
		ioClient.writeAndFlush( 
				NetGRNotifyBattleResult.ID.CODE1_VALUE, 
				NetGRNotifyBattleResult.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGRNotifyBattleResult msg ) {
		ioClient.writeAndFlush( 
				NetGRNotifyBattleResult.ID.CODE1_VALUE, 
				NetGRNotifyBattleResult.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGRRequestGameData msg ) {
		ioClient.writeAndFlush( 
				NetGRRequestGameData.ID.CODE1_VALUE, 
				NetGRRequestGameData.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGRRequestGameData msg ) {
		ioClient.writeAndFlush( 
				NetGRRequestGameData.ID.CODE1_VALUE, 
				NetGRRequestGameData.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRGCreateGame msg ) {
		ioClient.writeAndFlush( 
				NetRGCreateGame.ID.CODE1_VALUE, 
				NetRGCreateGame.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRGCreateGame msg ) {
		ioClient.writeAndFlush( 
				NetRGCreateGame.ID.CODE1_VALUE, 
				NetRGCreateGame.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGRCreateGame msg ) {
		ioClient.writeAndFlush( 
				NetGRCreateGame.ID.CODE1_VALUE, 
				NetGRCreateGame.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGRCreateGame msg ) {
		ioClient.writeAndFlush( 
				NetGRCreateGame.ID.CODE1_VALUE, 
				NetGRCreateGame.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGRNotifyQuitGame msg ) {
		ioClient.writeAndFlush( 
				NetGRNotifyQuitGame.ID.CODE1_VALUE, 
				NetGRNotifyQuitGame.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGRNotifyQuitGame msg ) {
		ioClient.writeAndFlush( 
				NetGRNotifyQuitGame.ID.CODE1_VALUE, 
				NetGRNotifyQuitGame.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGRNotifyQuitMPGGame msg ) {
		ioClient.writeAndFlush( 
				NetGRNotifyQuitMPGGame.ID.CODE1_VALUE, 
				NetGRNotifyQuitMPGGame.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGRNotifyQuitMPGGame msg ) {
		ioClient.writeAndFlush( 
				NetGRNotifyQuitMPGGame.ID.CODE1_VALUE, 
				NetGRNotifyQuitMPGGame.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetKeepLivePing msg ) {
		ioClient.writeAndFlush( 
				NetKeepLivePing.ID.CODE1_VALUE, 
				NetKeepLivePing.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetKeepLivePing msg ) {
		ioClient.writeAndFlush( 
				NetKeepLivePing.ID.CODE1_VALUE, 
				NetKeepLivePing.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetKeepLivePong msg ) {
		ioClient.writeAndFlush( 
				NetKeepLivePong.ID.CODE1_VALUE, 
				NetKeepLivePong.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetKeepLivePong msg ) {
		ioClient.writeAndFlush( 
				NetKeepLivePong.ID.CODE1_VALUE, 
				NetKeepLivePong.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetReConn msg ) {
		ioClient.writeAndFlush( 
				NetReConn.ID.CODE1_VALUE, 
				NetReConn.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetReConn msg ) {
		ioClient.writeAndFlush( 
				NetReConn.ID.CODE1_VALUE, 
				NetReConn.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetReConnResult msg ) {
		ioClient.writeAndFlush( 
				NetReConnResult.ID.CODE1_VALUE, 
				NetReConnResult.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetReConnResult msg ) {
		ioClient.writeAndFlush( 
				NetReConnResult.ID.CODE1_VALUE, 
				NetReConnResult.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetReConnResultStubTest msg ) {
		ioClient.writeAndFlush( 
				NetReConnResultStubTest.ID.CODE1_VALUE, 
				NetReConnResultStubTest.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetReConnResultStubTest msg ) {
		ioClient.writeAndFlush( 
				NetReConnResultStubTest.ID.CODE1_VALUE, 
				NetReConnResultStubTest.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetCheckTime msg ) {
		ioClient.writeAndFlush( 
				NetCheckTime.ID.CODE1_VALUE, 
				NetCheckTime.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetCheckTime msg ) {
		ioClient.writeAndFlush( 
				NetCheckTime.ID.CODE1_VALUE, 
				NetCheckTime.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetServerStoping msg ) {
		ioClient.writeAndFlush( 
				NetServerStoping.ID.CODE1_VALUE, 
				NetServerStoping.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetServerStoping msg ) {
		ioClient.writeAndFlush( 
				NetServerStoping.ID.CODE1_VALUE, 
				NetServerStoping.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetGraffitiPoint msg ) {
		ioClient.writeAndFlush( 
				NetGraffitiPoint.ID.CODE1_VALUE, 
				NetGraffitiPoint.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetGraffitiPoint msg ) {
		ioClient.writeAndFlush( 
				NetGraffitiPoint.ID.CODE1_VALUE, 
				NetGraffitiPoint.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRobotConnTest msg ) {
		ioClient.writeAndFlush( 
				NetRobotConnTest.ID.CODE1_VALUE, 
				NetRobotConnTest.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRobotConnTest msg ) {
		ioClient.writeAndFlush( 
				NetRobotConnTest.ID.CODE1_VALUE, 
				NetRobotConnTest.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBIGTest msg ) {
		ioClient.writeAndFlush( 
				NetWEBIGTest.ID.CODE1_VALUE, 
				NetWEBIGTest.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBIGTest msg ) {
		ioClient.writeAndFlush( 
				NetWEBIGTest.ID.CODE1_VALUE, 
				NetWEBIGTest.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetIGWEBTest msg ) {
		ioClient.writeAndFlush( 
				NetIGWEBTest.ID.CODE1_VALUE, 
				NetIGWEBTest.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetIGWEBTest msg ) {
		ioClient.writeAndFlush( 
				NetIGWEBTest.ID.CODE1_VALUE, 
				NetIGWEBTest.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBIGPlayerServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetWEBIGPlayerServerInfo.ID.CODE1_VALUE, 
				NetWEBIGPlayerServerInfo.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBIGPlayerServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetWEBIGPlayerServerInfo.ID.CODE1_VALUE, 
				NetWEBIGPlayerServerInfo.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetIGWEBPlayerServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetIGWEBPlayerServerInfo.ID.CODE1_VALUE, 
				NetIGWEBPlayerServerInfo.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetIGWEBPlayerServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetIGWEBPlayerServerInfo.ID.CODE1_VALUE, 
				NetIGWEBPlayerServerInfo.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBIGGameScene msg ) {
		ioClient.writeAndFlush( 
				NetWEBIGGameScene.ID.CODE1_VALUE, 
				NetWEBIGGameScene.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBIGGameScene msg ) {
		ioClient.writeAndFlush( 
				NetWEBIGGameScene.ID.CODE1_VALUE, 
				NetWEBIGGameScene.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetIGWEBGameScene msg ) {
		ioClient.writeAndFlush( 
				NetIGWEBGameScene.ID.CODE1_VALUE, 
				NetIGWEBGameScene.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetIGWEBGameScene msg ) {
		ioClient.writeAndFlush( 
				NetIGWEBGameScene.ID.CODE1_VALUE, 
				NetIGWEBGameScene.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBIGGameSceneGlobalData msg ) {
		ioClient.writeAndFlush( 
				NetWEBIGGameSceneGlobalData.ID.CODE1_VALUE, 
				NetWEBIGGameSceneGlobalData.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBIGGameSceneGlobalData msg ) {
		ioClient.writeAndFlush( 
				NetWEBIGGameSceneGlobalData.ID.CODE1_VALUE, 
				NetWEBIGGameSceneGlobalData.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetIGWEBGameSceneGlobalData msg ) {
		ioClient.writeAndFlush( 
				NetIGWEBGameSceneGlobalData.ID.CODE1_VALUE, 
				NetIGWEBGameSceneGlobalData.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetIGWEBGameSceneGlobalData msg ) {
		ioClient.writeAndFlush( 
				NetIGWEBGameSceneGlobalData.ID.CODE1_VALUE, 
				NetIGWEBGameSceneGlobalData.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBIGServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetWEBIGServerInfo.ID.CODE1_VALUE, 
				NetWEBIGServerInfo.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBIGServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetWEBIGServerInfo.ID.CODE1_VALUE, 
				NetWEBIGServerInfo.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetIGWEBServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetIGWEBServerInfo.ID.CODE1_VALUE, 
				NetIGWEBServerInfo.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetIGWEBServerInfo msg ) {
		ioClient.writeAndFlush( 
				NetIGWEBServerInfo.ID.CODE1_VALUE, 
				NetIGWEBServerInfo.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetWEBIGTiPlayer msg ) {
		ioClient.writeAndFlush( 
				NetWEBIGTiPlayer.ID.CODE1_VALUE, 
				NetWEBIGTiPlayer.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetWEBIGTiPlayer msg ) {
		ioClient.writeAndFlush( 
				NetWEBIGTiPlayer.ID.CODE1_VALUE, 
				NetWEBIGTiPlayer.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetIGWEBTiPlayer msg ) {
		ioClient.writeAndFlush( 
				NetIGWEBTiPlayer.ID.CODE1_VALUE, 
				NetIGWEBTiPlayer.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetIGWEBTiPlayer msg ) {
		ioClient.writeAndFlush( 
				NetIGWEBTiPlayer.ID.CODE1_VALUE, 
				NetIGWEBTiPlayer.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRIGRoomJoinGate msg ) {
		ioClient.writeAndFlush( 
				NetRIGRoomJoinGate.ID.CODE1_VALUE, 
				NetRIGRoomJoinGate.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRIGRoomJoinGate msg ) {
		ioClient.writeAndFlush( 
				NetRIGRoomJoinGate.ID.CODE1_VALUE, 
				NetRIGRoomJoinGate.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetIGRRoomJoinGate msg ) {
		ioClient.writeAndFlush( 
				NetIGRRoomJoinGate.ID.CODE1_VALUE, 
				NetIGRRoomJoinGate.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetIGRRoomJoinGate msg ) {
		ioClient.writeAndFlush( 
				NetIGRRoomJoinGate.ID.CODE1_VALUE, 
				NetIGRRoomJoinGate.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetIGRTryConnRoom msg ) {
		ioClient.writeAndFlush( 
				NetIGRTryConnRoom.ID.CODE1_VALUE, 
				NetIGRTryConnRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetIGRTryConnRoom msg ) {
		ioClient.writeAndFlush( 
				NetIGRTryConnRoom.ID.CODE1_VALUE, 
				NetIGRTryConnRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRIGPlayerJoinRoom msg ) {
		ioClient.writeAndFlush( 
				NetRIGPlayerJoinRoom.ID.CODE1_VALUE, 
				NetRIGPlayerJoinRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRIGPlayerJoinRoom msg ) {
		ioClient.writeAndFlush( 
				NetRIGPlayerJoinRoom.ID.CODE1_VALUE, 
				NetRIGPlayerJoinRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetIGRPlayerJoinRoom msg ) {
		ioClient.writeAndFlush( 
				NetIGRPlayerJoinRoom.ID.CODE1_VALUE, 
				NetIGRPlayerJoinRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetIGRPlayerJoinRoom msg ) {
		ioClient.writeAndFlush( 
				NetIGRPlayerJoinRoom.ID.CODE1_VALUE, 
				NetIGRPlayerJoinRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRIGPlayerQuitRoom msg ) {
		ioClient.writeAndFlush( 
				NetRIGPlayerQuitRoom.ID.CODE1_VALUE, 
				NetRIGPlayerQuitRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRIGPlayerQuitRoom msg ) {
		ioClient.writeAndFlush( 
				NetRIGPlayerQuitRoom.ID.CODE1_VALUE, 
				NetRIGPlayerQuitRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetIGRPlayerQuitRoom msg ) {
		ioClient.writeAndFlush( 
				NetIGRPlayerQuitRoom.ID.CODE1_VALUE, 
				NetIGRPlayerQuitRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetIGRPlayerQuitRoom msg ) {
		ioClient.writeAndFlush( 
				NetIGRPlayerQuitRoom.ID.CODE1_VALUE, 
				NetIGRPlayerQuitRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRIGSendDuadMSG msg ) {
		ioClient.writeAndFlush( 
				NetRIGSendDuadMSG.ID.CODE1_VALUE, 
				NetRIGSendDuadMSG.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRIGSendDuadMSG msg ) {
		ioClient.writeAndFlush( 
				NetRIGSendDuadMSG.ID.CODE1_VALUE, 
				NetRIGSendDuadMSG.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetIGRSendDuadMSG msg ) {
		ioClient.writeAndFlush( 
				NetIGRSendDuadMSG.ID.CODE1_VALUE, 
				NetIGRSendDuadMSG.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetIGRSendDuadMSG msg ) {
		ioClient.writeAndFlush( 
				NetIGRSendDuadMSG.ID.CODE1_VALUE, 
				NetIGRSendDuadMSG.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetIGRSendDuadMSGFail msg ) {
		ioClient.writeAndFlush( 
				NetIGRSendDuadMSGFail.ID.CODE1_VALUE, 
				NetIGRSendDuadMSGFail.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetIGRSendDuadMSGFail msg ) {
		ioClient.writeAndFlush( 
				NetIGRSendDuadMSGFail.ID.CODE1_VALUE, 
				NetIGRSendDuadMSGFail.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRIGCreateGame msg ) {
		ioClient.writeAndFlush( 
				NetRIGCreateGame.ID.CODE1_VALUE, 
				NetRIGCreateGame.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRIGCreateGame msg ) {
		ioClient.writeAndFlush( 
				NetRIGCreateGame.ID.CODE1_VALUE, 
				NetRIGCreateGame.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetIGRCreateGame msg ) {
		ioClient.writeAndFlush( 
				NetIGRCreateGame.ID.CODE1_VALUE, 
				NetIGRCreateGame.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetIGRCreateGame msg ) {
		ioClient.writeAndFlush( 
				NetIGRCreateGame.ID.CODE1_VALUE, 
				NetIGRCreateGame.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRIGPlayerChatMSG msg ) {
		ioClient.writeAndFlush( 
				NetRIGPlayerChatMSG.ID.CODE1_VALUE, 
				NetRIGPlayerChatMSG.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRIGPlayerChatMSG msg ) {
		ioClient.writeAndFlush( 
				NetRIGPlayerChatMSG.ID.CODE1_VALUE, 
				NetRIGPlayerChatMSG.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetIGRPlayerChatMSG msg ) {
		ioClient.writeAndFlush( 
				NetIGRPlayerChatMSG.ID.CODE1_VALUE, 
				NetIGRPlayerChatMSG.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetIGRPlayerChatMSG msg ) {
		ioClient.writeAndFlush( 
				NetIGRPlayerChatMSG.ID.CODE1_VALUE, 
				NetIGRPlayerChatMSG.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRIGGetPlayerState msg ) {
		ioClient.writeAndFlush( 
				NetRIGGetPlayerState.ID.CODE1_VALUE, 
				NetRIGGetPlayerState.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRIGGetPlayerState msg ) {
		ioClient.writeAndFlush( 
				NetRIGGetPlayerState.ID.CODE1_VALUE, 
				NetRIGGetPlayerState.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetIGRGetPlayerState msg ) {
		ioClient.writeAndFlush( 
				NetIGRGetPlayerState.ID.CODE1_VALUE, 
				NetIGRGetPlayerState.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetIGRGetPlayerState msg ) {
		ioClient.writeAndFlush( 
				NetIGRGetPlayerState.ID.CODE1_VALUE, 
				NetIGRGetPlayerState.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRIGPlayerQuitGame msg ) {
		ioClient.writeAndFlush( 
				NetRIGPlayerQuitGame.ID.CODE1_VALUE, 
				NetRIGPlayerQuitGame.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRIGPlayerQuitGame msg ) {
		ioClient.writeAndFlush( 
				NetRIGPlayerQuitGame.ID.CODE1_VALUE, 
				NetRIGPlayerQuitGame.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRIGApplyMPGRoom msg ) {
		ioClient.writeAndFlush( 
				NetRIGApplyMPGRoom.ID.CODE1_VALUE, 
				NetRIGApplyMPGRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRIGApplyMPGRoom msg ) {
		ioClient.writeAndFlush( 
				NetRIGApplyMPGRoom.ID.CODE1_VALUE, 
				NetRIGApplyMPGRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetIGRApplyMPGRoom msg ) {
		ioClient.writeAndFlush( 
				NetIGRApplyMPGRoom.ID.CODE1_VALUE, 
				NetIGRApplyMPGRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetIGRApplyMPGRoom msg ) {
		ioClient.writeAndFlush( 
				NetIGRApplyMPGRoom.ID.CODE1_VALUE, 
				NetIGRApplyMPGRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRIGNewMPGRoom msg ) {
		ioClient.writeAndFlush( 
				NetRIGNewMPGRoom.ID.CODE1_VALUE, 
				NetRIGNewMPGRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRIGNewMPGRoom msg ) {
		ioClient.writeAndFlush( 
				NetRIGNewMPGRoom.ID.CODE1_VALUE, 
				NetRIGNewMPGRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetIGRNewMPGRoom msg ) {
		ioClient.writeAndFlush( 
				NetIGRNewMPGRoom.ID.CODE1_VALUE, 
				NetIGRNewMPGRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetIGRNewMPGRoom msg ) {
		ioClient.writeAndFlush( 
				NetIGRNewMPGRoom.ID.CODE1_VALUE, 
				NetIGRNewMPGRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetRIGGameRequestMPGRoom msg ) {
		ioClient.writeAndFlush( 
				NetRIGGameRequestMPGRoom.ID.CODE1_VALUE, 
				NetRIGGameRequestMPGRoom.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetRIGGameRequestMPGRoom msg ) {
		ioClient.writeAndFlush( 
				NetRIGGameRequestMPGRoom.ID.CODE1_VALUE, 
				NetRIGGameRequestMPGRoom.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

	public static void writeAndFlush( GameTCPIOClient ioClient, long stubID, NetIGRTiPlayer msg ) {
		ioClient.writeAndFlush( 
				NetIGRTiPlayer.ID.CODE1_VALUE, 
				NetIGRTiPlayer.ID.CODE2_VALUE, stubID, msg );
	}
	public static void writeAndFlush( GameTCPIOClient ioClient, NetIGRTiPlayer msg ) {
		ioClient.writeAndFlush( 
				NetIGRTiPlayer.ID.CODE1_VALUE, 
				NetIGRTiPlayer.ID.CODE2_VALUE, ioClient.getStubID(), msg );
	}

}
