package com.tianbo.common.exception;

import com.tianbo.common.result.CodeMsg;
import org.springframework.stereotype.Service;

@Service
public class ThrowExceptionService {


    /**
     * 模拟处理异常信息
     */
    public <T> T doThrowEsxception(T data){

        Integer resultCode = 30000;
        if(resultCode.equals(0)){
            return data;
        }else{
            switch (resultCode) {
                case 30000:
                    throw new GlobalException(CodeMsg.NO_DATA);
                case 300001:
                    throw new GlobalException(CodeMsg.REQUEST_FAILED);
                case 300002:
                    throw new GlobalException(CodeMsg.ACCOUNT_INVALID);
                case 300003:
                    throw new GlobalException(CodeMsg.ACCOUNT_EXPIRED);
                case 300004:
                    throw new GlobalException(CodeMsg.ACCESS_FREQUENCY_TOO_FAST);
                case 300005:
                    throw new GlobalException(CodeMsg.PERMISSION_DENIED);
                case 300006:
                    throw new GlobalException(CodeMsg.INSUFFICIENT_BALANCE);
                case 300007:
                    throw new GlobalException(CodeMsg.NOT_ENOUGH_TIMES);
                case 300008:
                    throw new GlobalException(CodeMsg.PARAMETERS_ERROR);
                case 300009:
                    throw new GlobalException(CodeMsg.ACCOUNT_ERROR);
                case 300010:
                    throw new GlobalException(CodeMsg.URL_ERROR);
                default:
                    throw new GlobalException(CodeMsg.ERROR);
            }
        }
    }

}
