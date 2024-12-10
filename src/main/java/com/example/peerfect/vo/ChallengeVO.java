package com.example.peerfect.vo;


import lombok.Data;

import java.sql.Date;

@Data
public class ChallengeVO {
    private int challengeNo;
    private String challengeId;
    private String challengeType;
    private String challengeTitle;
    private String challengeLevel;
    private Date challengeStart;
    private String challengeContent;
    private String challengeExplanation;
    private String challengeRules;

    public ChallengeVO(){}

    public ChallengeVO(int challengeNo, String challengeId, String challengeType,
                       String challengeTitle, String challengeLevel, Date challengeStart,
                       String challengeContent, String challengeExplanation, String challengeRules) {
        this.challengeNo = challengeNo;
        this.challengeId = challengeId;
        this.challengeType = challengeType;
        this.challengeTitle = challengeTitle;
        this.challengeLevel = challengeLevel;
        this.challengeStart = challengeStart;
        this.challengeContent = challengeContent;
        this.challengeExplanation = challengeExplanation;
        this.challengeRules = challengeRules;
    }
}


