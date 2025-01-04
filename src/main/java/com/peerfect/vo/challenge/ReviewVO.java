package com.peerfect.vo.challenge;

import lombok.Data;

import java.sql.Date;

@Data
public class ReviewVO {

    private int reviewNo;
    private int memberNo;
    private int uploadNo;
    private String reviewContent;
    private Date reviewDate;


}
