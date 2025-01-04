package com.peerfect.vo.utils;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VerifyVO {
    private String verifyEmail;
    private String verifyPassword;
    private LocalDateTime verifyExpire;

    public VerifyVO(String verifyEmail, String verifyPassword, LocalDateTime verifyExpire) {
        this.verifyEmail = verifyEmail;
        this.verifyPassword = verifyPassword;
        this.verifyExpire = verifyExpire;
    }



    public VerifyVO() {

    }
}
