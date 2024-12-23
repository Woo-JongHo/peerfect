package com.peerfect.vo.users;

import lombok.Data;

import java.sql.Date;

@Data
public class MemberVO {

    private int memberNo;
    private int challengeNo;
    private String memberName;
    private String memberPassword;
    private String memberEmail;
    private String memberImg;
    private Date memberUIstart;
    private Date memberUIend;
    private Date memberUXstart;
    private Date memberUXend;

}
