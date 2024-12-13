package com.peerfect.vo.admin;

import lombok.Data;

@Data
public class AdminFeedbackVO {
    private int feedbackNo;
    private String adminId;
    private String userId;
    private String challengeNo;
    private String feedbackContent;


    public AdminFeedbackVO(){}

    public AdminFeedbackVO(int feedbackNo, String adminId, String userId, String challengeNo, String feedbackContent) {
        this.feedbackNo = feedbackNo;
        this.adminId = adminId;
        this.userId = userId;
        this.challengeNo = challengeNo;
        this.feedbackContent = feedbackContent;
    }
}
