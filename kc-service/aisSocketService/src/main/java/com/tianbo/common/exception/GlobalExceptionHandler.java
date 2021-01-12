package com.tianbo.common.exception;

/**
 * @author WangCH
 * @create 2020-06-29 15:51
 */

import com.tianbo.common.result.CodeMsg;
import com.tianbo.common.result.WebApiResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public WebApiResult<String> exceptionHandler(HttpServletRequest request, Exception e){
        e.printStackTrace();
        //天眼查异常通知
        if(e instanceof GlobalException) {
            GlobalException ex = (GlobalException)e;
            return WebApiResult.error(ex.getMsg());
        }else{
            //请求失败
            return WebApiResult.error(CodeMsg.ERROR);
        }
    }
}
