package com.tianbo.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
//import com.tianbo.common.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TokenManager {

    /**
     * 私钥
     */
    private static final String SECRET = "Axmk89Li3Aji9M";

    /**
     * 过期时间1分钟
     */
    private static final int expiresTime = 60000;

    /**
     * 测试
     * @param args
     */
//    public static void main(String[] args) {
//
//        //创建数据库存储auth_token
//        Long userId = 1L;
//        String authToken = MD5Util.inputToForm(userId.toString());
//        System.out.println(authToken);
//        //7530cb5d4b4a31aed32237d6bd7ee975
//        //创建接口token
//        String apiToken = createToken(authToken);
//        //eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJDbGllbnQiLCJ1c2VyVG9rZW4iOiI3NTMwY2I1ZDRiNGEzMWFlZDMyMjM3ZDZiZDdlZTk3NSIsImlzcyI6IlNlcnZpY2UiLCJpYXQiOjE1OTM1MTAzMjR9.fD0_EPgoKdN_nL1FAB9Gms1_sIVBLCiNDQB5LQe5oGY
//        System.out.println(apiToken);
//        //验证
//        Boolean flag = verifyToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJDbGllbnQiLCJ1c2VyVG9rZW4iOiI3NTMwY2I1ZDRiNGEzMWFlZDMyMjM3ZDZiZDdlZTk3NSIsImlzcyI6IlNlcnZpY2UiLCJpYXQiOjE1OTM1MTAzMjR9.fD0_EPgoKdN_nL1FAB9Gms1_sIVBLCiNDQB5LQe5oGY");
//        System.out.println(flag);
//    }
    /**
     * 创建token
     * @param userToken
     * @return
     */
    public static String createToken(String userToken){
        //获取加上过期时间后的时间
        Date nowDate = new Date();
        //System.out.println(nowDate);
        Date expiresDate = new Date(System.currentTimeMillis() + expiresTime);
        Map<String,Object> map = new HashMap<>(20);
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create().withHeader(map)	//请求头
                .withClaim("iss", "Service")	//签发方
                .withClaim("aud", "Client")		//接收方
                .withClaim("userToken", null==userToken?null:userToken) //存储信息，用户ID
                .withIssuedAt(nowDate)		//当前时间
                //.withExpiresAt(expiresDate)		//过期时间
                .sign(Algorithm.HMAC256(SECRET));		//私钥
        return token;
    }

    /**
     * 验证token
     * @param token
     * @return
     */
    public static boolean verifyToken(String token){
        try{
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            verifier.verify(token);
            return true;
        }catch(Exception e){
//            log.error(e.getMessage(), e);
            return false;
        }
    }


}
