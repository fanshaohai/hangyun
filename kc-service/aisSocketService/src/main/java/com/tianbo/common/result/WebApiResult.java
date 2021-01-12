package com.tianbo.common.result;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebApiResult<T> {

	private int code;
	private String msg;
	private T data;

	/**
	 * 成功时候的调用
	 * */
	public static <T> WebApiResult<T> success(T data){
		return new WebApiResult<>(data);
	}

	public static <T> String success1(T data){
		return JSONArray.toJSON(new WebApiResult<>(data)).toString();
	}



	/**
	 * 失败时候的调用
	 * */
	public static <T> WebApiResult<T> error(CodeMsg codeMsg){
		return new WebApiResult<>(codeMsg);
	}

	public static <T> String error1(CodeMsg codeMsg){
		return JSONArray.toJSON(new WebApiResult<>(codeMsg)).toString();
	}

	private WebApiResult(T data) {
		this.code = 0;
		this.msg = "success";
		this.data = data;
	}

	private WebApiResult(CodeMsg cm) {
		if(cm == null) {
			return;
		}
		this.code = cm.getCode();
		this.msg = cm.getMsg();
	}

	public int getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
	public T getData() {
		return data;
	}
}
