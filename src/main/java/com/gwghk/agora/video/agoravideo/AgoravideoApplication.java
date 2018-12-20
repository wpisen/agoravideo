package com.gwghk.agora.video.agoravideo;

import com.gwghk.agora.video.agoravideo.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class AgoravideoApplication {
    private static final Logger logger = LoggerFactory.getLogger(AgoravideoApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(AgoravideoApplication.class, args);
        logger.warn("Agoravideo->Agoravideo   start !!!!");
    }

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
}
