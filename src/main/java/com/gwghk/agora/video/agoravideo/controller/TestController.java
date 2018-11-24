package com.gwghk.agora.video.agoravideo.controller;

import javax.servlet.http.HttpServletRequest;

import com.gwghk.agora.video.agoravideo.base.ApiRespResult;
import com.gwghk.agora.video.agoravideo.base.ApiResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
public class TestController {
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	/**
	 * 功能：测试接口是否通
	 */
	@RequestMapping(value = "/test", method = { RequestMethod.POST, RequestMethod.GET })
	public ApiRespResult<Void> test(HttpServletRequest req) {
		logger.info(">>>monitor service is survivaling.....");
		return new ApiRespResult<>(ApiResultCode.SUCCESS);
	}
}
