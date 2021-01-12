package com.tianbo.launcher;

import com.alibaba.fastjson.JSONObject;
import com.tianbo.entity.AisBaowen1;
import com.tianbo.service.*;
import org.kechuang.common.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.net.Socket;

public class SocketThread extends Thread {
    private Socket socket = null;
    private OutputStream os = null;
    private InputStream is = null;

    @Autowired
    private AisSocket aisSocket;

    private String sendMsg = (char)1+"qdtxzx2020"+(char)0+"qdtxzx2020"+(char)0;


    private void fileWrite(String name, String path) {
        FileWriter fw = null;
        try {
            // 如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f = new File(path);
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.write(name);
        // pw.println("追加内容");
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        while (true) {
            try {
//                if (socket == null || socket.isClosed()) {
//                    socket = new Socket("198.12.175.16", 4010); // 连接socket
//                    os = socket.getOutputStream();
//                }
//
//                os = socket.getOutputStream();
//                PrintWriter pw = new PrintWriter(new OutputStreamWriter(os),true);
//                pw.println(sendMsg);
//                pw.flush();
//                System.out.println("登录成功!");
//
//                is = socket.getInputStream();
//                byte[] buffer = new byte[1024];
//                int len = -1;
//                System.out.println("读取数据并写入文件:");
//                String jsonStr = "";
//                while ((len = is.read(buffer)) != -1) {
//                    //写入txt文件
//                    String bu = new String(buffer);
//                    String bu2 = bu.replaceAll("}","}"+"\r\n");
//                    fileWrite(bu2,"d://ais.txt");
//
//                    //写入数据库
//                    System.out.println("<原文>"+bu2);
//                    System.out.println("------------------");
//
//                    String[] arrStr = bu2.split("\r\n");
//                    for(String aStr:arrStr){
//                        if(aStr.contains("{")&&aStr.contains("}")){
//                            System.out.println("<元素>"+aStr);
//                            //todo saveMessage(aStr);
//                        }else if(aStr.contains("{")||aStr.contains("}")){
//                            jsonStr+=(aStr);
//                            System.out.println("<jsonStr>"+jsonStr);
//                            //todo saveMessage(jsonStr);
//                            if(jsonStr.contains("{")&&jsonStr.contains("}")){
//                                jsonStr="";
//                            }
//                        }else{
//                            continue;
//                        }
//
//                    }
//                }
                String resD = "{\"ID\":\"1\",\"MMSI\":\"412303450\",\"STATE\":\"15\",\"ROT\":\"-128\",\"SOG\":\"1\"," +
                        "\"LONGITUDE\":\"71835760\",\"LATITUDE\":\"22879899\",\"COG\":\"1313\",\"HEADING\":\"511\"," +
                        "\"TIMESTAMP\":\"54\"} ";
                aisSocket = (AisSocket) SpringUtil.getBean("aisSocket");
                aisSocket.saveMessage(resD);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    socket.close();
                    is.close();
                    os.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
