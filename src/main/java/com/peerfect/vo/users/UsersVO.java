package com.peerfect.vo.users;

import lombok.Data;

import java.sql.Date;

@Data
public class UsersVO {

    private int userNo;
    private int challengeNo;
    private String userName;
    private String userEmail;
    private String userImage;
    private String userNickname;
    private Date userUIstart;
    private Date userUIend;
    private Date userUXstart;
    private Date userUXend;

    public UsersVO(){}

    public UsersVO(int userNo, int challengeNo, String userName,
                   String userEmail, String userImage, String userNickname,
                   Date userUIstart, Date userUIend, Date userUXstart, Date userUXend) {
        this.userNo = userNo;
        this.challengeNo = challengeNo;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userImage = userImage;
        this.userNickname = userNickname;
        this.userUIstart = userUIstart;
        this.userUIend = userUIend;
        this.userUXstart = userUXstart;
        this.userUXend = userUXend;
    }
}
