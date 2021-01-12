package com.tianbo.common.exception;


import com.tianbo.common.result.CodeMsg;

/**
 * 天眼查业务异常
 * @author WangCH
 * @create 2020-06-29 15:51
 */
public class GlobalException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private CodeMsg msg;


    public GlobalException(CodeMsg msg) {
        super(msg.toString());
        this.msg = msg;
    }


    public CodeMsg getMsg() {
        return msg;
    }

}
