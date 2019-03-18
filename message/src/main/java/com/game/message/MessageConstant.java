package com.game.message;

import com.game.message.proto.ProtoContext_Common.TFResult;

public final class MessageConstant {
	
	private MessageConstant() {
		
	}
	
	public static final TFResult TFRESULT_TRUE = TFResult.newBuilder().setResult(true).build();
	
	public static final TFResult TFRESULT_FALSE = TFResult.newBuilder().setResult(false).build();
	
	public static final TFResult FAIL_0 = TFResult.newBuilder(TFRESULT_FALSE).setCode(0).build();

	public static final TFResult FAIL_1 = TFResult.newBuilder(TFRESULT_FALSE).setCode(1).build();

	public static final TFResult FAIL_2 = TFResult.newBuilder(TFRESULT_FALSE).setCode(2).build();

	public static final TFResult FAIL_3 = TFResult.newBuilder(TFRESULT_FALSE).setCode(3).build();

	public static final TFResult FAIL_4 = TFResult.newBuilder(TFRESULT_FALSE).setCode(4).build();

	public static final TFResult FAIL_5 = TFResult.newBuilder(TFRESULT_FALSE).setCode(5).build();

	public static final TFResult FAIL_6 = TFResult.newBuilder(TFRESULT_FALSE).setCode(6).build();

	public static final TFResult FAIL_7 = TFResult.newBuilder(TFRESULT_FALSE).setCode(7).build();

	public static final TFResult FAIL_8 = TFResult.newBuilder(TFRESULT_FALSE).setCode(8).build();

	public static final TFResult FAIL_9 = TFResult.newBuilder(TFRESULT_FALSE).setCode(9).build();
	
	
	public static final TFResult TRUE_0 = TFResult.newBuilder(TFRESULT_TRUE).setCode(0).build();

	public static final TFResult TRUE_1 = TFResult.newBuilder(TFRESULT_TRUE).setCode(1).build();

	public static final TFResult TRUE_2 = TFResult.newBuilder(TFRESULT_TRUE).setCode(2).build();

	public static final TFResult TRUE_3 = TFResult.newBuilder(TFRESULT_TRUE).setCode(3).build();

	public static final TFResult TRUE_4 = TFResult.newBuilder(TFRESULT_TRUE).setCode(4).build();

	public static final TFResult TRUE_5 = TFResult.newBuilder(TFRESULT_TRUE).setCode(5).build();

	public static final TFResult TRUE_6 = TFResult.newBuilder(TFRESULT_TRUE).setCode(6).build();

	public static final TFResult TRUE_7 = TFResult.newBuilder(TFRESULT_TRUE).setCode(7).build();

	public static final TFResult TRUE_8 = TFResult.newBuilder(TFRESULT_TRUE).setCode(8).build();

	public static final TFResult TRUE_9 = TFResult.newBuilder(TFRESULT_TRUE).setCode(9).build();


	
	public static TFResult.Builder createTFResultFalseBuilder() {
		return TFResult.newBuilder().setResult(false);
	}
}
