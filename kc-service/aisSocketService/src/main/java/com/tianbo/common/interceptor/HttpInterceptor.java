package com.tianbo.common.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tianbo.common.exception.GlobalException;
import com.tianbo.common.jwt.TokenManager;
import com.tianbo.common.result.CodeMsg;
import com.tianbo.entity.UserAuth;
import com.tianbo.service.UserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author WangCH
 * @create 2020-06-30 12:05
 */
@Slf4j
@Service
public class HttpInterceptor extends HandlerInterceptorAdapter{

    @Autowired
    UserAuthService userAuthService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //return true;
//        log.info("preHandle");
        String paramToken = request.getParameter("token");
        String headerToken = request.getHeader("token");
        if(StringUtils.isEmpty(paramToken) &&StringUtils.isEmpty(headerToken)) {
            //授权异常
            throw new GlobalException(CodeMsg.AUTH_ERROR);
        }
        String authCode = StringUtils.isEmpty(paramToken)?headerToken:paramToken;
        //todo 根据authToken查询apiToken和授权状态进行判断
        QueryWrapper<UserAuth> queryWrapper = new QueryWrapper<>();
        UserAuth userAuth = userAuthService.getOne(queryWrapper.lambda().eq(UserAuth::getAuthCode, authCode));
        if(userAuth!=null){
            String apiToken = userAuth.getApiToken();
            Boolean authStatus = userAuth.getAuthStatus();
            if(!authStatus){
                //授权异常
                throw new GlobalException(CodeMsg.AUTH_ERROR);
            }
            //验证
            if(!TokenManager.verifyToken(apiToken)){
                //授权异常
                throw new GlobalException(CodeMsg.AUTH_ERROR);
            }else{
                return true;
            }
        }else{
            //授权异常
            throw new GlobalException(CodeMsg.AUTH_ERROR);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
//        log.info("afterCompletion");
        String origin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,token, content-type"); //这里要加上content-type
        return;
    }


}
