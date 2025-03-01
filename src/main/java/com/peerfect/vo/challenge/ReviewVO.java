package com.peerfect.vo.challenge;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

@Data
public class ReviewVO {
    private Long reviewNo;
    private Long memberId;
    private Long challengeNo;
    private Integer reviewLevel;
    private String reviewText;
    private String reviewDate;
    private Integer reviewWaste;
    private String memberName;

}
