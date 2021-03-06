package com.gwghk.agora.video.agoravideo.controller;

import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.speech.AipSpeech;
import com.gwghk.agora.video.agoravideo.base.ApiRespResult;
import com.gwghk.agora.video.agoravideo.base.ApiResultCode;
import com.gwghk.agora.video.agoravideo.dto.IntelligentDto;
import com.gwghk.agora.video.agoravideo.model.CommonResqDto;
import com.gwghk.agora.video.agoravideo.util.HttpClientUtil;
import com.gwghk.agora.video.agoravideo.util.Setting;
import com.gwghk.agora.video.agoravideo.util.SignatureUtil;
import com.gwghk.agora.video.agoravideo.util.ValidateUtil;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URLEncoder;
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
     * 语音合成服务提供商
     */
    @Value("${service.provider}")
    private String serviceProvider;

    /**
     * 语音识别服务提供商
     */
    @Value("${service.intelligent.provider}")
    private String serviceIntelligentProvider;

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
     * 帝派语音识别请求地址
     */
    @Value("${dp.service.address}")
    private String dpServiceAddress;
    //获取本轮识别会话ID
    private final static  String DP_TTS_URL_START="start";

    //语音上传识别接口，实时识别需要持续调用此接口
    private final static  String DP_TTS_URL="audio";

    //结束本轮会话接口
    private final static  String DP_TTS_URL_END="end";


    private final static String tts="V_TTS";

    private final static String asr="V_ASR";

    @Autowired
    private AipSpeech aipSpeech;

    /**
     * @api {post} /voice/asr 1、语音识别接口
     * @apiDescription 语音识别接口
     * @apiGroup 3、Voice
     * @apiName identity
     * @apiVersion 1.0.0
     * @apiSampleRequest /voice/asr
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
            if (StringUtils.isEmpty(dto.getUrl()) || StringUtils.isEmpty(dto.getVoiceFormat()) || StringUtils.isEmpty(dto.getChannelNo()) || StringUtils.isEmpty(dto.getStep())) {
                return ApiRespResult.error(ApiResultCode.E1);
            }

            if (Setting.ServiceProvider.TX.name().equals(serviceIntelligentProvider)) {
                return this.identityTxHttp(dto);
            } else if (Setting.ServiceProvider.BD.name().equals(serviceIntelligentProvider)) {
                return this.identityBDHttp(dto);
            } else if (Setting.ServiceProvider.DP.name().equals(serviceIntelligentProvider)) {
                return this.identityDPHttp(dto);
            }else {
                return ApiRespResult.error(ApiResultCode.E1);
            }
        } catch (Exception e) {
            return ApiRespResult.error(ApiResultCode.EXCEPTION);
        }
    }

    /**
     * 语音识别接口(帝派)
     * @param dto 请求参数信息
     * @return 返回语音识别结果信息
     */
    private ApiRespResult identityDPHttp(IntelligentDto dto) {
        try {
            //首先获取回话id
            Object sid = StringUtils.isEmpty(dto.getSid()) ? querySid() : dto.getSid();
            if (!StringUtils.isEmpty(sid)) {
                File file = new File(fileStorePath + dto.getUrl().substring(dto.getUrl().lastIndexOf("/") + 1, dto.getUrl().length()));
                Map map = new HashMap();
                map.put("stype", "dsr");//引擎类型，参数可为isr，dsr
                map.put("sid", sid);
                map.put("audio_status", String.valueOf(1 == dto.getStep() ? dto.getStep() : (-1 == dto.getStep() ? 4 : 2)));
                map.put("wav_len", String.valueOf(file.length()));
                map.put("wav_data", URLEncoder.encode(Base64.getEncoder().encodeToString(this.getBytes(fileStorePath + dto.getUrl().substring(dto.getUrl().lastIndexOf("/") + 1, dto.getUrl().length()))),"GBK"));
                long beginTime = System.nanoTime();
                String result = HttpClientUtil.doPostWithMap(dpServiceAddress + DP_TTS_URL, map, null);
                logger.debug("identityDPHttp-->耗时={}ms，resp={}", (System.nanoTime() - beginTime) / 1000000, result);
                if (!StringUtils.isEmpty(result)) {
                    JSONObject responseRes = getResult(result);
                    if (!StringUtils.isEmpty(responseRes)) {
                        new Thread(() -> SignatureUtil.sendGet(dpServiceAddress + DP_TTS_URL_END, "stype=dsr&sid=" + sid)).start();
                        dto.setSid(String.valueOf(sid));
                        return this.operateResult(dto, responseRes.containsKey("kw") ? responseRes.get("kw") : null);
                    }
                }
            }
            return ApiRespResult.error(ApiResultCode.FAIL);
        } catch (Exception e) {
            logger.debug("identityDPHttp-->invoke异常信息={}", e);
            return ApiRespResult.error(ApiResultCode.EXCEPTION);
        }
    }



    /**
     * 获取回话id信息
     * @return 返回回话id
     */
    private Object querySid() {
        Object sid = null;
        long beginTime = System.nanoTime();
        String startResult = SignatureUtil.sendGet(dpServiceAddress + DP_TTS_URL_START, "stype=dsr");
        logger.debug("querySid-->耗时={}ms，resp={}",(System.nanoTime()-beginTime)/1000000, startResult);
        if (!StringUtils.isEmpty(startResult)) {
            JSONObject startJR =  getResult(startResult);
            if(!StringUtils.isEmpty(startJR)){
                sid = startJR.containsKey("sid")?startJR.get("sid"):null;
            }
        }
        return sid;
    }

    /**
     * 获取有效结果信息
     * @param startResult 返回结果信息
     * @return 返回有效结果信息
     */
    private JSONObject getResult(String startResult) {
        Map<String,Object> startMap = JSONObject.parseObject(startResult);
        if (0 == Integer.valueOf(startMap.getOrDefault("code", -1) + "")) {
              return JSONObject.parseObject(startMap.get("result") + "");
        }
        return null;
    }


    /**
     * 语音识别接口(百度)
     * @param dto 请求参数信息
     * @return 返回语音识别结果信息
     */
    private ApiRespResult identityBDHttp(IntelligentDto dto) {
        try {
            long beginTime = System.nanoTime();
            org.json.JSONObject response = aipSpeech.asr(fileStorePath + dto.getUrl().substring(dto.getUrl().lastIndexOf("/") + 1, dto.getUrl().length()), dto.getVoiceFormat(), 16000, null);
            logger.debug("identityBDHttp-->耗时={}ms，resp={}",(System.nanoTime()-beginTime)/1000000, response);
            if(!StringUtils.isEmpty(response) && response.has("err_no") && 0 == response.getInt("err_no")){
                Object result = response.get("result");
                if(!StringUtils.isEmpty(result)){
                    if(result instanceof JSONArray){
                        result =((JSONArray) result).get(0);
                    }
                    return this.operateResult(dto,result);
                }
            }
            return ApiRespResult.error(ApiResultCode.FAIL);
        }catch (Exception e){
            e.printStackTrace();
            logger.debug("identityBDHttp-->invoke异常信息={}", e);
            return ApiRespResult.error(ApiResultCode.EXCEPTION);
        }
    }

    /**
     * 设置返回信息
     * @param dto 请求参数信息
     * @param identityTxResult 语音识别信息
     * @return 返回前端信息
     */
    private ApiRespResult operateResult(IntelligentDto dto, Object identityTxResult) {
        List<Object> resultList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("flag",matchingWord.equals(identityTxResult) ? 1 : 0);
        map.put("resultDetails",identityTxResult);
        map.put("step",dto.getStep());
        map.put("sid",dto.getSid());
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
        return  ApiRespResult.success(map);
    }


    /**
     * 语音识别接口(http，腾讯)
     *
     * @param dto 请求参数信息
     * @return 返回语音识别结果信息
     */
    private ApiRespResult identityTxHttp(IntelligentDto dto) {
        try {
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
                        return this.operateResult(dto,identityTxResult);
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
     * @apiGroup 3、Voice
     * @apiName textToVoice
     * @apiVersion 1.0.0
     * @apiSampleRequest /voice/tts
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

    /**
     * 获取文件二进制流
     *
     * @param filePath 文件路径
     * @return 返回文件二进制流
     */
    private byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
