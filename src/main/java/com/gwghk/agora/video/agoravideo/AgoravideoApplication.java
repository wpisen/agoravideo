package com.gwghk.agora.video.agoravideo;

import com.baidu.aip.speech.AipSpeech;
import com.gwghk.agora.video.agoravideo.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class AgoravideoApplication {
    private static final Logger logger = LoggerFactory.getLogger(AgoravideoApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(AgoravideoApplication.class, args);
        logger.warn("Agoravideo->Agoravideo   start !!!!");
    }

    // 腾讯云 语音识别key begin
    @Value("${appId}")
    public String   appId ;

    @Value("${service.secretId}")
    public String  secretId ;

    @Value("${service.secretKey}")
    public String  secretKey;

    @Value("${bucket.name}")
    public String bucketName;

    @Value("${api.url}")
    public String demoApiUrl;

    // 腾讯云 语音识别key end


    //百度语音识别 key begin
    @Value("${baidu.app.id}")
    public String BAIDU_APP_ID;
    @Value("${baidu.api.key}")
    public String BAIDU_API_KEY;
    @Value("${baidu.secret.key}")
    public String BAIDU_SECRET_KEY;
    //百度语音识别 key end

    /**
     * 初始化 参数信息
     */
    @PostConstruct
    public void initParams() {
        ValidateUtil.appId = appId;
        ValidateUtil.secretId = secretId;
        ValidateUtil.secretKey = secretKey;
        ValidateUtil.bucketName = bucketName;
        ValidateUtil.demoApiUrl = demoApiUrl;
    }


    /**
     * 初始化 百度语音识别 参数信息
     */
    @Bean
    public AipSpeech initBaiduAipSpeech() {
        AipSpeech client = new AipSpeech(BAIDU_APP_ID, BAIDU_API_KEY, BAIDU_SECRET_KEY);
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        return client;
    }
}
