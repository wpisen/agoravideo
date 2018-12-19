package com.gwghk.agora.video.agoravideo.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 语音识别dto
 */
public class IntelligentDto implements Serializable {
    private static final long serialVersionUID = 1L;
    //腾讯云项目 ID，可填 0，总长度不超过 1024 字节。
    private String projectId;
    //子服务类型。2，一句话识别。
    private String subServiceType;
    //引擎类型。8k：电话 8k 通用模型；16k：16k 通用模型。只支持单声道音频识别。
    private String engSerViceType;
    //语音数据来源。0：语音 URL；1：语音数据（post body）。
    private String sourceType;
    //语音 URL，公网可下载。当 SourceType 值为 0 时须填写该字段，为 1 时不填；URL 的长度大于 0，小于 2048，需进行urlencode编码。音频时间长度要小于60s。
    private String url;
    //识别音频的音频格式（支持mp3,wav）。
    private String voiceFormat;
    //用户端对此任务的唯一标识，用户自助生成，用于用户查找识别结果。
    private String usrAudioKey;
    //语音数据，当SourceType 值为1时必须填写，为0可不写。要base64编码(采用python语言时注意读取文件应该为string而不是byte，以byte格式读取后要decode()。编码后的数据不可带有回车换行符)。音频数据要小于900k。
    private String data;
    //数据长度，当 SourceType 值为1时必须填写，为0可不写（此数据长度为数据未进行base64编码时的数据长度）。
    private String dataLen;
    //	地域参数，用来标识希望操作哪个地域的数据。
    private String region;
    //	在云API密钥上申请的标识身份的 SecretId，一个 SecretId 对应唯一的 SecretKey ，而 SecretKey 会用来生成请求签名 Signature。
    private String secretId;
    //具体操作的指令接口名称，例如想要调用云服务器的查询实例列表接口，则 Action 参数即为 DescribeInstances 。
    private String action;
    //API 的版本。例如 2017-03-12
    private String version;
    //随机正整数，与 Timestamp 联合起来，用于防止重放攻击。
    private String nonce;
    // 时间戳信息
    private String timestamp;
    // 文本信息
    private String text;
    private String channelNo;//通道号

    @JSONField(name = "ProjectId")
    public String getProjectId() {
        return projectId;
    }

    @JSONField(name = "SubServiceType")
    public String getSubServiceType() {
        return subServiceType;
    }

    @JSONField(name = "EngSerViceType")
    public String getEngSerViceType() {
        return engSerViceType;
    }
    @JSONField(name = "SourceType")
    public String getSourceType() {
        return sourceType;
    }
    @JSONField(name = "Url")
    public String getUrl() {
        return url;
    }
    @JSONField(name = "VoiceFormat")
    public String getVoiceFormat() {
        return voiceFormat;
    }
    @JSONField(name = "UsrAudioKey")
    public String getUsrAudioKey() {
        return usrAudioKey;
    }
    @JSONField(name = "Data")
    public String getData() {
        return data;
    }
    @JSONField(name = "DataLen")
    public String getDataLen() {
        return dataLen;
    }
    @JSONField(name = "Region")
    public String getRegion() {
        return region;
    }
    @JSONField(name = "SecretId")
    public String getSecretId() {
        return secretId;
    }
    @JSONField(name = "Action")
    public String getAction() {
        return action;
    }
    @JSONField(name = "Version")
    public String getVersion() {
        return version;
    }
    @JSONField(name = "Nonce")
    public String getNonce() {
        return nonce;
    }
    @JSONField(name = "Timestamp")
    public String getTimestamp() {
        return timestamp;
    }
    @JSONField(name = "Text")
    public String getText() {
        return text;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setSubServiceType(String subServiceType) {
        this.subServiceType = subServiceType;
    }

    public void setEngSerViceType(String engSerViceType) {
        this.engSerViceType = engSerViceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setVoiceFormat(String voiceFormat) {
        this.voiceFormat = voiceFormat;
    }

    public void setUsrAudioKey(String usrAudioKey) {
        this.usrAudioKey = usrAudioKey;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setDataLen(String dataLen) {
        this.dataLen = dataLen;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    public IntelligentDto() {
    }

    @Override
    public String toString() {
        return "IntelligentDto{" +
                "projectId='" + projectId + '\'' +
                ", subServiceType='" + subServiceType + '\'' +
                ", engSerViceType='" + engSerViceType + '\'' +
                ", sourceType='" + sourceType + '\'' +
                ", url='" + url + '\'' +
                ", voiceFormat='" + voiceFormat + '\'' +
                ", usrAudioKey='" + usrAudioKey + '\'' +
                ", data='" + data + '\'' +
                ", dataLen='" + dataLen + '\'' +
                ", region='" + region + '\'' +
                ", secretId='" + secretId + '\'' +
                ", action='" + action + '\'' +
                ", version='" + version + '\'' +
                ", nonce='" + nonce + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", text='" + text + '\'' +
                ", channelNo='" + channelNo + '\'' +
                '}';
    }
}

