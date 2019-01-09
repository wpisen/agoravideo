package com.gwghk.agora.video.agoravideo.controller;

import com.alibaba.fastjson.JSON;
import com.gwghk.agora.video.agoravideo.base.ApiRespResult;
import com.gwghk.agora.video.agoravideo.base.ApiResultCode;
import com.gwghk.agora.video.agoravideo.model.AliveResqDto;
import com.gwghk.agora.video.agoravideo.model.CommonResqDto;
import com.gwghk.agora.video.agoravideo.model.IdentityResqDto;
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
import java.util.HashMap;
import java.util.Map;

/**
 * 摘要：Check
 * 
 * @author eva.liu
 * @version 1.0
 * @Date 2018年12月13日
 */
@RestController
public class CheckController {
    private static final Logger logger = LoggerFactory.getLogger(CheckController.class);

    /**
     * 身份证ocr识别
     */
    private static String idCard ="C_IDCARD";
    /**
     * 图像人身核实
     */
    private static String identity ="C_IDENTITY";
    /**
     * 图像活体检测
     */
    private static String alive ="C_ALIVE";
    /**
     * 图片地址前缀信息
     */
    private static String imageUrl ="C_IMAGE";


    /**
     * @api {post} /check/identity 1、图像人身核实
     * @apiDescription 图像人身核实 (用户上传照片身份信息核验) 0,1及相似度
     * @apiGroup 2、Check
     * @apiName checkIdentity
     * @apiVersion 1.0.0
     * @apiSampleRequest /check/identity
     * @apiParam {String} channelNo 通道信息
     * @apiParam {String} idcardNumber 证件号
     * @apiParam {String} idcardName 证件名称
     * @apiParam {String} imgUrl 图片地址信息
     * @apiSuccess (成功响应) {String} code 请求返回码 0:成功,其它请参见文档定义
     * @apiSuccess (成功响应) {String} msg  请求返回信息
     * @apiSuccess (成功响应) {Json} data   成功后返回参数
     * @apiSuccess (成功响应) {Json} data.flag   code返回0表示cardType要求验证的结果一致，标记为1
     * @apiSuccess (成功响应) {Json} data.otherData   用户上传的图像与身份证登记照的人脸相似度，取值范围[0,100]，推荐相似度大于 75 时可判断为同一人，可根据具体场景自行调整阈值
     * @apiSuccess (成功响应) {Json} data.resultDetails   具体属性key
     * @apiSuccess (成功响应) {Json} data.resultDetails.similarity   用户上传的图像与身份证登记照的人脸相似度，取值范围[0,100]，推荐相似度大于 75 时可判断为同一人，可根据具体场景自行调整阈值
     * @apiSuccessExample {Json} 成功响应示例:
     * {
     * "code": "0" ,
     * "msg": "success",
     * "data": {
     *         "flag": 0,
     *         "otherData": 1.2,
     *         "resultDetails":{
     *             "similarity":1.2
     *         }
     *      }
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
//            str="{\"data\":{\"similarity\":100.0,\"session_id\":\"\",},\"code\":0,\"message\":\"OK\"}";
            if(StringUtils.isNotBlank(str)){
                Map<String, Object> map = JsonUtil.json2Map(str);
                Object obj = map.get("code");
                if (obj != null) {
                    CommonResqDto commonResqDto = new CommonResqDto();
                    //核实成功，同一人
                    if (obj.equals(0)) {
                        //code返回0表示核实成功，同一人，标记为1
                        commonResqDto.setFlag(1);
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
                        //code返回非0表示核实非同一人，标记为0
                        commonResqDto.setFlag(0);
                        Object message = map.get("message");
                        if (obj != null && StringUtils.isNotBlank(obj.toString())) {
                            result.setCode(obj.toString());
                        }
                        if (message != null && StringUtils.isNotBlank(message.toString())) {
                            result.setMsg(message.toString());
                        }
                    }
                    //ValidateUtil.addResult(identity+"_"+channelNo, commonResqDto);
                    ValidateUtil.addResult(channelNo, identity,commonResqDto);
                    ValidateUtil.addResult(channelNo, imageUrl,imgUrl);
                }
            }
            return result;
        } catch (Exception e) {
            logger.error("checkIdentity is fail...",e);
            return ApiRespResult.error(ApiResultCode.EXCEPTION);
        }        
    }


    /**
     * @api {post} /check/alive 2、图像活体检测
     * @apiDescription 图像活体检测 (人脸静态活体检测) 0,1
     * @apiGroup 2、Check
     * @apiName checkAlive
     * @apiVersion 1.0.0
     * @apiSampleRequest /check/alive
     * @apiParam {String} imgUrl 图片地址信息
     * @apiParam {String} channelNo 通道信息
     * @apiSuccess (成功响应) {String} code 请求返回码 0:成功,其它请参见文档定义
     * @apiSuccess (成功响应) {String} msg  请求返回信息
     * @apiSuccess (成功响应) {Json} data   成功后返回参数
     * @apiSuccess (成功响应) {Json} data.flag   匹配结果信息 ，此接口暂时未用到此字段信息
     * @apiSuccess (成功响应) {Json} data.otherData   其他信息
     * @apiSuccess (成功响应) {Json} data.resultDetails   活体打分信息key
     * @apiSuccess (成功响应) {Json} data.resultDetails.five_point   打分信息
     * @apiSuccess (成功响应) {Json} data.resultDetails.score   活体打分，取值范围 [0,100]，推荐相大于 87 时可判断为活体，可根据具体场景自行调整阈值
     * @apiSuccessExample {Json} 成功响应示例:
    {
        "code":"0",
        "msg":"success",
        "data":{
        "flag":1,
        "otherData":null,
        "resultDetails":{
        "five_point":[
            182
        ],
        "score":0
    }
    },
    "ok":true
    }
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
                    //检测成功
                    if (obj.equals(0)) {
                        //code返回0表示要求验证的结果一致，标记为1
                        commonResqDto.setFlag(1);
                        AliveResqDto aliveResqDto = null;
                        Object dataStr = map.get("data");
                        if (dataStr != null && StringUtils.isNotBlank(dataStr.toString())) {
                            aliveResqDto = JsonUtil.json2Obj(dataStr.toString(), AliveResqDto.class);
                        }
                        commonResqDto.setResultDetails(aliveResqDto);
                        result = ApiRespResult.success(commonResqDto);
                    } else {
                        //code返回非0表示要求验证的结果不一致，标记为0
                        commonResqDto.setFlag(0);
                        Object message = map.get("message");
                        result = new ApiRespResult<CommonResqDto>();
                        if (obj != null && StringUtils.isNotBlank(obj.toString())) {
                            result.setCode(obj.toString());
                        }
                        if (message != null && StringUtils.isNotBlank(message.toString())) {
                            result.setMsg(message.toString());
                        }
                    }
                    //ValidateUtil.addResult(alive+"_"+channelNo, commonResqDto);
                    ValidateUtil.addResult(channelNo, alive, commonResqDto);
                    ValidateUtil.addResult(channelNo, imageUrl,imgUrl);
                }
            }          
            return result;
        } catch (Exception e) {
            logger.error("checkAlive is fail...",e);
            return ApiRespResult.error(ApiResultCode.EXCEPTION);
        }
    }

    

}
