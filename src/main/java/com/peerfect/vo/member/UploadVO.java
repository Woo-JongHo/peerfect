package com.peerfect.vo.member;

import lombok.Data;

import java.sql.Date;

@Data
public class UploadVO {
    private int uploadNo;
    private int memberNo;
    private int missionNo;
    private int uploadLevel;
    private String uploadFile;
    private Date uploadDate;
    private String uploadTimeSpent;
    private String uploadContent;

    UploadVO(){}

}
