package com.gwghk.agora.video.agoravideo.controller;

import com.gwghk.agora.video.agoravideo.base.ApiRespResult;
import com.gwghk.agora.video.agoravideo.base.ApiResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * 文件上传
 */
@RestController
@RequestMapping("/common")
public class FileController {

    protected final static Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("${file.storePath}")
    private String storePath;

    @Value("${file.accessPath}")
    private String accessPath;

    private String[] IMG_FILE_TYPES = {".JPG", ".PNG", ".JPEG", ".WEBP"};

    private long IMG_MAX_SIZE = 5 * 1024 * 1024;

    /**
     * @api {post} /common/uploadFile 1、文件上传
     * @apiDescription 文件上传
     * @apiGroup group000_common
     * @apiName uploadFile
     * @apiVersion 1.0.0
     * @apiSampleRequest /common/uploadFile
     * @apiPermission admin
     * @apiHeader {String} Authorization 访问token
     * @apiParam {int} file 文件域参数名称
     * @apiSuccess (成功响应) {String} code 请求返回码 0:成功,其它请参见文档定义
     * @apiSuccess (成功响应) {String} msg  请求返回信息
     * @apiSuccess (成功响应) {Json} data   上传成功后返回参数
     * @apiSuccess (成功响应) {String} data.accessUrl     上传成功后文件访问路径
     * @apiSuccess (成功响应) {String} data.storageName   上传成功后服务器存储的文件名
     * @apiSuccess (成功响应) {String} data.fileName      上传的文件原文件名
     * @apiSuccessExample {Json} 成功响应示例:
     * {
     * "code": "0" ,
     * "msg": "success",
     * "data": {
     * "fileName": "selfedit_one.apk",
     * "accessUrl": "http://172.27.1.104:82/ea208b14-3487-4937-81ba-e8c1ff313220.apk",
     * "storageName": "ea208b14-3487-4937-81ba-e8c1ff313220.apk"
     * }
     * }
     * @apiErrorExample {Json} 失败响应示例:
     * {
     * "code": "-1",
     * "msg": "exception",
     * "data": ""
     * }
     */

    @PostMapping("/uploadFile")
    public ApiRespResult uploadFile(@RequestParam("file") MultipartFile file) {
        ApiRespResult apiRespResult = checkFile(file);
        if (!ApiResultCode.SUCCESS.getCode().equals(apiRespResult.getCode())) {
            return apiRespResult;
        }
        return doUploadFile(file);
    }

    /**
     * 校验文件是否合法
     */
    private ApiRespResult checkFile(MultipartFile file) {
        if (file.isEmpty()) {
            logger.info(ApiResultCode.E1.getCode());
            return ApiRespResult.error(ApiResultCode.E1);
        }
        if (file.getSize() > IMG_MAX_SIZE) {
            logger.info(ApiResultCode.E7.getCode());
            return ApiRespResult.error(ApiResultCode.E7);
        }
        return ApiRespResult.success();
    }


    /**
     * 上传文件
     *
     * @param file 文件信息
     * @return 返回前端信息
     */
    private ApiRespResult doUploadFile(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            String fileSubfix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + fileSubfix;
            Path path = Paths.get(storePath + newFileName);
            Files.createDirectories(path.getParent());
            Files.write(path, bytes);
            String accessUrl = accessPath + newFileName;
            Map<String, Object> respMap = new HashMap<>(3);
            respMap.put("accessUrl", accessUrl);
            respMap.put("storageName", newFileName);
            respMap.put("fileName", file.getOriginalFilename());
            ApiRespResult result = ApiRespResult.success();
            result.setData(respMap);
            return result;
        } catch (Exception e) {
            logger.error(ApiResultCode.EXCEPTION.getCode());
            logger.error(e.getMessage(), e);
            return ApiRespResult.error(ApiResultCode.EXCEPTION);
        }
    }
}
