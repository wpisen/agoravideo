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
 * @author Greet.guo
 * @version 1.0
 * @Date 2017年10月31日
 */
@RestController
public class RecordController {
	private static final Logger logger = LoggerFactory.getLogger(RecordController.class);
	private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100);
	private static ConcurrentHashMap<String,RecordingSample> redordingMap = new ConcurrentHashMap<String,RecordingSample>();

	/**
	 * @api {post} /service/startRecord 1、开启录制服务
	 * @apiDescription 开启录制服务
	 * @apiGroup 5、Service
	 * @apiName startRecord
	 * @apiVersion 1.0.0
	 * @apiSampleRequest /service/startRecord
	 * @apiParam {String} channelNo 通道信息
	 * @apiSuccess (成功响应) {String} code 请求返回码 0:成功,其它请参见文档定义
	 * @apiSuccess (成功响应) {String} msg  请求返回信息
	 * @apiSuccess (成功响应) {Json} data   成功后返回参数
	 * @apiSuccessExample {Json} 成功响应示例:
	{
	"code":"0",
	"msg":"success",
	"data":{},
	"ok":true
	}
	 * @apiErrorExample {Json} 失败响应示例:
	 * {
	 * "code": "-1",
	 * "msg": "exception",
	 * "data": ""
	 * }
	 */
	@RequestMapping(value = "/service/startRecord", method = { RequestMethod.POST, RequestMethod.GET })
	public ApiRespResult<Void> startRecord(HttpServletRequest req,@RequestParam String channelNo) {
		if(StringUtils.isEmpty(channelNo)) {
			return ApiRespResult.error("channel 不能为空");
		}

		System.out.println("startRecord :" + System.currentTimeMillis() + "; channel :" + channelNo);
	    String[] args = {"--appId","eab1f86c1f8f46f584c3bb70daee1241","--uid","0","--channel",channelNo,"--appliteDir","/usr/local/agora/","-recordFileRootDir","/data/webroot/files/","--channelProfile","0","--isMixingEnabled","1","--mixedVideoAudio","1"};
	    fixedThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				RecordingSDK RecordingSdk = new RecordingSDK();
				RecordingSample ars = new RecordingSample(RecordingSdk);
				redordingMap.put(channelNo, ars);
			    ars.createChannel(args);
			    ars.unRegister();
			}
		});
		return new ApiRespResult<>(ApiResultCode.SUCCESS);
	}

	/**
	 * @api {post} /service/stopRecord 2、关闭录制服务
	 * @apiDescription 开启录制服务
	 * @apiGroup 5、Service
	 * @apiName stopRecord
	 * @apiVersion 1.0.0
	 * @apiSampleRequest /service/stopRecord
	 * @apiParam {String} channelNo 通道信息
	 * @apiSuccess (成功响应) {String} code 请求返回码 0:成功,其它请参见文档定义
	 * @apiSuccess (成功响应) {String} msg  请求返回信息
	 * @apiSuccess (成功响应) {Json} data   成功后返回参数
	 * @apiSuccessExample {Json} 成功响应示例:
	{
	"code":"0",
	"msg":"success",
	"data":{},
	"ok":true
	}
	 * @apiErrorExample {Json} 失败响应示例:
	 * {
	 * "code": "-1",
	 * "msg": "exception",
	 * "data": ""
	 * }
	 */
	@RequestMapping(value = "/service/stopRecord", method = { RequestMethod.POST, RequestMethod.GET })
	public ApiRespResult<Void> stopRecord(HttpServletRequest req,@RequestParam String channelNo,@RequestParam String type) {
		if(StringUtils.isEmpty(channelNo)) {
			return ApiRespResult.error("channel 不能为空");
		}

		System.out.println("stopRecord :" + System.currentTimeMillis() + "; channelNo :" + channelNo);
		if(redordingMap.get(channelNo) != null) {
			RecordingSample ars = redordingMap.get(channelNo);
			ars.leaveChannel();
		}
		return new ApiRespResult<>(ApiResultCode.SUCCESS);
	}
}
