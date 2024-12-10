package com.example.peerfect.vo;

import lombok.Data;

import java.sql.Date;

@Data
public class UsersReviewVO {

    private int reviewNo;
    private int userNo;
    private int challengeNo;
    private String reviewContent;
    private Date reviewDate;

    UsersReviewVO(){}

    public UsersReviewVO(int reviewNo, int userNo, int challengeNo, String reviewContent, Date reviewDate) {
        this.reviewNo = reviewNo;
        this.userNo = userNo;
        this.challengeNo = challengeNo;
        this.reviewContent = reviewContent;
        this.reviewDate = reviewDate;
    }
}
