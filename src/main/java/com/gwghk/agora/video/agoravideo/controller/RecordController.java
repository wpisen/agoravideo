package com.gwghk.agora.video.agoravideo.controller;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import com.gwghk.agora.video.agoravideo.base.ApiRespResult;
import com.gwghk.agora.video.agoravideo.base.ApiResultCode;

import io.agora.recording.RecordingSDK;
import io.agora.recording.test.RecordingSample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 摘要：验证项目是否运行中
 * 
 * @author Greet.guo
 * @version 1.0
 * @Date 2017年10月31日
 */
@RestController
@RequestMapping(value = "/service")
public class RecordController {
	private static final Logger logger = LoggerFactory.getLogger(RecordController.class);
	private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100);
	private static ConcurrentHashMap<String,RecordingSample> redordingMap = new ConcurrentHashMap<String,RecordingSample>();
	/**
	 * 功能：测试接口是否通
	 */
	@RequestMapping(value = "/startRecord", method = { RequestMethod.POST, RequestMethod.GET })
	public ApiRespResult<Void> test(HttpServletRequest req,@RequestParam String channel) {
		if(StringUtils.isEmpty(channel)) {
			return ApiRespResult.error("channel 不能为空");
		}
	    String[] args = {"--appId","eab1f86c1f8f46f584c3bb70daee1241","--uid","0","--channel",channel,"--appliteDir","/data/agora/files","--channelProfile","0","--isMixingEnabled","1","--mixedVideoAudio","1"};
	    fixedThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				RecordingSDK RecordingSdk = new RecordingSDK();
				RecordingSample ars = new RecordingSample(RecordingSdk);
				redordingMap.put(channel, ars);
			    ars.createChannel(args);
			    ars.unRegister();
			}
		});
		return new ApiRespResult<>(ApiResultCode.SUCCESS);
	}
	
	/**
	 * 功能：测试接口是否通
	 */
	@RequestMapping(value = "/stopRecord", method = { RequestMethod.POST, RequestMethod.GET })
	public ApiRespResult<Void> stopRecord(HttpServletRequest req,@RequestParam String channel,@RequestParam String type) {
		if(StringUtils.isEmpty(channel)) {
			return ApiRespResult.error("channel 不能为空");
		}
		if(redordingMap.get(channel) != null) {
			RecordingSample ars = redordingMap.get(channel);
			ars.leaveChannel();
		}
		return new ApiRespResult<>(ApiResultCode.SUCCESS);
	}
}
