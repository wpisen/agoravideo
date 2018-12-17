package com.gwghk.agora.video.agoravideo.model;

public class CommonResqDto {
    private Integer flag = -1;//-1:检测不到或各异常；0:与期望结果相反值，1:与期望结果一致

    
    private Object otherData;//其它数据信息
    
    private Object resultDetails;//结果详情数据

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

     

    public Object getOtherData() {
        return otherData;
    }

    public void setOtherData(Object otherData) {
        this.otherData = otherData;
    }

    public Object getResultDetails() {
        return resultDetails;
    }

    public void setResultDetails(Object resultDetails) {
        this.resultDetails = resultDetails;
    }

    
    
}
