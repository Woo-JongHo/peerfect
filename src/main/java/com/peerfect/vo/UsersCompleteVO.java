package com.peerfect.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UsersCompleteVO {
    private int completeNo;
    private int userNO;
    private int challengeNo;
    private Date completeTime;

    UsersCompleteVO(){}

    public UsersCompleteVO(int completeNo, int userNO, int challengeNo, Date completeTime) {
        this.completeNo = completeNo;
        this.userNO = userNO;
        this.challengeNo = challengeNo;
        this.completeTime = completeTime;
    }

}
