package com.gwghk.agora.video.agoravideo.dto;

import sun.misc.BASE64Decoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.TreeMap;

/**
 * 签名工具类
 */

public class SignatureUtil {

    /**
     * 获取签名信息
     * @param params 签名字段信息
     * @param key 签名秘钥
     * @param type 加密方式
     * @return 返回签名字符串信息
     * @throws Exception
     */
    public static String sign(String params, String key, String type) throws Exception {
        Mac mac = Mac.getInstance(type);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), mac.getAlgorithm());
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(params.getBytes("UTF-8"));
        return DatatypeConverter.printBase64Binary(hash);
    }

    /**
     * 拼接签名所需字符串信息
     * @param method 请求方式
     * @param endpoint 接入点信息
     * @param params 参数
     * @return 返回拼接后的字符串信息
     */
    public static String getStringToSign(String method, String endpoint, TreeMap<String, Object> params) {
        StringBuilder s2s = new StringBuilder();
        s2s.append(method).append(endpoint).append("/?");
        // 签名时要求对参数进行字典排序，此处用TreeMap保证顺序
        for (String k : params.keySet()) {
            s2s.append(k).append("=").append(params.get(k).toString()).append("&");
        }
        return s2s.toString().substring(0, s2s.length() - 1);
    }

    /**
     * 拼接请求所需字符串信息
     * @param method 请求方式
     * @param endpoint 接入点信息
     * @param secretKey 秘钥信息
     * @param type 加密方式
     * @param params 参数
     * @return 返回拼接后的字符串信息
     */
    public static String getSendParams(String method, String endpoint,String secretKey,String type, TreeMap<String, Object> params) throws Exception {
        StringBuilder s2s = new StringBuilder();
        params.put("Signature",URLEncoder.encode(sign(getStringToSign(method,endpoint,params),secretKey,type),"GBK"));
        for (String k : params.keySet()) {
            s2s.append(k).append("=").append(params.get(k).toString()).append("&");
        }
        return s2s.toString().substring(0, s2s.length() - 1);
    }


    /**
     * 发送请求
     * @param url 请求地址
     * @param param 参数信息
     * @return 返回请求结果信息
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64音频文件转存
     * @param base64Code  base64音频文件
     * @param targetPath 文件存放文件信息
     * @throws Exception
     */
    public static void decoderBase64File(String base64Code, String targetPath) throws Exception {
        FileOutputStream out = null;
        try {
            byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
            out = new FileOutputStream(targetPath);
            out.write(buffer);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            if(null!=out){
                out.close();
            }
        }
    }

}


