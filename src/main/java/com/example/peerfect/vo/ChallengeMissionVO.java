package com.example.peerfect.vo;

import lombok.Data;

@Data
public class ChallengeMissionVO {
    private int missionNo;
    private int challengeNo;
    private int missionOrder;
    private String missionContent;

    public ChallengeMissionVO(){}

    public ChallengeMissionVO(int missionNo, int challengeNo, int missionOrder, String missionContent) {
        this.missionNo = missionNo;
        this.challengeNo = challengeNo;
        this.missionOrder = missionOrder;
        this.missionContent = missionContent;
    }
}
