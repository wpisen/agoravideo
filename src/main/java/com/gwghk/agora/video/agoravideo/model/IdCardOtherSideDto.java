package com.gwghk.agora.video.agoravideo.model;

public class IdCardOtherSideDto {
  
     
    
    /**反面相关的属性**/
    /**
     * 发证机关
     */
    private String authority;
    /**
     * 证件有效期
     */
    private String valid_date;
    
    /**
     * 发证机关置信度，取值范围[0,100]
     */
    private Integer[] authority_confidence_all;
    
    /**
     * 证件有效期置信度，取值范围[0,100]
     */
    private Integer[] valid_date_confidence_all;
    
    public String getAuthority() {
        return authority;
    }
    public void setAuthority(String authority) {
        this.authority = authority;
    }
    public String getValid_date() {
        return valid_date;
    }
    public void setValid_date(String valid_date) {
        this.valid_date = valid_date;
    }
    public Integer[] getAuthority_confidence_all() {
        return authority_confidence_all;
    }
    public void setAuthority_confidence_all(Integer[] authority_confidence_all) {
        this.authority_confidence_all = authority_confidence_all;
    }
    public Integer[] getValid_date_confidence_all() {
        return valid_date_confidence_all;
    }
    public void setValid_date_confidence_all(Integer[] valid_date_confidence_all) {
        this.valid_date_confidence_all = valid_date_confidence_all;
    }
    
    
}
