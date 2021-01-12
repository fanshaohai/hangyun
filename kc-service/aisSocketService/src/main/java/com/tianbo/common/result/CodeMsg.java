package com.tianbo.common.result;


public enum CodeMsg {

	//通用异常
	ERROR(500,"请求出错"),
	AUTH_ERROR(200000,"未授权"),
	//天眼查
	SUCCESS(0,"请求成功"),
	FAILURE_DATE(300010,"token过期"),
	TOKEN_ERR(300011, "token错误"),
	COMPANY_ERR(300012,"公司代码错误"),
	MAXDARA_ERR(300013,"单次录入条数不能大于50"),

	NO_DATA(300000,"无数据"),
	REQUEST_FAILED(300001,"请求失败"),
	ACCOUNT_INVALID(300002,"账号失效"),
	ACCOUNT_EXPIRED(300003,"账号过期"),
	ACCESS_FREQUENCY_TOO_FAST(300004,"访问频率过快"),
	PERMISSION_DENIED(300005,"无权限访问此api"),
	INSUFFICIENT_BALANCE(300006, "余额不足"),
	NOT_ENOUGH_TIMES(300007, "剩余次数不足"),
	PARAMETERS_ERROR(300008, "缺少必要参数"),
	ACCOUNT_ERROR(300009, "账号信息有误"),
	URL_ERROR(300010, "URL不存在");
	//业务异常


	private final int code;
	private final String msg;

	CodeMsg(int code, String msg){
		this.code = code;
		this.msg = msg;
	}

	public int getCode(){
		return code;
	}
	public String getMsg(){
		return msg;
	}



}
