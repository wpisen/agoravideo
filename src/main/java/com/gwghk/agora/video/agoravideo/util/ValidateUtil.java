package com.gwghk.agora.video.agoravideo.util;

import com.gwghk.agora.video.agoravideo.model.CommonResqDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ValidateUtil {
    @Value("${appId}")
    public static String   appId ;//= "1258281633";

    @Value("${service.secretId}")
    public static String  secretId ;//= "AKIDnXJ0KDweFOwiV4On0Qty0E8BRhPECbxA";

    @Value("${service.secretKey}")
    public static String  secretKey;// = "yap06HIP1lNySlziX8JNZhgbxDJGwcDh";

    @Value("${bucket.name}")
    public static String bucketName;//="test-1";

    @Value("${api.url}")
    public static String demoApiUrl;//="http://recognition.image.myqcloud.com";

    private static Map<String,CommonResqDto> resultMap = new HashMap<String,CommonResqDto>();


    private static Map<String,Map<String,Object>> finishResultMap = new ConcurrentHashMap<>();

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

    /**
     * 将认证信息存放于内存中，便于后期使用
     * @param key 通道key
     * @param type 类型
     * @param value value
     */
    public static void addResult(String key,String type,CommonResqDto value){
        Map<String,Object> tMap = finishResultMap.containsKey(key) ? finishResultMap.get(key) : new HashMap();
        tMap.put(type,value);
        finishResultMap.put(key, tMap);
    }

    /**
     * 获取结果值信息
     * @param key 通道信息
     * @return 返回通道对于的所有认证信息
     */

    public static Object getResult(String key){
        return  finishResultMap.get(key);
    }
}
