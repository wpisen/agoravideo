package com.gwghk.agora.video.agoravideo.base;

import java.io.Serializable;

/**
 * 摘要：接口返回结果对象
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年11月9日
 */
public class ApiRespResult<T> implements Serializable {

    private static final long serialVersionUID = 8724365909968157425L;

    /**
     * 结果码
     */
    private String code = ApiResultCode.SUCCESS.getCode();

    /**
     * 结果信息
     */
    private String msg = ApiResultCode.SUCCESS.getMessage();

    /**
     * 返回结果的数据对象
     */
    private T data;

    public ApiRespResult() {
    }

    public ApiRespResult(String code) {
        this.code = code;
    }
    
    public ApiRespResult(String code, String message){
    	this.code = code;
    	this.msg = message;
    }
    
    public ApiRespResult(ApiResultCode apiResultCode){
    	this.code = apiResultCode.getCode();
    	this.msg = apiResultCode.getMessage();
    }

    public boolean isOk() {
        return ApiResultCode.SUCCESS.getCode().equals(code);
    }
    
    public static <T> ApiRespResult<T> error(ApiResultCode apiResultCode){
    	return new ApiRespResult<T>(apiResultCode);
    }
    
    public static <T> ApiRespResult<T> error(ApiResultCode apiResultCode,T data){
    	return new ApiRespResult<T>(apiResultCode).setData(data);
    }
    
	public static <T> ApiRespResult<T> error(String msg){
    	return new ApiRespResult<T>(ApiResultCode.FAIL.getCode(),msg);
    }
	
	public static <T> ApiRespResult<T> error(String msg,T data){
		return new ApiRespResult<T>(ApiResultCode.FAIL.getCode(),msg).setData(data);
    }
    
	public static <T> ApiRespResult<T> error(String code, String msg){
    	return new ApiRespResult<T>(code,msg);
    }
	
	public static <T> ApiRespResult<T> error(String code, String msg,T data){
    	return new ApiRespResult<T>(code,msg).setData(data);
    }
    
   	public static  ApiRespResult<Void> success(){
       	return new ApiRespResult<Void>(ApiResultCode.SUCCESS);
    }
    
   	public static <T> ApiRespResult<T> success(T data){
    	ApiRespResult<T> ar = new ApiRespResult<T>(ApiResultCode.SUCCESS);
    	ar.setData(data);
    	return ar;
    }
   	
	public ApiRespResult<T> setRespMsg(ApiResultCode apiResultCode){
    	this.code = apiResultCode.getCode();
    	this.msg = apiResultCode.getMessage();
    	return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public ApiRespResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }
    
	public T getData() {
        return data;
    }

    public ApiRespResult<T> setData(T data) {
        this.data = data;
        return this;
    }

	@Override
	public String toString() {
		return "[code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}
}
