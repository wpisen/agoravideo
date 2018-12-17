package com.gwghk.agora.video.agoravideo.util;

import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSON;

/**
 * 摘要：JSON工具类
 * @author Gavin.guo
 * @version 1.0
 * @Date 2016年9月10日
 */
public class JsonUtil {

    /**
     * 将对象转为JSON字符串
     */
    public static String obj2Str(Object object) {
    	if(null == object){
    		return null;
    	}
        return JSON.toJSONString(object);
    }

    /**
     * 将JSON字符串转换成对象
     * @param jsonStr
     * @param cls
     * @return
     */
    public static <T> T json2Obj(String jsonStr,Class<T> cls) {
    	if(null == jsonStr || "".equals(jsonStr)){
    		return null;
    	}
    	return JSON.parseObject(jsonStr, cls);
    }

    /**
     * 将JSON字符串转成list
     * @param jsonStr
     * @param cls
     * @return
     */
    public static <T> List<T> json2List(String jsonStr,Class<T> cls) {
    	if(null == jsonStr || "".equals(jsonStr)){
    		return null;
    	}
    	return JSON.parseArray(jsonStr, cls);
    }

    /**
     * 将JSON字符串转成map
     */
    @SuppressWarnings("unchecked")
    public static  Map<String,Object> json2Map(String jsonStr) {
    	if(null == jsonStr || "".equals(jsonStr)){
    		return null;
    	}
    	return (Map<String,Object>)JSON.parse(jsonStr);
    }
}
