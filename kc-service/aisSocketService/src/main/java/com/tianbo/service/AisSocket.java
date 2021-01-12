package com.tianbo.service;

import com.alibaba.fastjson.JSONObject;
import com.tianbo.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AisSocket {

    @Autowired
    private AisBaowen1Service baowen1Service;
    @Autowired
    private AisBaowen4Service baowen4Service;
    @Autowired
    private AisBaowen5Service baowen5Service;
    @Autowired
    private AisBaowen18Service baowen18Service;
    @Autowired
    private AisBaowen19Service baowen19Service;
    @Autowired
    private AisBaowen21Service baowen21Service;
    @Autowired
    private AisBaowen127Service baowen127Service;
    @Autowired
    private AisBaowen241Service baowen241Service;
    @Autowired
    private AisBaowen242Service baowen242Service;

    public void saveMessage(String resD){
//        resD = "{\"ID\":\"1\",\"MMSI\":\"412303450\",\"STATE\":\"15\",\"ROT\":\"-128\",\"SOG\":\"1\"," +
//                "\"LONGITUDE\":\"71835760\",\"LATITUDE\":\"22879899\",\"COG\":\"1313\",\"HEADING\":\"511\"," +
//                "\"TIMESTAMP\":\"54\"} ";
        JSONObject jsonObject = JSONObject.parseObject(resD);
        if("1".equals(jsonObject.getString("ID"))){
            AisBaowen1 baowen1 = JSONObject.parseObject(resD,AisBaowen1.class);
            baowen1.setPid(jsonObject.getIntValue("ID"));
            baowen1Service.save(baowen1);
        }
        if("4".equals(jsonObject.getString("ID"))){
            AisBaowen4 baowen4 = JSONObject.parseObject(resD,AisBaowen4.class);
            baowen4.setPid(jsonObject.getIntValue("ID"));
            baowen4Service.save(baowen4);
        }
        if("5".equals(jsonObject.getString("ID"))){
            AisBaowen5 baowen5 = JSONObject.parseObject(resD,AisBaowen5.class);
            baowen5.setPid(jsonObject.getIntValue("ID"));
            baowen5Service.save(baowen5);
        }
        if("18".equals(jsonObject.getString("ID"))){
            AisBaowen18 baowen18 = JSONObject.parseObject(resD,AisBaowen18.class);
            baowen18.setPid(jsonObject.getIntValue("ID"));
            baowen18Service.save(baowen18);
        }
        if("19".equals(jsonObject.getString("ID"))){
            AisBaowen19 baowen19 = JSONObject.parseObject(resD,AisBaowen19.class);
            baowen19.setPid(jsonObject.getIntValue("ID"));
            baowen19Service.save(baowen19);
        }
        if("21".equals(jsonObject.getString("ID"))){
            AisBaowen21 baowen21 = JSONObject.parseObject(resD,AisBaowen21.class);
            baowen21.setPid(jsonObject.getIntValue("ID"));
            baowen21Service.save(baowen21);
        }
        if("24".equals(jsonObject.getString("ID"))){
            AisBaowen241 baowen241 = JSONObject.parseObject(resD,AisBaowen241.class);
            baowen241.setPid(jsonObject.getIntValue("ID"));
            baowen241Service.save(baowen241);
        }
        if("27".equals(jsonObject.getString("ID"))){
            AisBaowen127 baowen127 = JSONObject.parseObject(resD,AisBaowen127.class);
            baowen127.setPid(jsonObject.getIntValue("ID"));
            baowen127Service.save(baowen127);
        }
    }
}
