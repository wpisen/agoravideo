package com.gwghk.agora.video.agoravideo.controller;

import com.gwghk.agora.video.agoravideo.base.ApiRespResult;
import com.gwghk.agora.video.agoravideo.base.ApiResultCode;
import com.gwghk.agora.video.agoravideo.dto.IntelligentDto;
import com.gwghk.agora.video.agoravideo.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取结果信息
 */
@RestController
@RequestMapping("/result")
public class ResultController {
    protected final static Logger logger = LoggerFactory.getLogger(ResultController.class);

    /**
     * @api {post} /result/get 1、根据通道获取结果信息
     * @apiDescription 获取结果信息
     * @apiGroup group000_result
     * @apiName voice
     * @apiVersion 1.0.0
     * @apiSampleRequest /result/get
     * @apiPermission admin
     * @apiParam {String} channelNo 通道信息
     * @apiSuccess (成功响应) {String} code 请求返回码 0:成功,其它请参见文档定义
     * @apiSuccess (成功响应) {String} msg  请求返回信息
     * @apiSuccess (成功响应) {Json} data   成功后返回参数
     * @apiSuccessExample {Json} 成功响应示例:
     * <p>
     * {
     * "code": "0" ,
     * "msg": "success",
     * "data": {
     * <p>
     * }
     * }
     * @apiErrorExample {Json} 失败响应示例:
     * {
     * "code": "-1",
     * "msg": "exception",
     * "data": ""
     * }
     */

    @RequestMapping(value = "/get")
    public ApiRespResult identity(IntelligentDto dto, String channelNo) {
        try {
            dto.setChannelNo(channelNo);
            logger.debug("identity-->dto={}", dto);
            if (StringUtils.isEmpty(dto.getChannelNo())) {
                return ApiRespResult.error(ApiResultCode.E1);
            }
            return ApiRespResult.success(ValidateUtil.getResult(dto.getChannelNo()));
        } catch (Exception e) {
            return ApiRespResult.error(ApiResultCode.EXCEPTION);
        }
    }


}
