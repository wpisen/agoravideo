package com.gwghk.agora.video.agoravideo.controller;

import com.alibaba.fastjson.JSONObject;
import com.gwghk.agora.video.agoravideo.base.ApiRespResult;
import com.gwghk.agora.video.agoravideo.base.ApiResultCode;
import com.gwghk.agora.video.agoravideo.util.Setting;
import com.gwghk.agora.video.agoravideo.dto.IntelligentDto;
import com.gwghk.agora.video.agoravideo.util.SignatureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;

/**
 * 语音识别接口
 */
@RestController
@RequestMapping("/voice")
public class IntelligentController {
    protected final static Logger logger = LoggerFactory.getLogger(IntelligentController.class);

    /**
     * region,在控制台申请
     */
    @Value("${service.region}")
    private String region;
    /**
     * 服务提供商
     */
    @Value("${service.provider}")
    private String serviceProvider;

    /**
     * secretId,在控制台申请
     */
    @Value("${service.secretId}")
    private String secretId;

    /**
     * secretKey,在控制台申请
     */
    @Value("${service.secretKey}")
    private String secretKey;
    /**
     * 服务接入地域
     */
    @Value("${service.endpoint}")
    private String serviceEndpoint;

    /**
     * 服务请求地址
     */
    @Value("${service.address}")
    private String serviceAddress;
    /**
     * 加密方式
     */
    @Value("${service.sign}")
    private String serviceSign;
    /**
     * 文件存放位置
     */
    @Value("${file.storePath}")
    private String fileStorePath;
    /**
     * 外网访问地址
     */
    @Value("${file.accessPath}")
    private String fileAccessPath;

    /**
     * 语音结果匹配词语
     */
    @Value("${matching.word}")
    private String matchingWord;


    /**
     * @api {post} /voice/asr 1、语音识别接口
     * @apiDescription 文件上传
     * @apiGroup group000_voice
     * @apiName voice
     * @apiVersion 1.0.0
     * @apiSampleRequest /voice/asr
     * @apiPermission admin
     * @apiHeader {String} Url 音频地址信息
     * @apiParam {String} voiceFormat 音频格式信息
     * @apiSuccess (成功响应) {String} code 请求返回码 0:成功,其它请参见文档定义
     * @apiSuccess (成功响应) {String} msg  请求返回信息
     * @apiSuccess (成功响应) {Json} data   上传成功后返回参数
     * @apiSuccess (成功响应) {String} data.accessUrl     上传成功后文件访问路径
     * @apiSuccess (成功响应) {String} data.storageName   上传成功后服务器存储的文件名
     * @apiSuccess (成功响应) {String} data.fileName      上传的文件原文件名
     * @apiSuccessExample {Json} 成功响应示例:
     * <p>
     * {
     * "code": "0" ,
     * "msg": "success",
     * "data": "你好"
     * }
     * @apiErrorExample {Json} 失败响应示例:
     * {
     * "code": "-1",
     * "msg": "exception",
     * "data": ""
     * }
     */

    @PostMapping("/asr")
    public ApiRespResult identity(@RequestBody IntelligentDto dto) {
        try {
            logger.debug("identity-->dto={}", dto);
            if (Setting.ServiceProvider.TX.name().equals(serviceProvider)) {
                return this.identityTxHttp(dto);
            } else {
                return ApiRespResult.error(ApiResultCode.E1);
            }
        } catch (Exception e) {
            return ApiRespResult.error(ApiResultCode.EXCEPTION);
        }
    }


    /**
     * 语音识别接口(http)
     *
     * @param dto 请求参数信息
     * @return 返回语音识别结果信息
     */
    private ApiRespResult identityTxHttp(IntelligentDto dto) {
        try {
            if (StringUtils.isEmpty(dto.getUrl()) || StringUtils.isEmpty(dto.getVoiceFormat())) {
                return ApiRespResult.error(ApiResultCode.E1);
            }
            TreeMap<String, Object> params = new TreeMap<>();
            params.put("Nonce", new Random().nextInt(Integer.MAX_VALUE));
            params.put("Timestamp", System.currentTimeMillis() / 1000);
            params.put("Region", region);
            params.put("SecretId", secretId);
            params.put("Action", "SentenceRecognition");
            params.put("Version", "2018-05-22");
            params.put("ProjectId", "0");
            params.put("SubServiceType", "2");
            params.put("EngSerViceType", "16k");
            params.put("SourceType", "0");
            params.put("Url", dto.getUrl());
            params.put("VoiceFormat", dto.getVoiceFormat());
            params.put("UsrAudioKey", UUID.randomUUID().toString());
            long beginTime = System.nanoTime();
            String result = SignatureUtil.sendGet(
                    serviceAddress,
                    SignatureUtil.getSendParams("GET", serviceEndpoint, secretKey, serviceSign, params));
            logger.debug("identityTxHttp-->耗时={}ms，resp={}",(System.nanoTime()-beginTime)/1000000, result);
            System.out.println(result);
            if (null != result) {
                Map responseMap = JSONObject.parseObject(result, Map.class);
                Object o = responseMap.get("Response");
                if (null != o) {
                    responseMap = JSONObject.parseObject(String.valueOf(o), Map.class);
                    String identityTxResult = String.valueOf(responseMap.get("Result"));
                    if (null != identityTxResult) {
                        return identityTxResult.equals(matchingWord) ? ApiRespResult.success() : ApiRespResult.error(ApiResultCode.FAIL, identityTxResult);
                    }
                }
            }
            return ApiRespResult.error(ApiResultCode.FAIL);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("identityTxHttp-->invoke异常信息={}", e);
            return ApiRespResult.error(ApiResultCode.EXCEPTION);
        }
    }


    /**
     * @api {post} /voice/tts 2、语音合成接口
     * @apiDescription 语音合成接口
     * @apiGroup group000_voice
     * @apiName voice
     * @apiVersion 1.0.0
     * @apiSampleRequest /voice/tts
     * @apiPermission admin
     * @apiHeader {String} text 合成语音文字信息
     * @apiSuccess (成功响应) {String} code 请求返回码 0:成功,其它请参见文档定义
     * @apiSuccess (成功响应) {String} msg  请求返回信息
     * @apiSuccess (成功响应) {Json} data   上传成功后返回参数
     * @apiSuccess (成功响应) {String} data.accessUrl     上传成功后文件访问路径
     * @apiSuccess (成功响应) {String} data.storageName   上传成功后服务器存储的文件名
     * @apiSuccess (成功响应) {String} data.fileName      上传的文件原文件名
     * @apiSuccessExample {Json} 成功响应示例:
     * <p>
     * {
     * "code": "0" ,
     * "msg": "success",
     * "data": null
     * @apiErrorExample {Json} 失败响应示例:
     * {
     * "code": "-1",
     * "msg": "exception",
     * "data": ""
     * }
     */

    @PostMapping("/tts")
    public ApiRespResult textToVoice(@RequestBody IntelligentDto dto) {
        try {
            logger.debug("textToVoice-->dto={}", dto);
            if (Setting.ServiceProvider.TX.name().equals(serviceProvider)) {
                return this.textToVoiceTxHttp(dto);
            } else {
                return ApiRespResult.error(ApiResultCode.E1);
            }
        } catch (Exception e) {
            return ApiRespResult.error(ApiResultCode.EXCEPTION);
        }
    }

    /**
     * 语音合成接口
     *
     * @param dto 请求参数信息
     * @return 返回语音合成结果信息
     */
    private ApiRespResult textToVoiceTxHttp(IntelligentDto dto) {
        try {
            if (StringUtils.isEmpty(dto.getText())) {
                return ApiRespResult.error(ApiResultCode.E1);
            }
            TreeMap<String, Object> params = new TreeMap<>();
            params.put("Nonce", new Random().nextInt(Integer.MAX_VALUE));
            params.put("Timestamp", System.currentTimeMillis() / 1000);
            params.put("Region", region);
            params.put("SecretId", secretId);
            params.put("Action", "TextToVoice");
            params.put("Version", "2018-05-22");
            params.put("Text", dto.getText());
            params.put("SessionId", UUID.randomUUID().toString());
            params.put("Volume", "0");
            params.put("Speed", "0");
            params.put("ProjectId", "0");
            params.put("ModelType", "1");
            params.put("VoiceType", "1");
            params.put("PrimaryLanguage", "1");
            long beginTime = System.nanoTime();
            String result = SignatureUtil.sendGet(
                    serviceAddress,
                    SignatureUtil.getSendParams("GET", serviceEndpoint, secretKey, serviceSign, params));
            logger.debug("identityTxHttp-->耗时={}ms，resp={}",(System.nanoTime()-beginTime)/1000000, result);
            if (null != result) {
                Map responseMap = JSONObject.parseObject(result, Map.class);
                Object o = responseMap.get("Response");
                if (null != o) {
                    responseMap = JSONObject.parseObject(String.valueOf(o), Map.class);
                    String identityTxResult = String.valueOf(responseMap.get("Audio"));
                    if (null != identityTxResult) {
                        logger.debug("textToVoiceTxHttp-->存储音频文件信息，begin，{}", fileStorePath + params.get("SessionId"));
                        long beginSaveTime = System.nanoTime();
                        String fileName = params.get("SessionId") + ".wav";
                        SignatureUtil.decoderBase64File(identityTxResult, fileStorePath + fileName);
                        logger.debug("textToVoiceTxHttp-->存储音频文件信息，耗时={}ms，end，{}", (System.nanoTime()-beginSaveTime)/1000000,fileStorePath + params.get("SessionId"));
                        ApiRespResult apiRespResult = ApiRespResult.success();
                        apiRespResult.setData(fileAccessPath + fileName);
                        return apiRespResult;
                    }
                }
            }
            return ApiRespResult.error(ApiResultCode.FAIL);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("textToVoiceTxHttp-->invoke异常信息={}", e);
            return ApiRespResult.error(ApiResultCode.EXCEPTION);
        }
    }
}
