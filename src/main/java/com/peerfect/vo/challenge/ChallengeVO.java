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
    private String challengeIntro;
    private String challengeMission;
    private String challengeLevel;

    public ChallengeVO(int challengeNo, String challengeGroup, String challengeGoal, String challengeType, String challengeTitle, String challengeIntro, String challengeMission, String challengeLevel) {
        this.challengeNo = challengeNo;
        this.challengeGroup = challengeGroup;
        this.challengeGoal = challengeGoal;
        this.challengeType = challengeType;
        this.challengeTitle = challengeTitle;
        this.challengeIntro = challengeIntro;
        this.challengeMission = challengeMission;
        this.challengeLevel = challengeLevel;
    }

}


