package com.gwghk.agora.video.agoravideo.model;

public class IdCardResqDto {
  
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
    private IdCardDto data;

    public IdCardDto getData() {
        return data;
    }

    public void setData(IdCardDto data) {
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
