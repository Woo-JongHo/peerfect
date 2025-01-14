package com.peerfect.vo.member;

import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Data
public class MemberVO {

    private int memberNo;
    private int challengeNo;
    private UUID memberId;
    private String memberName;
    private String memberPassword;
    private String memberEmail;
    private String memberImg;
    private Date memberUIstart;
    private Date memberUIend;
    private Date memberUXstart;
    private Date memberUXend;
    private boolean memberAgree;

    public MemberVO(String memberName, String memberPassword, String memberEmail) {
        this.memberName = memberName;
        this.memberPassword = memberPassword;
        this.memberEmail = memberEmail;
    }
}
