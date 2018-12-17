package com.gwghk.agora.video.agoravideo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AgoravideoApplication {
    private static final Logger logger = LoggerFactory.getLogger(AgoravideoApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(AgoravideoApplication.class, args);
        logger.warn("Agoravideo->Agoravideo   start !!!!");
    }
}
