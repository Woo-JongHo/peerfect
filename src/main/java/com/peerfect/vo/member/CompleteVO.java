package com.peerfect.vo.member;

import lombok.Data;

import java.util.Date;

@Data
public class CompleteVO {
    private int completeNo;
    private int memberNo;
    private int missionNo;
    private Date completeTime;

}
