package com.gwghk.agora.video.agoravideo.util;

import java.util.HashMap;
import java.util.Map;

import com.gwghk.agora.video.agoravideo.model.CommonResqDto;

public class ValidateUtil {
    public static String   appId = "1258281633";
    
    public static String  secretId = "AKIDnXJ0KDweFOwiV4On0Qty0E8BRhPECbxA";
 
    public static String  secretKey = "yap06HIP1lNySlziX8JNZhgbxDJGwcDh";
 
    public static String bucketName="test-1";
 
    public static String demoApiUrl="http://recognition.image.myqcloud.com"; 

    private static Map<String,CommonResqDto> resultMap = new HashMap<String,CommonResqDto>();
    

    public static void addResult(String key,CommonResqDto value){
        resultMap.put(key, value);
    }
    
    public static  Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("host", "recognition.image.myqcloud.com");
        headers.put("content-type", "application/json");
        String s = "";
        try {
            s = SignUtil.appSign(appId, secretId, secretKey, bucketName, 10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        headers.put("authorization", s);
        return headers;
    }

 
    
}
