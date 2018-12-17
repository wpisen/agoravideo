package com.gwghk.agora.video.agoravideo.model;

public class IdentityResqDto {
    /**
     * 用户上传的图像与身份证登记照的人脸相似度，取值范围[0,100]，推荐相似度大于 75 时可判断为同一人，可根据具体场景自行调整阈值
     */
   private Float similarity;

    public Float getSimilarity() {
        return similarity;
    }

    public void setSimilarity(Float similarity) {
        this.similarity = similarity;
    }
  
}
