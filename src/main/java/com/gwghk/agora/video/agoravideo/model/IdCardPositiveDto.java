package com.gwghk.agora.video.agoravideo.model;

public class IdCardPositiveDto {
  
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 性别
     */
    private String sex;
    
    /**
     * 民族
     */
    private String nation;
    
    /**
     * 出生日期
     */
    private String birth;
    
    /**
     * 地址
     */
    private String address;
    
    /**
     * 身份证号
     */
    private String id;
    
    /**
     * 证件姓名置信度，取值范围[0,100]
     */
    private Integer[] name_confidence_all;
    
    /**
     * 性别置信度，取值范围[0,100]
     */
    private Integer[] sex_confidence_all;
    
    /**
     * 民族置信度，取值范围[0,100]
     */
    private Integer[] nation_confidence_all;
    
    /**
     * 出生日期置信度，取值范围[0,100]
     */
    private Integer[] birth_confidence_all;
    
    /**
     * 地址置信度，取值范围[0,100]
     */
    private Integer[] address_confidence_all;
    
    /**
     * 身份证号置信度,，取值范围[0,100]
     */
    private Integer[] id_confidence_all;
    
    /**
     * 
     */
    private String card_type;
    private String[] recognize_warn_code;
    private String[] recognize_warn_msg;
    
   
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getNation() {
        return nation;
    }
    public void setNation(String nation) {
        this.nation = nation;
    }
    public String getBirth() {
        return birth;
    }
    public void setBirth(String birth) {
        this.birth = birth;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
   
    public Integer[] getName_confidence_all() {
        return name_confidence_all;
    }
    public void setName_confidence_all(Integer[] name_confidence_all) {
        this.name_confidence_all = name_confidence_all;
    }
    public Integer[] getSex_confidence_all() {
        return sex_confidence_all;
    }
    public void setSex_confidence_all(Integer[] sex_confidence_all) {
        this.sex_confidence_all = sex_confidence_all;
    }
    public Integer[] getNation_confidence_all() {
        return nation_confidence_all;
    }
    public void setNation_confidence_all(Integer[] nation_confidence_all) {
        this.nation_confidence_all = nation_confidence_all;
    }
    public Integer[] getBirth_confidence_all() {
        return birth_confidence_all;
    }
    public void setBirth_confidence_all(Integer[] birth_confidence_all) {
        this.birth_confidence_all = birth_confidence_all;
    }
    public Integer[] getAddress_confidence_all() {
        return address_confidence_all;
    }
    public void setAddress_confidence_all(Integer[] address_confidence_all) {
        this.address_confidence_all = address_confidence_all;
    }
    public Integer[] getId_confidence_all() {
        return id_confidence_all;
    }
    public void setId_confidence_all(Integer[] id_confidence_all) {
        this.id_confidence_all = id_confidence_all;
    }
    public String getCard_type() {
        return card_type;
    }
    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }
    public String[] getRecognize_warn_code() {
        return recognize_warn_code;
    }
    public void setRecognize_warn_code(String[] recognize_warn_code) {
        this.recognize_warn_code = recognize_warn_code;
    }
    public String[] getRecognize_warn_msg() {
        return recognize_warn_msg;
    }
    public void setRecognize_warn_msg(String[] recognize_warn_msg) {
        this.recognize_warn_msg = recognize_warn_msg;
    }
    
    
}
