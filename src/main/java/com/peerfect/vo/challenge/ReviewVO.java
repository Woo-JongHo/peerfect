package com.peerfect.vo.challenge;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class ReviewVO {

    private Long reviewNo;
    private Long memberNo;
    private Long challengeNo;
    private Long reviewLevel;
    private String reviewText;
    private Timestamp reviewDate;
    private Long reviewWaste;
}
