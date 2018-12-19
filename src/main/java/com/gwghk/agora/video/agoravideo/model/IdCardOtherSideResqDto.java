package com.gwghk.agora.video.agoravideo.model;

public class IdCardOtherSideResqDto {
  
    /**
     * 错误码，0 为成功
     */
    private String code;
    
    /**
     * 错误描述
     */
    private String message;
    
    /**
     * 当前图片的 url
     */
    private String url;
    /**
     * 具体查询数据
     */
    private IdCardOtherSideDto data;

    public IdCardOtherSideDto getData() {
        return data;
    }

    public void setData(IdCardOtherSideDto data) {
        this.data = data;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    
    
}
