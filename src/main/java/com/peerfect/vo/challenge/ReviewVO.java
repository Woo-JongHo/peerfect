package com.peerfect.vo.challenge;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

@Data
public class ReviewVO {

    private Long reviewNo;
    private String memberId;
    private Long challengeNo;
    private Long reviewLevel;
    private String reviewText;
    private Timestamp reviewDate;
    private Long reviewWaste;
    private String memberName;
}
