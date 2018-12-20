package com.gwghk.agora.video.agoravideo.controller;

import com.alibaba.fastjson.JSONObject;
import com.gwghk.agora.video.agoravideo.base.ApiRespResult;
import com.gwghk.agora.video.agoravideo.base.ApiResultCode;
import com.gwghk.agora.video.agoravideo.dto.IntelligentDto;
import com.gwghk.agora.video.agoravideo.model.CommonResqDto;
import com.gwghk.agora.video.agoravideo.util.Setting;
import com.gwghk.agora.video.agoravideo.util.SignatureUtil;
import com.gwghk.agora.video.agoravideo.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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


    private final static String tts="V_TTS";

    private final static String asr="V_ASR";

    /**
     * @api {post} /voice/asr 4、语音识别接口
     * @apiDescription 语音识别接口
     * @apiGroup group000_Intelligent voice
     * @apiName identity
     * @apiVersion 1.0.0
     * @apiSampleRequest /voice/asr
     * @apiPermission admin
     * @apiHeader {String} Authorization 访问token
     * @apiParam {String} url 音频地址信息
     * @apiParam {String} voiceFormat 音频格式信息
     * @apiParam {String} channelNo 通道信息
     * @apiParam {String} step 用户操作步骤信息
     * @apiSuccess (成功响应) {String} code 请求返回码 0:成功,其它请参见文档定义
     * @apiSuccess (成功响应) {String} msg  请求返回信息
     * @apiSuccess (成功响应) {Json} data   成功后返回参数
     * @apiSuccess (成功响应) {Json} data.flag    匹配结果信息 -1:检测不到或各异常；0:与期望结果不一致，1:与期望结果一致
     * @apiSuccess (成功响应) {Json} data.resultDetails    原始语音信息
     * @apiSuccess (成功响应) {Json} data.step    用户操作步骤信息
     * @apiSuccessExample {Json} 成功响应示例:
        {
            "code": "0",
            "msg": "success",
            "data": [
                {
                "resultDetails": "请问你叫什么名字。",
                "flag": 0,
                "step": "1"
                }
            ],
        "ok": true
        }
     * @apiErrorExample {Json} 失败响应示例:
     * {
     * "code": "-1",
     * "msg": "exception",
     * "data": ""
     * }
     */

    @RequestMapping("/asr")
    public ApiRespResult identity(IntelligentDto dto) {
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
            if (StringUtils.isEmpty(dto.getUrl()) || StringUtils.isEmpty(dto.getVoiceFormat()) || StringUtils.isEmpty(dto.getChannelNo()) || StringUtils.isEmpty(dto.getStep())) {
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
                        List<Object> resultList = new ArrayList<>();
                        Map<String,Object> map = new HashMap<>();
                        map.put("flag",identityTxResult.equals(matchingWord) ? 1 : 0);
                        map.put("resultDetails",identityTxResult);
                        map.put("step",dto.getStep());
                        if(1 != dto.getStep()){
                            Object tempObj = ValidateUtil.getResult(dto.getChannelNo());
                            if(!StringUtils.isEmpty(tempObj) && tempObj instanceof Map){
                                Object otherT = ((Map) tempObj).get(asr);
                                if(!StringUtils.isEmpty(tempObj) && otherT instanceof List){
                                    resultList = (List<Object>) otherT;
                                }
                            }
                        }
                        resultList.add(map);
                        ValidateUtil.addResult(dto.getChannelNo(),asr, resultList);
                        ApiRespResult apiRespResult = ApiRespResult.success();
                        apiRespResult.setData(resultList);
                        return apiRespResult;
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
     * @api {post} /voice/tts 5、语音合成接口
     * @apiDescription 语音合成接口
     * @apiGroup group000_Intelligent voice
     * @apiName textToVoice
     * @apiVersion 1.0.0
     * @apiSampleRequest /voice/tts
     * @apiPermission admin
     * @apiHeader {String} Authorization 访问token
     * @apiParam {String} text 合成语音文字信息
     * @apiParam {String} channelNo 通道信息
     * @apiSuccess (成功响应) {String} code 请求返回码 0:成功,其它请参见文档定义
     * @apiSuccess (成功响应) {String} msg  请求返回信息
     * @apiSuccess (成功响应) {Json} data   成功后返回参数
     * @apiSuccess (成功响应) {Json} data.flag   匹配结果信息 ，此接口暂时未用到此字段信息
     * @apiSuccess (成功响应) {Json} data.resultDetails   语音地址信息
     * @apiSuccessExample {Json} 成功响应示例:
     * {
     * "code": "0" ,
     * "msg": "success",
     * "data":  {
     *             "flag": 0,
     *             "otherData": null,
     *             "resultDetails": "http://ip:port/xx/1.wav"
     *     }
     * }
     * @apiErrorExample {Json} 失败响应示例:
     * {
     * "code": "-1",
     * "msg": "exception",
     * "data": ""
     * }
     */

    @RequestMapping("/tts")
    public ApiRespResult textToVoice(IntelligentDto dto) {
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
            if (StringUtils.isEmpty(dto.getText())|| StringUtils.isEmpty(dto.getChannelNo())) {
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
                        CommonResqDto commonResqDto = new CommonResqDto();
                        commonResqDto.setFlag(1);
                        commonResqDto.setResultDetails(fileAccessPath + fileName);
                        ValidateUtil.addResult(dto.getChannelNo(),tts, commonResqDto);
                        apiRespResult.setData(commonResqDto);
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
