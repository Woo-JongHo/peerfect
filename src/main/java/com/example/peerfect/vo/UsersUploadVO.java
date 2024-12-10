package com.example.peerfect.vo;

public class UsersUploadVO {
    private int uploadNo;
    private int userNo;
    private int challengeNo;
    private String challengeLevel;
    private String uploadTitle;
    private String uploadFile;

    UsersUploadVO(){}

    public UsersUploadVO(int uploadNo, int userNo, int challengeNo, String challengeLevel,
                         String uploadTitle, String uploadFile) {
        this.uploadNo = uploadNo;
        this.userNo = userNo;
        this.challengeNo = challengeNo;
        this.challengeLevel = challengeLevel;
        this.uploadTitle = uploadTitle;
        this.uploadFile = uploadFile;
    }
}
