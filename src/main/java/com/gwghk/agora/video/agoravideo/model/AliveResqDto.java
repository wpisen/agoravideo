package com.gwghk.agora.video.agoravideo.model;

public class AliveResqDto {
    private Long[] five_point;

    /**
     * 活体打分，取值范围 [0,100]，推荐相大于 87 时可判断为活体，可根据具体场景自行调整阈值
     */
    private Integer score;

    public Long[] getFive_point() {
        return five_point;
    }

    public void setFive_point(Long[] five_point) {
        this.five_point = five_point;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
