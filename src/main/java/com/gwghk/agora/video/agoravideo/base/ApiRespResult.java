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
     * 扩展对象(放置分页信息、其他信息等)
     */
    private Object extendData;
    
    /**
     * 参数数组
     */
    private Object[] params;
    
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
    
    public ApiRespResult(String code, String message,Object... params){
    	this.code = code;
    	this.msg = message;
    	this.params = params;
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
    
    public static <T> ApiRespResult<T> error(ApiResultCode apiResultCode,Object...params){
    	return new ApiRespResult<T>(apiResultCode.getCode(),String.format(apiResultCode.getMessage(),params),params);
    }
    
    public static <T> ApiRespResult<T> error(ApiResultCode apiResultCode,String param){
        return new ApiRespResult<T>(apiResultCode.getCode(),String.format(apiResultCode.getMessage(),param),new Object[]{param});
    }
    
    public static <T> ApiRespResult<T> error(ApiResultCode apiResultCode,T data,Object...params){
    	return new ApiRespResult<T>(apiResultCode.getCode(),String.format(apiResultCode.getMessage(),params),params).setData(data);
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
   	
	public static <T> ApiRespResult<T> success(T data,Object extendData){
    	ApiRespResult<T> ar = new ApiRespResult<T>(ApiResultCode.SUCCESS);
    	return ar.setData(data).setExtendData(extendData);
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
    
    public ApiRespResult<T> setMsg(String msg,Object...params) {
        this.msg = String.format(msg,params);
        this.params = params;
        return this;
    }

    public Object getExtendData() {
        return extendData;
    }

    public ApiRespResult<T> setExtendData(Object extendData) {
        this.extendData = extendData;
        return this;
    }

    public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
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
		return "[code=" + code + ", msg=" + msg + ", extendData=" + extendData + ", data=" + data + "]";
	}
}
