package com.peerfect.vo.challenge;


import lombok.Data;

import java.sql.Date;

@Data
public class ChallengeVO {
    private int challengeNo;
    private String challengeGroup;
    private String challengeGoal;
    private String challengeType;
    private String challengeTitle;
    private String challengeShortIntro;
    private String challengeIntro;
    private String challengeMission;
    private String challengeLevel;
    private String challengeDay;

    public ChallengeVO(int challengeNo, String challengeGroup, String challengeGoal, String challengeType, String challengeTitle, String challengeIntro, String challengeMission, String challengeLevel, String challengeDay) {
        this.challengeNo = challengeNo;
        this.challengeGroup = challengeGroup;
        this.challengeGoal = challengeGoal;
        this.challengeType = challengeType;
        this.challengeTitle = challengeTitle;
        this.challengeIntro = challengeIntro;
        this.challengeMission = challengeMission;
        this.challengeLevel = challengeLevel;
        this.challengeDay = challengeDay;
    }

}


