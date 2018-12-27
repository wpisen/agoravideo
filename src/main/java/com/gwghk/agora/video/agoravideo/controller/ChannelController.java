package com.gwghk.agora.video.agoravideo.controller;

import com.alibaba.fastjson.JSON;
import com.gwghk.agora.video.agoravideo.base.ApiRespResult;
import com.gwghk.agora.video.agoravideo.base.ApiResultCode;
import com.gwghk.agora.video.agoravideo.model.CommonResqDto;
import com.gwghk.agora.video.agoravideo.model.IdCardOtherSideResqDto;
import com.gwghk.agora.video.agoravideo.model.IdCardPositiveResqDto;
import com.gwghk.agora.video.agoravideo.util.HttpClientUtil;
import com.gwghk.agora.video.agoravideo.util.JsonUtil;
import com.gwghk.agora.video.agoravideo.util.ValidateUtil;
import javafx.beans.binding.BooleanBinding;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 摘要：Channel
 * 
 * @author eva.liu
 * @version 1.0
 * @Date 2018年12月13日
 */
@RestController
public class ChannelController {
    private static final Logger logger = LoggerFactory.getLogger(ChannelController.class);

    /**
     * 通道标示数据
     */
    private static Map<String,Boolean> flatMap = new ConcurrentHashMap<String,Boolean>();


    /**
     * @api {post} /channel/clent/check 1、获取通道开始双录
     * @apiDescription 获取通道开始双录 true 表示没有占用通道可以进入双录  false 表示当前已经占用需要等待
     * @apiGroup 4、Channel
     * @apiName clentCheck
     * @apiVersion 1.0.0
     * @apiSampleRequest /channel/clent/check
     * @apiParam {String} channelNo 通道信息
     * @apiSuccess (成功响应) {String} code 请求返回码 0:成功,其它请参见文档定义
     * @apiSuccess (成功响应) {String} msg  请求返回信息
     * @apiSuccess (成功响应) {Json} data   成功后返回参数 true false
     * @apiSuccessExample {Json} 成功响应示例:
    {
    "code":"0",
    "msg":"success",
    "data":true,
    "ok":true
    }
     * @apiErrorExample {Json} 失败响应示例:
     * {
     * "code": "-1",
     * "msg": "exception",
     * "data": ""
     * }
     */
    @RequestMapping(value = "/channel/clent/check", method = { RequestMethod.POST, RequestMethod.GET })
    public ApiRespResult<Boolean> clentCheck(HttpServletRequest req, String channelNo) {
        try{
            if(channelNo == null){
                return ApiRespResult.error("channelNo 不能为空");
            }
            Boolean flat = flatMap.get(channelNo);
            if(flat != null && flat.equals(true)){
                return ApiRespResult.success(false);
            }else{
                flatMap.put(channelNo,true);
                return ApiRespResult.success(true);
            }
        } catch (Exception e) {
            logger.error("clentCheck is fail...",e);
            return ApiRespResult.error(ApiResultCode.EXCEPTION);
        }
    }

    /**
     * @api {post} /channel/customer/check 2、调度端获取通道状态
     * @apiDescription 调度端获取通道状态 true 表示需要进入并进行双录工作  false 表示当前没有用户等待双录
     * @apiGroup 4、Channel
     * @apiName customerCheck
     * @apiVersion 1.0.0
     * @apiSampleRequest /channel/customer/check
     * @apiParam {String} channelNo 通道信息
     * @apiSuccess (成功响应) {String} code 请求返回码 0:成功,其它请参见文档定义
     * @apiSuccess (成功响应) {String} msg  请求返回信息
     * @apiSuccess (成功响应) {Json} data   成功后返回参数 true false
     * @apiSuccessExample {Json} 成功响应示例:
    {
    "code":"0",
    "msg":"success",
    "data":true,
    "ok":true
    }
     * @apiErrorExample {Json} 失败响应示例:
     * {
     * "code": "-1",
     * "msg": "exception",
     * "data": ""
     * }
     */
    @RequestMapping(value = "/channel/customer/check", method = { RequestMethod.POST, RequestMethod.GET })
    public ApiRespResult<Boolean> customerCheck(HttpServletRequest req, String channelNo) {
        try{
            if(channelNo == null){
                return ApiRespResult.error("channelNo 不能为空");
            }
            Boolean flat = flatMap.get(channelNo);
            if(flat != null && flat.equals(true)){
                return ApiRespResult.success(true);
            }else{
                return ApiRespResult.success(false);
            }
        } catch (Exception e) {
            logger.error("customerCheck is fail...",e);
            return ApiRespResult.error(ApiResultCode.EXCEPTION);
        }
    }

    /**
     * @api {post} /channel/customer/close 3、调度端关闭（结束占用）
     * @apiDescription 调度端结束并关闭录 true 表示关闭成功
     * @apiGroup 4、Channel
     * @apiName customerClose
     * @apiVersion 1.0.0
     * @apiSampleRequest /channel/customer/close
     * @apiParam {String} channelNo 通道信息
     * @apiSuccess (成功响应) {String} code 请求返回码 0:成功,其它请参见文档定义
     * @apiSuccess (成功响应) {String} msg  请求返回信息
     * @apiSuccess (成功响应) {Json} data   成功后返回参数 true false
     * @apiSuccessExample {Json} 成功响应示例:
    {
    "code":"0",
    "msg":"success",
    "data":true,
    "ok":true
    }
     * @apiErrorExample {Json} 失败响应示例:
     * {
     * "code": "-1",
     * "msg": "exception",
     * "data": ""
     * }
     */
    @RequestMapping(value = "/channel/customer/close", method = { RequestMethod.POST, RequestMethod.GET })
    public ApiRespResult<Boolean> customerClose(HttpServletRequest req, String channelNo) {
        try{
            if(channelNo == null){
                return ApiRespResult.error("channelNo 不能为空");
            }
            flatMap.put(channelNo,false);
            return ApiRespResult.success(true);
        } catch (Exception e) {
            logger.error("customerClose is fail...",e);
            return ApiRespResult.error(ApiResultCode.EXCEPTION);
        }
    }

    /**
     * @api {post} /channel/customer/open 4、调度端开启 （开启占用）
     * @apiDescription 调度端结束并关闭录 true 表示关闭成功
     * @apiGroup 4、Channel
     * @apiName customerOpen
     * @apiVersion 1.0.0
     * @apiSampleRequest /channel/customer/open
     * @apiParam {String} channelNo 通道信息
     * @apiSuccess (成功响应) {String} code 请求返回码 0:成功,其它请参见文档定义
     * @apiSuccess (成功响应) {String} msg  请求返回信息
     * @apiSuccess (成功响应) {Json} data   成功后返回参数 true false
     * @apiSuccessExample {Json} 成功响应示例:
    {
    "code":"0",
    "msg":"success",
    "data":true,
    "ok":true
    }
     * @apiErrorExample {Json} 失败响应示例:
     * {
     * "code": "-1",
     * "msg": "exception",
     * "data": ""
     * }
     */
    @RequestMapping(value = "/channel/customer/open", method = { RequestMethod.POST, RequestMethod.GET })
    public ApiRespResult<Boolean> customerOpen(HttpServletRequest req, String channelNo) {
        try{
            if(channelNo == null){
                return ApiRespResult.error("channelNo 不能为空");
            }
            flatMap.put(channelNo,true);
            return ApiRespResult.success(true);
        } catch (Exception e) {
            logger.error("customeroOpen is fail...",e);
            return ApiRespResult.error(ApiResultCode.EXCEPTION);
        }
    }

}
