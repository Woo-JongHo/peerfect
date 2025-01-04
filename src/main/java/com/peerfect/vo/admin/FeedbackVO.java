package com.peerfect.vo.admin;

import lombok.Data;

@Data
public class FeedbackVO {
    private int feedbackNo;
    private String adminId;
    private String userNo;
    private String missionNo;
    private String feedbackContent;


}