package com.gwghk.agora.video.agoravideo.controller;

import com.alibaba.fastjson.JSON;
import com.gwghk.agora.video.agoravideo.base.ApiRespResult;
import com.gwghk.agora.video.agoravideo.base.ApiResultCode;
import com.gwghk.agora.video.agoravideo.model.*;
import com.gwghk.agora.video.agoravideo.util.HttpClientUtil;
import com.gwghk.agora.video.agoravideo.util.JsonUtil;
import com.gwghk.agora.video.agoravideo.util.ValidateUtil;
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

/**
 * 摘要：demo
 * 
 * @author eva.liu
 * @version 1.0
 * @Date 2018年12月13日
 */
@RestController
public class DemoController {
    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);
 
    private static String orcIdCord_no="C_ORCIDCORD_";//身份证ocr识别
    private static String identity_no="C_Identity_";//图像人身核实
    private static String alive_no="C_ALIVE_";//图像活体检测 
    
    /**
     * @api {post} /ocr/idCard 1、身份证ocr识别
     * @apiDescription 身份证ocr识别
     * @apiGroup group000_Intelligent voice
     * @apiName ocrIdCard
     * @apiVersion 1.0.0
     * @apiSampleRequest /ocr/idCard
     * @apiPermission admin
     * @apiHeader {String} Authorization 访问token
     * @apiParam {String} channelNo 通道信息
     * @apiParam {String} imgUrl 图片地址信息
     * @apiParam {String} cardType 0 为识别身份证有照片的一面，1 为识别身份证有国徽的一面；如果未指定，默认为0。
     * @apiSuccess (成功响应) {String} code 请求返回码 0:成功,其它请参见文档定义
     * @apiSuccess (成功响应) {String} msg  请求返回信息
     * @apiSuccess (成功响应) {Json} data   成功后返回参数
     * @apiSuccessExample {Json} 成功响应示例:
     * <p>
     * {
     * "code": "0" ,
     * "msg": "success",
     * "data": {

     * }
     * }
     * @apiErrorExample {Json} 失败响应示例:
     * {
     * "code": "-1",
     * "msg": "exception",
     * "data": ""
     * }
     */
    @RequestMapping(value = "/ocr/idCard", method = { RequestMethod.POST, RequestMethod.GET })
    public ApiRespResult<CommonResqDto> ocrIdCard(HttpServletRequest req, String channelNo,String imgUrl, Integer cardType) {
        try{
            if (StringUtils.isBlank(channelNo) || StringUtils.isBlank(imgUrl)) {
                return ApiRespResult.error(ApiResultCode.E4);
            }
            if (cardType == null) {
                cardType = 0;
            }
            if (!(cardType.equals(0) || cardType.equals(1))) {
                return ApiRespResult.error(ApiResultCode.E4);
            }
            ApiRespResult<CommonResqDto> result = ApiRespResult.error(ApiResultCode.FAIL);
            List<String> imgUrlList = new ArrayList<String>();
//            String[] imgUrlArr = imgUrl.split(",");
//            for (String iUrl : imgUrlArr) {
//                if (StringUtils.isNotBlank(iUrl)) {
//                    imgUrlList.add(iUrl);
//                }
//            }
            imgUrlList.add(imgUrl);//先默认只支持一张图片
            
            Map<String, String> headers = ValidateUtil.getHeaders();
            String url = ValidateUtil.demoApiUrl + "/ocr/idcard";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("appid", ValidateUtil.appId);
            params.put("bucket", ValidateUtil.bucketName);
            params.put("url_list", imgUrlList);
            params.put("card_type", cardType);
            String jsonStr = JSON.toJSON(params).toString();
            String str = HttpClientUtil.doPostWithJson(url, jsonStr, headers);
            if(StringUtils.isNotBlank(str)){
                Map<String, Object> map = JsonUtil.json2Map(str);
                Object obj = map.get("result_list");
                if (obj != null) {
                    CommonResqDto commonResqDto = new CommonResqDto();
                    if(cardType.equals(0)){//为识别身份证有照片的一面
                        List<IdCardPositiveResqDto>  list =  JsonUtil.json2List(obj.toString(), IdCardPositiveResqDto.class);
                        if(list!=null && list.size()>0){
                            IdCardPositiveResqDto idCardResqDto = list.get(0);
                            commonResqDto.setFlag(idCardResqDto.getCode().equals("0")?1:0);//code返回0表示cardType要求验证的结果一致，标记为1
                            commonResqDto.setResultDetails(idCardResqDto);
                        }
                    }else if(cardType.equals(1)){
                        List<IdCardOtherSideResqDto>  list =  JsonUtil.json2List(obj.toString(), IdCardOtherSideResqDto.class);
                        if(list!=null && list.size()>0){
                            IdCardOtherSideResqDto idCardResqDto = list.get(0);
                            commonResqDto.setFlag(idCardResqDto.getCode().equals("0")?1:0);//code返回0表示cardType要求验证的结果一致，标记为1
                            commonResqDto.setResultDetails(idCardResqDto);
                        }
                    }
                    ValidateUtil.addResult(orcIdCord_no+cardType+"_"+channelNo, commonResqDto);
                    ValidateUtil.addResult(channelNo,orcIdCord_no, commonResqDto);
                    result =  ApiRespResult.success(commonResqDto);
                }
            }            
            return result;
        } catch (Exception e) {
            logger.error("ocrIdCard is fail...",e);
            return ApiRespResult.error(ApiResultCode.EXCEPTION);
        }
    }


    /**
     * @api {post} /check/identity 2、图像人身核实
     * @apiDescription 图像人身核实 (用户上传照片身份信息核验) 0,1及相似度
     * @apiGroup group000_Intelligent voice
     * @apiName checkIdentity
     * @apiVersion 1.0.0
     * @apiSampleRequest /check/identity
     * @apiPermission admin
     * @apiHeader {String} Authorization 访问token
     * @apiParam {String} channelNo 通道信息
     * @apiParam {String} idcardNumber 证件号
     * @apiParam {String} idcardName 证件名称
     * @apiParam {String} imgUrl 图片地址信息
     * @apiSuccess (成功响应) {String} code 请求返回码 0:成功,其它请参见文档定义
     * @apiSuccess (成功响应) {String} msg  请求返回信息
     * @apiSuccess (成功响应) {Json} data   成功后返回参数
     * @apiSuccessExample {Json} 成功响应示例:
     * <p>
     * {
     * "code": "0" ,
     * "msg": "success",
     * "data": {

     * }
     * }
     * @apiErrorExample {Json} 失败响应示例:
     * {
     * "code": "-1",
     * "msg": "exception",
     * "data": ""
     * }
     */
    @RequestMapping(value = "/check/identity", method = { RequestMethod.POST, RequestMethod.GET })
    public ApiRespResult<CommonResqDto> checkIdentity(HttpServletRequest req,String channelNo, String idcardNumber, String idcardName, String imgUrl) {
        try{
            if (StringUtils.isBlank(channelNo)|| StringUtils.isBlank(idcardNumber)|| StringUtils.isBlank(idcardName)||StringUtils.isBlank(imgUrl)) {
                return ApiRespResult.error(ApiResultCode.E4);
            }
            ApiRespResult<CommonResqDto> result = ApiRespResult.error(ApiResultCode.FAIL);
            Map<String, String> headers = ValidateUtil.getHeaders();
            String url = ValidateUtil.demoApiUrl + "/face/idcardcompare";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("appid", ValidateUtil.appId);
            params.put("url", imgUrl);
            params.put("idcard_number", idcardNumber);
            params.put("idcard_name", idcardName);
            String jsonStr = JSON.toJSON(params).toString();
            String str = HttpClientUtil.doPostWithJson(url, jsonStr, headers);
            str="{\"data\":{\"similarity\":100.0,\"session_id\":\"\",},\"code\":0,\"message\":\"OK\"}";
            if(StringUtils.isNotBlank(str)){
                Map<String, Object> map = JsonUtil.json2Map(str);
                Object obj = map.get("code");
                if (obj != null) {
                    CommonResqDto commonResqDto = new CommonResqDto();
                    if (obj.equals(0)) {//核实成功，同一人
                        commonResqDto.setFlag(1);//code返回0表示核实成功，同一人，标记为1
                        IdentityResqDto identityResqDto = null;
                        Object dataStr = map.get("data");
                        if (dataStr != null && StringUtils.isNotBlank(dataStr.toString())) {
                            identityResqDto = JsonUtil.json2Obj(dataStr.toString(), IdentityResqDto.class);
                        }
                        if(identityResqDto!=null){
                            commonResqDto.setOtherData(identityResqDto.getSimilarity());
                            commonResqDto.setResultDetails(identityResqDto);
                        }
                        result = ApiRespResult.success(commonResqDto);
                    } else {
                        commonResqDto.setFlag(0);//code返回非0表示核实非同一人，标记为0
                        Object message = map.get("message");
                        if (obj != null && StringUtils.isNotBlank(obj.toString())) {
                            result.setCode(obj.toString());
                        }
                        if (message != null && StringUtils.isNotBlank(message.toString())) {
                            result.setMsg(message.toString());
                        }
                    }
                    ValidateUtil.addResult(identity_no+"_"+channelNo, commonResqDto);
                    ValidateUtil.addResult(channelNo, identity_no,commonResqDto);
                }
            }
            return result;
        } catch (Exception e) {
            logger.error("checkIdentity is fail...",e);
            return ApiRespResult.error(ApiResultCode.EXCEPTION);
        }        
    }


    /**
     * @api {post} /check/alive 3、图像活体检测
     * @apiDescription 图像活体检测 (人脸静态活体检测) 0,1
     * @apiGroup group000_Intelligent voice
     * @apiName checkAlive
     * @apiVersion 1.0.0
     * @apiSampleRequest /check/alive
     * @apiPermission admin
     * @apiHeader {String} Authorization 访问token
     * @apiParam {String} imgUrl 图片地址信息
     * @apiParam {String} channelNo 通道信息
     * @apiSuccess (成功响应) {String} code 请求返回码 0:成功,其它请参见文档定义
     * @apiSuccess (成功响应) {String} msg  请求返回信息
     * @apiSuccess (成功响应) {Json} data   成功后返回参数
     * @apiSuccess (成功响应) {Json} data.flag   匹配结果信息 ，此接口暂时未用到此字段信息
     * @apiSuccess (成功响应) {Json} data.resultDetails   语音地址信息
     * @apiSuccessExample {Json} 成功响应示例:
     * <p>
     * {
     * "code": "0" ,
     * "msg": "success",
     * "data": {

     * }
     * }
     * @apiErrorExample {Json} 失败响应示例:
     * {
     * "code": "-1",
     * "msg": "exception",
     * "data": ""
     * }
     */
    @RequestMapping(value = "/check/alive", method = { RequestMethod.POST, RequestMethod.GET })
    public ApiRespResult<CommonResqDto> checkAlive(HttpServletRequest req,String channelNo, String imgUrl) {
        try {
            if (StringUtils.isBlank(channelNo) || StringUtils.isBlank(imgUrl)) {
                return ApiRespResult.error(ApiResultCode.E4);
            }
            ApiRespResult<CommonResqDto> result = ApiRespResult.error(ApiResultCode.FAIL);
            Map<String, String> headers = ValidateUtil.getHeaders();
            String url = ValidateUtil.demoApiUrl + "/face/livedetectpicture";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("appid", ValidateUtil.appId);
            params.put("url", imgUrl);
            String jsonStr = JSON.toJSON(params).toString();
            String str = HttpClientUtil.doPostWithJson(url, jsonStr, headers);
            if(StringUtils.isNotBlank(str)){
                Map<String, Object> map = JsonUtil.json2Map(str);
                Object obj = map.get("code");
                if (obj != null) {
                    CommonResqDto commonResqDto = new CommonResqDto();
                    if (obj.equals(0)) {//检测成功
                        commonResqDto.setFlag(1);//code返回0表示要求验证的结果一致，标记为1
                        AliveResqDto aliveResqDto = null;
                        Object dataStr = map.get("data");
                        if (dataStr != null && StringUtils.isNotBlank(dataStr.toString())) {
                            aliveResqDto = JsonUtil.json2Obj(dataStr.toString(), AliveResqDto.class);
                        }
                        commonResqDto.setResultDetails(aliveResqDto);
                        result = ApiRespResult.success(commonResqDto);
                    } else {
                        commonResqDto.setFlag(0);//code返回非0表示要求验证的结果不一致，标记为0
                        Object message = map.get("message");
                        result = new ApiRespResult<CommonResqDto>();
                        if (obj != null && StringUtils.isNotBlank(obj.toString())) {
                            result.setCode(obj.toString());
                        }
                        if (message != null && StringUtils.isNotBlank(message.toString())) {
                            result.setMsg(message.toString());
                        }
                    }
                    ValidateUtil.addResult(alive_no+"_"+channelNo, commonResqDto);
                    ValidateUtil.addResult(channelNo,alive_no, commonResqDto);
                }
            }          
            return result;
        } catch (Exception e) {
            logger.error("checkAlive is fail...",e);
            return ApiRespResult.error(ApiResultCode.EXCEPTION);
        }
    }

    

}
